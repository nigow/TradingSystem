package org.twelve.gateways.ram;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.entities.TradeStatus;
import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.TradeManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTradeGateway implements TradeGateway {

    /**
     * pseudo-external storage of trades
     */
    private final Map<Integer, String[]> tradeMap;

    /**
     * pseudo-external storage of timePlace objects
     */
    private final Map<Integer, String[]> timePlaceMap;

    /**
     * Initialize the gateway
     * @param timePlace a key-pair set of a timePlace id and timePlace object
     * @param trades a key-pair set of a trade id and trade object
     */
    public InMemoryTradeGateway(Map<Integer, String[]> trades,
                                Map<Integer, String[]> timePlace) {
        this.timePlaceMap = timePlace;
        this.tradeMap = trades;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(TradeManager tradeManager) {
        /*
        List<Integer> existingIds = tradeManager.getAllTradesIds();
        for (Trade trade : tradeMap.values()) {
            if (!existingIds.contains(trade.getId())) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                tradeManager.addToTrades(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds()
                        , trade.getEditedCounter(), trade.getStatus().name(),
                        trade.getTradeCompletions(), timePlaceMap.get(trade.getId()).getTime().format(formatter),
                        timePlaceMap.get(trade.getId()).getPlace());
            }
        }*/
        for (int tradeId: tradeMap.keySet()) {
            String[] trade = tradeMap.get(tradeId);
            String[] timePlace = timePlaceMap.get(tradeId);

            String time = timePlace[0];
            String location = timePlace[1];

            boolean isPerm = Boolean.parseBoolean(trade[0]);

            String[] traderIdsString = trade[1].split(" ");
            List<Integer> traderIds = new ArrayList<>();
            for (String traderId: traderIdsString) {
                traderIds.add(Integer.parseInt(traderId));
            }

            String[] itemIdsString = trade[2].split(" ");
            List<Integer> itemIds = new ArrayList<>();
            for (String itemId: itemIdsString) {
                itemIds.add(Integer.parseInt(itemId));
            }

            int editCounter = Integer.parseInt(trade[3]);
            String tradeStatus = trade[4];

            String[] tradeCompString = trade[5].split(" ");
            List<Boolean> tradeComps = new ArrayList<>();
            for (String tradeComp: tradeCompString) {
                tradeComps.add(Boolean.parseBoolean(tradeComp));
            }
            tradeManager.createTrade(tradeId, isPerm, traderIds, itemIds, editCounter, tradeStatus,
                    tradeComps, time, location);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<Integer> itemIds,
                     int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time,
                     String location, boolean newTrade) {
        /*
        Trade trade = new Trade(tradeId, isPermanent, traderIds,
                itemIds, editedCounter, TradeStatus.valueOf(tradeStatus),
                tradeCompletions);
        TimePlace timePlace = new TimePlace(tradeId, LocalDateTime.parse(time), location);
        tradeMap.put(tradeId, trade);
        timePlaceMap.put(tradeId, timePlace);*/
        String[] trade = new String[6];
        trade[0] = String.valueOf(isPermanent);
        if (traderIds.size() == 0) {
            trade[1] = " ";
        }
        else {
            StringBuilder traderIdsString = new StringBuilder();
            for (int traderId: traderIds) {
                traderIdsString.append(tradeId).append(" ");
                trade[1] = traderIdsString.toString();
            }
        }
        if (itemIds.size() == 0) {
            trade[2] = " ";
        }
        else {
            StringBuilder itemIdsString = new StringBuilder();
            for (int itemId: itemIds) {
                itemIdsString.append(itemId).append(" ");
                trade[2] = itemIdsString.toString();
            }
        }
        trade[3] = String.valueOf(editedCounter);
        trade[4] = tradeStatus;
        if (tradeCompletions.size() == 0) {
            trade[5] = " ";
        }
        else {
            StringBuilder traderString = new StringBuilder();
            for (boolean tradeComps: tradeCompletions) {
                traderString.append(tradeComps).append(" ");
                trade[5] = traderString.toString();
            }
        }
        String[] timePlace = new String[2];
        timePlace[0] = time;
        timePlace[1] = location;
        tradeMap.put(tradeId, trade);
        timePlaceMap.put(tradeId, timePlace);
        return true;
    }
}
