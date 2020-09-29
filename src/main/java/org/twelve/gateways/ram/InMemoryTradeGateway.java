package org.twelve.gateways.ram;

import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.trade.TradeRepository;

import java.util.*;

/**
 * Pseudo-external storage of trade data.
 */
public class InMemoryTradeGateway implements TradeGateway {

    private final Map<Integer, String[]> tradeMap;
    private final Map<Integer, String[]> timePlaceMap;

    /**
     * Initialize the gateway
     *
     * @param timePlace a key-pair set of a timePlace id and timePlace object
     * @param trades    a key-pair set of a trade id and trade object
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
    public boolean populate(TradeRepository tradeRepository) {
        for (int tradeId : tradeMap.keySet()) {
            String[] trade = tradeMap.get(tradeId);
            String[] timePlace = timePlaceMap.get(tradeId);

            String time = timePlace[0];
            String location = timePlace[1];

            boolean isPerm = Boolean.parseBoolean(trade[0]);

            String[] traderIdsString = trade[1].split(" ");
            List<Integer> traderIds = new ArrayList<>();
            for (String traderId : traderIdsString) {
                traderIds.add(Integer.parseInt(traderId));
            }

            String[] itemIdsString = trade[2].split(";");
            List<List<Integer>> temp = new ArrayList<>();
            for (String itemId : itemIdsString) {
                String[] itemIDsString2 = itemId.split(" ");
                if (itemIDsString2.length == 0) {
                    temp.add(new ArrayList<>());
                } else {
                    List<Integer> itemIds = new ArrayList<>();
                    for (String i : itemIDsString2) {
                        itemIds.add(Integer.parseInt(i));
                    }
                    temp.add(itemIds);
                }
            }

            int editCounter = Integer.parseInt(trade[3]);
            String tradeStatus = trade[4];

            String[] tradeCompString = trade[5].split(" ");
            List<Boolean> tradeComps = new ArrayList<>();
            for (String tradeComp : tradeCompString) {
                tradeComps.add(Boolean.parseBoolean(tradeComp));
            }


            tradeRepository.addToTrades(tradeId, isPerm, traderIds, temp, editCounter, tradeStatus,
                    tradeComps, time, location);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<List<Integer>> itemIds,
                        int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time,
                        String location, boolean newTrade) {
        String[] trade = new String[6];
        trade[0] = String.valueOf(isPermanent);
        if (traderIds.size() == 0) {
            trade[1] = " ";
        } else {
            StringBuilder traderIdsString = new StringBuilder();
            for (int traderId : traderIds) {
                traderIdsString.append(traderId).append(" ");
            }
            trade[1] = traderIdsString.toString();
        }
        if (itemIds.size() == 0) {
            trade[2] = " ";
        } else {
            StringBuilder itemIdsString = new StringBuilder();
            for (List<Integer> ids : itemIds) {
                if (ids.size() != 0) {
                    for (int id : ids) {
                        itemIdsString.append(id).append(" ");
                    }
                } else {
                    itemIdsString.append(" ");
                }
                itemIdsString.append(";");
            }
            trade[2] = itemIdsString.toString();
        }
        trade[3] = String.valueOf(editedCounter);
        trade[4] = tradeStatus;
        if (tradeCompletions.size() == 0) {
            trade[5] = " ";
        } else {
            StringBuilder traderString = new StringBuilder();
            for (boolean tradeComps : tradeCompletions) {
                traderString.append(tradeComps).append(" ");
            }
            trade[5] = traderString.toString();
        }
        String[] timePlace = new String[2];
        timePlace[0] = time;
        timePlace[1] = location;
        tradeMap.put(tradeId, trade);
        timePlaceMap.put(tradeId, timePlace);
        return true;
    }
}
