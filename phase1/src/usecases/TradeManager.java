package usecases;

import entities.TimePlace;
import entities.Trade;
import entities.TradeStatus;
import gateways.TradeGateway;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a manager responsible for creating and editing trades
 * @author Isaac
 */

public class TradeManager {
    private Trade trade;
    private TimePlace timePlace;
    private TradeGateway tradeGateway;

    public TradeManager(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
    }

    public TradeManager(TradeGateway tradeGateway, Trade trade) {
        this.tradeGateway = tradeGateway;
        this.trade = trade;
    }


    public void createTrade(LocalDateTime time, String place, Boolean isPermanent,
                            int traderOneID, int traderTwoID, List<Integer> itemOneID,
                            List<Integer> itemTwoID) {
        int id = tradeGateway.generateValidId();
        this.timePlace = new TimePlace(id, time, place);
        this.trade = new Trade(id, id, isPermanent, traderOneID, traderTwoID,
                itemOneID, itemTwoID, 0);
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Need delete trade method in trade gateway
     */
    public void deleteTrade() {

    }

    public void editTimePlace(LocalDateTime time, String place, int editorId) {
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.setLastEditorID(editorId);
        trade.incrementEditedCounter();
        tradeGateway.updateTrade(trade, timePlace);
    }

    public void updateStatus(TradeStatus tradeStatus) {
        trade.setStatus(tradeStatus);
        tradeGateway.updateTrade(trade, timePlace);
    }

    public TimePlace getTimePlace() {
        return this.timePlace;
    }

    public TradeStatus getTradeStatus() {
        return trade.getStatus();
    }

    public List<Trade> getAllItems() {
        return tradeGateway.getAllTrades();
    }

}
