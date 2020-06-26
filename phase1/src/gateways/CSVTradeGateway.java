package gateways;

import entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A trade gateway that uses csv files as persistent storage.
 */
public class CSVTradeGateway implements TradeGateway {

    private final File csvFile;

    private final LinkedHashMap<String, Integer> headers = new LinkedHashMap<String, Integer>() {{

        put("trade_id", 0);
        put("trader_one_id", 1);
        put("trader_two_id", 2);
        put("trader_one_items", 3);
        put("trader_two_items", 4);
        put("timeplace", 5);
        put("edit_counter", 6);
        put("last_editor_id", 7);
        put("status", 8);
        put("is_permanent", 9);

    }}; // must be ordered to ensure each header is written into the correct column

    private final Map<Integer, String> trades;

    /**
     * Create a trade gateway that uses csv files as persistent storage.
     * @param csvPath Path to the csv file holding trade data.
     * @throws IOException If the given csv file cannot be accessed.
     */
    public CSVTradeGateway(String csvPath) throws IOException {
        csvFile = new File(csvPath);
        trades = new HashMap<>();

        try {
            Scanner fileReader = new Scanner(csvFile);

            fileReader.nextLine(); // skip the header

            while (fileReader.hasNext()) {

                String[] row = fileReader.nextLine().split(",");

                Integer tradeId = Integer.valueOf(row[headers.get("account_id")]);

                trades.put(tradeId, String.join(",", row));

            }

            fileReader.close();


        } catch (FileNotFoundException e) {

            // todo: file handling behaviour could be improved if I figure out a way to test file system problems
            save();

        }
    }

    private void save() throws IOException {

        FileWriter fileWriter = new FileWriter(csvFile); // create nonexistent file or overwrite outdated file
        fileWriter.write(String.join(",", headers.keySet()));

        for (String line : trades.values()) {

            fileWriter.write(line);

        }

        fileWriter.close();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade findTradeById(int id) {
        if (trades.containsKey(id)) {

            String[] row = trades.get(id).split(",");

            int traderOneId = Integer.parseInt(row[headers.get("trader_one_id")]);
            int traderTwoId = Integer.parseInt(row[headers.get("trader_two_id")]);
            List<Integer> traderOneItems = Arrays.stream(row[headers.get("trader_one_items")].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            List<Integer> traderTwoItems = Arrays.stream(row[headers.get("trader_two_items")].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            int editCounter = Integer.parseInt(row[headers.get("edit_counter")]);
            int lastEditorId = Integer.parseInt(row[headers.get("last_editor_id")]);
            TradeStatus status = TradeStatus.valueOf(row[headers.get("status")]);
            boolean isPermanent = Boolean.parseBoolean(row[headers.get("is_permanent")]);

            Trade trade = new Trade(id, id, isPermanent, traderOneId, traderTwoId, traderOneItems, traderTwoItems, editCounter);
            trade.setLastEditorID(lastEditorId);
            trade.setStatus(status);

            return trade;
        }

        return null;
    }

    @Override
    public TimePlace findTimePlaceById(int id) {

        if (trades.containsKey(id)) {

            String[] timePlaceStr = trades.get(id).split(",")[headers.get("timeplace")].split(" ");
            return new TimePlace(id, LocalDateTime.parse(timePlaceStr[0]), timePlaceStr[1]);

        }
        return null;
    }

    @Override
    public boolean updateTrade(Trade trade, TimePlace timePlace) {

        String backup = trades.get(trade.getId());

        String[] row = {
                String.valueOf(trade.getId()),
                String.valueOf(trade.getTraderOneID()),
                String.valueOf(trade.getTraderTwoID()),
                trade.getItemOneID().stream().map(String::valueOf).collect(Collectors.joining(" ")),
                trade.getItemTwoID().stream().map(String::valueOf).collect(Collectors.joining(" ")),
                timePlace.getTime().truncatedTo(ChronoUnit.MINUTES) + " " + timePlace.getPlace(),
                String.valueOf(trade.getEditedCounter()),
                String.valueOf(trade.getLastEditorID()),
                String.valueOf(trade.getStatus())
        };

        if (trade.getItemTwoID().isEmpty()) row[headers.get("trader_one_items")] = " ";
        if (trade.getItemTwoID().isEmpty()) row[headers.get("trader_two_items")] = " ";

        trades.put(trade.getId(), String.join(",", row));
        try {

            save();

        } catch (IOException e) {

            trades.put(trade.getId(), backup);
            return false;

        }

        return true;

    }

    @Override
    public List<Trade> getAllTrades() {
        List<Trade> allTrades = new ArrayList<>();
        for (Integer id : trades.keySet()) {
            allTrades.add(findTradeById(id));
        }
        return allTrades;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int generateValidId() {
        if (trades.isEmpty()) return 0;
        return Collections.max(trades.keySet()) + 1;
    }
}
