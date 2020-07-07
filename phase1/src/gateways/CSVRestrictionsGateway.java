package gateways;

import entities.Restrictions;

import java.io.*;
import java.util.regex.Pattern;

/**
 * A restriction gateway that uses csv files as persistent storage.
 *
 * @author Tairi
 */
public class CSVRestrictionsGateway implements RestrictionsGateway {

    /**
     * The current restrictions of the system consists of lend, weekly trades and incomplete trade limits
     */
    private Restrictions currentRestriction;


    /**
     * Path to the CSV file that stores the current restrictions
     */
    private final String filepath;


    /**
     * Constructor for CSVRoleGateway that construct the current restrictions
     *
     * @param filepath the filepath to the csv file
     */
    public CSVRestrictionsGateway(String filepath) throws IOException {
        this.filepath = filepath;

        //pre-processing of file reading
        File f = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();

        //skip the first row
        line = br.readLine();

        while (line != null && Pattern.matches("^[0-9]+,[0-9]+,[0-9]+$", line)) {
            //only the second line consists of restrictions separated by commas
            String[] lineArray = line.split(",");
            int lendLimit = Integer.parseInt(lineArray[0]);
            int incompleteTrade = Integer.parseInt(lineArray[1]);
            int weeklyTrade = Integer.parseInt(lineArray[2]);

            this.currentRestriction = new Restrictions(lendLimit, incompleteTrade, weeklyTrade);

            line = br.readLine();

        }

        br.close();

        // currentRestriction being null here means file was corrupted
        if (currentRestriction == null) throw new IOException("Restrictions.csv corrupted.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Restrictions getRestrictions() {
        return this.currentRestriction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateRestrictions(Restrictions restrictions) {
        try {
            this.currentRestriction = restrictions;

            //get the restrictions to the correct csv format
            String lendLimit = String.valueOf(restrictions.getLendMoreThanBorrow());
            String incompleteTrade = String.valueOf(restrictions.getMaxIncompleteTrade());
            String weeklyTrade = String.valueOf(restrictions.getMaxWeeklyTrade());
            String newLine = lendLimit + "," + incompleteTrade + "," + weeklyTrade;

            //rewrite the first row and restrictions row
            FileWriter fw = new FileWriter(filepath, false);
            String first_row = "lend_limit,incomplete_limit,weekly_limit\n";
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
