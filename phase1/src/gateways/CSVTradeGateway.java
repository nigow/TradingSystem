package gateways;

import entities.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A trade gateway that uses csv files as persistent storage.
 */
public class CSVTradeGateway implements TradeGateway {

    private final File csvFile;

    private final LinkedHashMap<String, Integer> headers;

    private final Map<Integer, String> trades;

    /**
     * Create a trade gateway that uses csv files as persistent storage.
     * @param csvPath Path to the csv file holding trade data.
     * @throws IOException If the given csv file cannot be accessed.
     */
    public CSVTradeGateway(String csvPath) throws IOException {

        csvFile = new File(csvPath);

        headers = new LinkedHashMap<>();

        headers.put("trade_id", 0);
        headers.put("trader_one_id", 1);
        headers.put("trader_two_id", 2);
        headers.put("trader_one_items", 3);
        headers.put("trader_two_items", 4);
        headers.put("timeplace", 5);
        headers.put("edit_counter", 6);
        headers.put("last_editor_id", 7);
        headers.put("status", 8);
        headers.put("is_permanent", 9);
        headers.put("trader_one_confirmed", 10);
        headers.put("trader_two_confirmed", 11);

        trades = new HashMap<>();

        if (csvFile.length() == 0) { // according to internal docs this handles the directory case

            save();

        } else {

            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            reader.readLine(); // skip header

            String row;
            while ((row = reader.readLine()) != null) {

                String[] col = row.split(",");
                Integer tradeId = Integer.valueOf(col[headers.get("trade_id")]);
                trades.put(tradeId, row);

            }

            reader.close();

        }

    }

    private void save() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        writer.write(String.join(",", headers.keySet()));
        writer.newLine();

        for (String line : trades.values()) {

            writer.write(line);
            writer.newLine();

        }

        writer.close();

    }

    private String generateRow(Trade trade, TimePlace timePlace) {

        String[] col = {
                String.valueOf(trade.getId()),
                String.valueOf(trade.getTraderOneID()),
                String.valueOf(trade.getTraderTwoID()),
                trade.getItemOneIDs().stream().map(String::valueOf).collect(Collectors.joining(" ")),
                trade.getItemTwoIDs().stream().map(String::valueOf).collect(Collectors.joining(" ")),
                //timePlace.getTime().truncatedTo(ChronoUnit.MINUTES) + " " + timePlace.getPlace(),
                timePlace.getTime() + " " + timePlace.getPlace(),
                String.valueOf(trade.getEditedCounter()),
                String.valueOf(trade.getLastEditorID()),
                String.valueOf(trade.getStatus()),
                String.valueOf(trade.isPermanent()),
                String.valueOf(trade.isTraderOneCompleted()),
                String.valueOf(trade.isTraderTwoCompleted())
        };

        if (trade.getItemOneIDs().isEmpty()) col[headers.get("trader_one_items")] = " ";
        if (trade.getItemTwoIDs().isEmpty()) col[headers.get("trader_two_items")] = " ";

        return String.join(",", col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade findTradeById(int id) {
        if (trades.containsKey(id)) {

            String[] col = trades.get(id).split(",");

            int traderOneId = Integer.parseInt(col[headers.get("trader_one_id")]);
            int traderTwoId = Integer.parseInt(col[headers.get("trader_two_id")]);
            List<Integer> traderOneItems = Arrays.stream(col[headers.get("trader_one_items")].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            List<Integer> traderTwoItems = Arrays.stream(col[headers.get("trader_two_items")].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            int editCounter = Integer.parseInt(col[headers.get("edit_counter")]);
            int lastEditorId = Integer.parseInt(col[headers.get("last_editor_id")]);
            TradeStatus status = TradeStatus.valueOf(col[headers.get("status")]);
            boolean isPermanent = Boolean.parseBoolean(col[headers.get("is_permanent")]);
            boolean traderOneConfirmed = Boolean.parseBoolean(col[headers.get("trader_one_confirmed")]);
            boolean traderTwoConfirmed = Boolean.parseBoolean(col[headers.get("trader_two_confirmed")]);

            Trade trade = new Trade(id, id, isPermanent, traderOneId, traderTwoId, traderOneItems, traderTwoItems, editCounter);
            trade.setLastEditorID(lastEditorId);
            trade.setStatus(status);
            trade.setTraderOneCompleted(traderOneConfirmed);
            trade.setTraderTwoCompleted(traderTwoConfirmed);

            return trade;
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TimePlace findTimePlaceById(int id) {

        if (trades.containsKey(id)) {

            String[] timePlaceStr = trades.get(id).split(",")[headers.get("timeplace")].split(" ");
            return new TimePlace(id, LocalDateTime.parse(timePlaceStr[0]), timePlaceStr[1]);

        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateTrade(Trade trade, TimePlace timePlace) {

        String backup = trades.get(trade.getId());

        trades.put(trade.getId(), generateRow(trade, timePlace));
        try {

            save();

        } catch (IOException e) {

            if (backup != null) trades.put(trade.getId(), backup);
            return false;

        }

        return true;

    }

    /**
     * {@inheritDoc}
     */
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
