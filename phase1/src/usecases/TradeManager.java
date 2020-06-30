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

    /**
     * An object representing a transaction between 2 users
     */
    private Trade trade;

    /**
     * An object representing the time and place of the trade
     */
    private TimePlace timePlace;

    /**
     * The gateway for dealing with the storage of accounts
     */
    private TradeGateway tradeGateway;

    /**
     * Constructor for TradeManager which Stores a TradeGateway
     * @param tradeGateway The gateway for dealing with the persistent storage of trades
     */
    public TradeManager(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
    }

    /**
     * Constructor for TradeManager to edit an existing Trade
     * @param tradeGateway The gateway for dealing with the persistent storage of trades
     * @param trade An object representing a transaction between 2 users
     */
    public TradeManager(TradeGateway tradeGateway, Trade trade, TimePlace timePlace) {
        this.tradeGateway = tradeGateway;
        this.trade = trade;
        this.timePlace = timePlace;
    }


    /**
     * Creates a new Trade object to be edited
     * @param time The time of the Trade
     * @param place The location of the Trade
     * @param isPermanent If the trade is permanent or not
     * @param traderOneID The id of the first trader
     * @param traderTwoID The id of the second trader
     * @param itemOneID A list of items trader one is offering
     * @param itemTwoID A list of items trader two is offering
     */
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

    /**
     * Changes the TimePlace of the trade and updates last edit info
     * @param time New time of the trade
     * @param place New place of the trade
     * @param editorId The id of the person editing the trade
     */
    public void editTimePlace(LocalDateTime time, String place, int editorId) {
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.setLastEditorID(editorId);
        trade.incrementEditedCounter();
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Updates the status of the trade
     * @param tradeStatus The new status of the trade
     */
    public void updateStatus(TradeStatus tradeStatus) {
        trade.setStatus(tradeStatus);
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Getter for the TimePlace of the trade
     * @return TimePlace of the trade
     */
    public TimePlace getTimePlace() {
        return this.timePlace;
    }

    /**
     * Getter for the status of the trade
     * @return Current status of the trade
     */
    public TradeStatus getTradeStatus() {
        return trade.getStatus();
    }

    public Trade getTrade() {
        return trade;
    }

    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }
    /**
     * Retrieves a list of all trades in persistent storage
     * @return List of all trades
     */
    public List<Trade> getAllItems() {
        return tradeGateway.getAllTrades();
    }

}
