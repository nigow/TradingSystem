package org.twelve.gateways;

import org.twelve.entities.Thresholds;

import java.io.*;
import java.util.regex.Pattern;

/**
 * A restriction gateway that uses csv files as persistent storage.
 *
 * @author Tairi
 */
public class CSVThresholdsGateway implements ThresholdsGateway {

    /**
     * The current restrictions of the system consists of lend, weekly trades and incomplete trade limits.
     */
    private Thresholds currentThresholds;


    /**
     * Path to the CSV file that stores the current restrictions.
     */
    private final String filepath;


    /**
     * Constructor for CSVRoleGateway that construct the current restrictions.
     *
     * @param filepath the filepath to the csv file
     * @throws IOException If the given csv file cannot be accessed
     */
    public CSVThresholdsGateway(String filepath) throws IOException {
        this.filepath = filepath;

        //pre-processing of file reading
        File f = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();

        //skip the first row
        line = br.readLine();

        while (line != null && Pattern.matches("^[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+$", line)) {
            //only the second line consists of restrictions separated by commas
            String[] lineArray = line.split(",");
            int lendLimit = Integer.parseInt(lineArray[0]);
            int incompleteTrade = Integer.parseInt(lineArray[1]);
            int weeklyTrade = Integer.parseInt(lineArray[2]);
            int numberOfDays = Integer.parseInt(lineArray[3]);
            int numberOfEdits = Integer.parseInt(lineArray[4]);
            int numberOfStats = Integer.parseInt(lineArray[5]);

            this.currentThresholds = new Thresholds(lendLimit, incompleteTrade, weeklyTrade, numberOfDays, numberOfEdits, numberOfStats);

            line = br.readLine();

        }

        br.close();

        // currentRestriction being null here means file was corrupted
        if (currentThresholds == null) throw new IOException("Restrictions.csv corrupted.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Thresholds getThresholds() {
        return this.currentThresholds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateRestrictions(Thresholds thresholds) {
        try {
            this.currentThresholds = thresholds;

            //get the restrictions to the correct csv format
            String lendLimit = String.valueOf(thresholds.getLendMoreThanBorrow());
            String incompleteTrade = String.valueOf(thresholds.getMaxIncompleteTrade());
            String weeklyTrade = String.valueOf(thresholds.getMaxWeeklyTrade());
            String numberOfDays = String.valueOf(thresholds.getNumberOfDays());
            String numberOfEdits = String.valueOf(thresholds.getNumberOfEdits());
            String numberOfStats = String.valueOf(thresholds.getNumberOfStats());
            String newLine = lendLimit + "," + incompleteTrade + "," + weeklyTrade + "," + numberOfDays + "," + numberOfEdits + "," + numberOfStats;

            //rewrite the first row and restrictions row
            FileWriter fw = new FileWriter(filepath, false);
            String first_row = "lend_limit,incomplete_limit,weekly_limit,number_of_days,number_of_edits,number_of_stats\n";
            fw.write(first_row + newLine);
            fw.flush();
            fw.close();
            return true;

        } catch (IOException e) {
            System.out.println("No such file exists");

        }
        return false;
    }

}