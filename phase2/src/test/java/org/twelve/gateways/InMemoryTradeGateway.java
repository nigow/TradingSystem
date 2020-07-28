package test.java.org.twelve.gateways;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.usecases.TradeManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InMemoryTradeGateway implements org.twelve.gateways.TradeGateway {

    public final Map<Integer, org.twelve.entities.Trade> tradeMap;
    public final Map<Integer, org.twelve.entities.TimePlace> timePlaceMap;

    public InMemoryTradeGateway(Map<Integer, org.twelve.entities.Trade> trades,
                                Map<Integer, org.twelve.entities.TimePlace> timePlace) {
        this.timePlaceMap = timePlace;
        this.tradeMap = trades;
    }

    @Override
    public void populate(TradeManager tradeManager) {

    }

    @Override
    public void save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<Integer> itemIds,
                     int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time,
                     String location) {
        org.twelve.entities.Trade trade = new Trade(tradeId, isPermanent, traderIds,
                itemIds, editedCounter, org.twelve.entities.TradeStatus.valueOf(org.twelve.entities.TradeStatus),
                tradeCompletions);
        org.twelve.entities.TimePlace timePlace = new TimePlace(tradeId, LocalDateTime.parse(time), location);
        tradeMap.put(tradeId, trade);
        timePlaceMap.put(tradeId, timePlace);
    }
}
