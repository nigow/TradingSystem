package usecases;

import entities.*;
import gateways.TradeGateway;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public void createTrade(LocalDateTime time, String place, boolean isPermanent,
                            int traderOneID, int traderTwoID, List<Integer> itemOneID,
                            List<Integer> itemTwoID) {
        int id = tradeGateway.generateValidId();
        this.timePlace = new TimePlace(id, time, place);
        this.trade = new Trade(id, id, isPermanent, traderOneID, traderTwoID,
                itemOneID, itemTwoID, 0);
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Initiates a reverse trade
     */
    public void reverseTrade() {
        int id = tradeGateway.generateValidId();
        this.timePlace = new TimePlace(id, timePlace.getTime().plusDays(30), timePlace.getPlace());
        this.trade = new Trade(id, id, true, trade.getTraderOneID(),
                trade.getTraderTwoID(), trade.getItemTwoIDs(), trade.getItemOneIDs(), 0);
        tradeGateway.updateTrade(this.trade, this.timePlace);
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

    /**
     * @return The current trade.
     */
    public Trade getTrade() {
        return trade;
    }

    /**
     * @param trade The current trade.
     */
    public void setTrade(Trade trade) {
        this.trade = trade;
        timePlace = tradeGateway.findTimePlaceById(trade.getId());
    }

    /**
     * @return Whether the trade is rejected
     */
    public boolean isRejected() {
        return trade.getStatus().equals(TradeStatus.REJECTED);
    }

    /**
     * @return Whether trade is confirmed
     */
    public boolean isConfirmed() {
        return trade.getStatus().equals(TradeStatus.CONFIRMED);
    }

    /**
     * @return Whether trade is unconfirmed
     */
    public boolean isUnconfirmed() {
        return trade.getStatus().equals(TradeStatus.CONFIRMED);
    }

    /**
     * @return Whether trade is completed.
     */
    public boolean isCompleted() {
        return trade.getStatus().equals(TradeStatus.COMPLETED);
    }

    /**
     * @param account The account checked
     * @return Whether it's the account's turn to edit.
     */
    public boolean isEditTurn(Account account) {
        return account.getAccountID() != trade.getLastEditorID();
    }

    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }
    /**
     * Retrieves a list of all trades in persistent storage
     * @return List of all trades
     */
    public List<Trade> getAllTrades() {
        return tradeGateway.getAllTrades();
    }



    /**
     * Retrieves all trades stored in persistent storage in string format
     * @return List of trades in string format
     */
    public List<String> getAllTradesString() {
        List<String> StringTrade = new ArrayList<>();
        for (Trade trade : getAllTrades()) {
            StringTrade.add(trade.toString());
        }
        return StringTrade;
    }

    // TODO add implementation to tradeAsString  -maryam

    /**
     * @param accountManager: An accountManager instance
     * @return A friendly user representation of a string.
     */
    public String tradeAsString(AccountManager accountManager) {
        String ans = "";
        String username1 = accountManager.getAccountFromID(
                trade.getTraderOneID()).getUsername();
        String username2 = accountManager.getAccountFromID(
                trade.getTraderTwoID()).getUsername();

        if (trade.getItemOneIDs().size() > 0 && trade.getItemTwoIDs().size() > 0) {
            ans += "Type: Two-way ";
            ans += "Account 1: " + username1 + " Account 2: " + username2;
        }
        else {
            ans += "Type: One-way ";
            if (trade.getItemOneIDs().size() > 0) {
                ans += "Borrower: " + username1 + " Lender: " + username2;
            }
            else {
                ans += "Borrower: " + username2 + " Lender " + username1;
            }

        }
        ans += "Status: " + trade.getStatus().toString();
        ans += "Type: ";
        ans += trade.isPermanent() ? "Permanent": "Temporary";
        ans += "Location: " + timePlace.getPlace();
        ans += "Time: " + timePlace.getTime();


        return ans;
    }

    public boolean isPermanent() {
        return trade.isPermanent();
    }

    public void updateCompletion(int accountID) {
        if (accountID == trade.getTraderOneID())
            trade.setTraderOneCompleted(true);
        else if (accountID == trade.getTraderTwoID())
            trade.setTraderTwoCompleted(true);
    }

}
