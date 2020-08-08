package org.twelve.gateways.ram;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.entities.TradeStatus;
import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.TradeManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class InMemoryTradeGateway implements TradeGateway {

    /**
     * pseudo-external storage of trades
     */
    private final Map<Integer, Trade> tradeMap;

    /**
     * pseudo-external storage of timePlace objects
     */
    private final Map<Integer, TimePlace> timePlaceMap;

    /**
     * Initialize the gateway
     * @param timePlace a key-pair set of a timePlace id and timePlace object
     * @param timePlace a key-pair set of a trade id and trade object
     */
    public InMemoryTradeGateway(Map<Integer, Trade> trades,
                                Map<Integer, TimePlace> timePlace) {
        this.timePlaceMap = timePlace;
        this.tradeMap = trades;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(TradeManager tradeManager) {
        List<Integer> existingIds = tradeManager.getAllTradesIds();
        for (Trade trade : tradeMap.values()) {
            if (!existingIds.contains(trade.getId())) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                tradeManager.addToTrades(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds()
                        , trade.getEditedCounter(), trade.getStatus().name(),
                        trade.getTradeCompletions(), timePlaceMap.get(trade.getId()).getTime().format(formatter),
                        timePlaceMap.get(trade.getId()).getPlace());
            }
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
        Trade trade = new Trade(tradeId, isPermanent, traderIds,
                itemIds, editedCounter, TradeStatus.valueOf(tradeStatus),
                tradeCompletions);
        TimePlace timePlace = new TimePlace(tradeId, LocalDateTime.parse(time), location);
        tradeMap.put(tradeId, trade);
        timePlaceMap.put(tradeId, timePlace);
        return true;
    }
}
