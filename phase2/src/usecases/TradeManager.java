package usecases;

import entities.*;
import gateways.TradeGateway;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager responsible for creating and editing trades.
 *
 * @author Isaac
 */

public class TradeManager {

    private final int RETURN_TRADE_DAYS = 30;

    /**
     * An object representing a transaction between 2 users.
     */
    private Trade trade;

    /**
     * An object representing the time and place of the trade.
     */
    private TimePlace timePlace;

    /**
     * The gateway for dealing with the storage of accounts.
     */
    private final TradeGateway tradeGateway;

    /**
     * Constructor for TradeManager which Stores a TradeGateway.
     *
     * @param tradeGateway The gateway for dealing with the persistent storage of trades
     */
    public TradeManager(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
    }

    /**
     * Constructor for TradeManager to edit an existing Trade.
     *
     * @param tradeGateway Gateway for dealing with the persistent storage of trades
     * @param trade        Object representing a transaction between 2 users
     * @param timePlace    TimePlace of the trade.
     */
    public TradeManager(TradeGateway tradeGateway, Trade trade, TimePlace timePlace) {
        this.tradeGateway = tradeGateway;
        this.trade = trade;
        this.timePlace = timePlace;
    }

    /**
     * Creates a new Trade object to be edited.
     *
     * @param time           Time of the Trade
     * @param place          Location of the Trade
     * @param isPermanent    Whether the trade is permanent or not
     * @param traderOneID    ID of the first trader
     * @param traderTwoID    ID of the second trader
     * @param itemOneID      List of items trader one is offering
     * @param itemTwoID      List of items trader two is offering
     * @param accountManager Manager for editing wishlist
     */
    public void createTrade(LocalDateTime time, String place, boolean isPermanent,
                            int traderOneID, int traderTwoID, List<Integer> itemOneID,
                            List<Integer> itemTwoID, AccountManager accountManager) {
        int id = tradeGateway.generateValidId();
        this.timePlace = new TimePlace(id, time, place);
        this.trade = new Trade(id, id, isPermanent, traderOneID, traderTwoID,
                itemOneID, itemTwoID, 0);
        tradeGateway.updateTrade(trade, timePlace);
        Account account = accountManager.getCurrAccount();
        accountManager.setCurrAccount(accountManager.getUsernameFromID(traderTwoID));
        for (Integer itemId : itemOneID) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
        }
        accountManager.setCurrAccount(accountManager.getUsernameFromID(traderOneID));
        for (Integer itemId : itemTwoID) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
        }
        accountManager.setCurrAccount(account.getUsername());
    }

    /**
     * Initiates a reverse trade.
     *
     * @param accountManager Manager for editing wishlist
     */
    public void reverseTrade(AccountManager accountManager) {
        createTrade(timePlace.getTime().plusDays(RETURN_TRADE_DAYS), timePlace.getPlace(), true, trade.getTraderOneID(),
                trade.getTraderTwoID(), trade.getItemTwoIDs(), trade.getItemOneIDs(), accountManager);
    }

    /**
     * Changes the TimePlace of the trade and updates last edit info.
     *
     * @param time     New time of the trade
     * @param place    New place of the trade
     * @param editorID ID of the person editing the trade
     */
    public void editTimePlace(LocalDateTime time, String place, int editorID) {
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.setLastEditorID(editorID);
        trade.incrementEditedCounter();
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Updates the status of the trade.
     *
     * @param tradeStatus New status of the trade
     */
    public void updateStatus(TradeStatus tradeStatus) {
        trade.setStatus(tradeStatus);
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Getter for the TimePlace of the trade.
     *
     * @return TimePlace of the trade
     */
    public TimePlace getTimePlace() {
        return this.timePlace;
    }

    /**
     * Gets the status of the trade.
     *
     * @return Current status of the trade
     */
    public TradeStatus getTradeStatus() {
        return trade.getStatus();
    }

    /**
     * Gets the current trade.
     *
     * @return The current trade.
     */
    public Trade getTrade() {
        return trade;
    }

    /**
     * Gets the number of times this trade has been edited.
     *
     * @return The number of times this trade has been edited.
     */
    public int getEditedCounter() {
        return trade.getEditedCounter();
    }

    /**
     * Sets the current trade.
     *
     * @param trade The current trade.
     */
    public void setTrade(Trade trade) {
        this.trade = trade;
        timePlace = tradeGateway.findTimePlaceById(trade.getId());
    }

    /**
     * Returns if trade is rejected.
     *
     * @return Whether the trade is rejected
     */
    public boolean isRejected() {
        return trade.getStatus().equals(TradeStatus.REJECTED);
    }

    /**
     * Returns if trade is confirmed.
     *
     * @return Whether trade is confirmed
     */
    public boolean isConfirmed() {
        return trade.getStatus().equals(TradeStatus.CONFIRMED);
    }

    /**
     * Returns if trade is unconfirmed.
     *
     * @return Whether trade is unconfirmed
     */
    public boolean isUnconfirmed() {
        return trade.getStatus().equals(TradeStatus.UNCONFIRMED);
    }

    /**
     * Returns if trade is completed.
     *
     * @return Whether trade is completed.
     */
    public boolean isCompleted() {
        return trade.getStatus().equals(TradeStatus.COMPLETED);
    }

    /**
     * Returns if it is a accounts turn to edit.
     *
     * @param account The account checked
     * @return Whether it's the account's turn to edit.
     */
    public boolean isEditTurn(Account account) {
        return account.getAccountID() != trade.getLastEditorID();
    }

    /**
     * Returns the current tradeGateway, a gateway dealing with trades.
     *
     * @return the current tradeGateway
     */
    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }

    /**
     * Retrieves a list of all trades in persistent storage.
     *
     * @return List of all trades
     */
    public List<Trade> getAllTrades() {
        return tradeGateway.getAllTrades();
    }


    /**
     * Retrieves all trades stored in persistent storage in string format.
     *
     * @return List of trades in string format
     */
    public List<String> getAllTradesString() {
        List<String> StringTrade = new ArrayList<>();
        for (Trade trade : getAllTrades()) {
            StringTrade.add(trade.toString());
        }
        return StringTrade;
    }

    /**
     * Returns a user-friendly string representation of a trade.
     *
     * @param accountManager Manager for manipulating accounts
     * @param itemManager    Manager for manipulating items
     * @return An user-friendly representation of a trade
     */
    public String tradeAsString(AccountManager accountManager, ItemManager itemManager) {
        StringBuilder ans = new StringBuilder();
        String username1 = accountManager.getAccountFromID(
                trade.getTraderOneID()).getUsername();
        String username2 = accountManager.getAccountFromID(
                trade.getTraderTwoID()).getUsername();

        if (trade.getItemOneIDs().size() > 0 && trade.getItemTwoIDs().size() > 0) {
            ans.append("Type: Two-way ");
            ans.append("\nAccount 1: ").append(username1).append("\nAccount 2: ").append(username2);
        } else {
            ans.append("Type: One-way ");
            if (trade.getItemOneIDs().size() > 0) {
                ans.append("\nBorrower: ").append(username2).append("\nLender: ").append(username1);
            } else {
                ans.append("\nBorrower: ").append(username1).append("\nLender: ").append(username2);
            }

        }
        ans.append("\nStatus: ").append(trade.getStatus().toString()).append(" ");
        ans.append("\nType: ");
        ans.append(trade.isPermanent() ? "Permanent " : "Temporary ");
        ans.append("\nLocation: ").append(timePlace.getPlace()).append(" ");
        ans.append("\nTime: ").append(timePlace.getTime()).append(" ");

        if (trade.getItemOneIDs().size() > 0 && trade.getItemTwoIDs().size() > 0) {
            ans.append("\nTrader 1 Items: ");
            String separator = "";
            for (Integer tradeId : trade.getItemOneIDs()) {
                ans.append(separator).append(itemManager.getItemById(tradeId).toString());
                separator = ", ";
            }
            separator = "";
            ans.append("\nTrader 2 Items: ");
            for (Integer tradeId : trade.getItemTwoIDs()) {
                ans.append(separator).append(itemManager.getItemById(tradeId).toString());
                separator = ", ";
            }
        } else {
            ans.append("\nItem being borrowed/lent: ");
            String separator = "";
            for (Integer tradeId : trade.getItemOneIDs()) {
                ans.append(separator).append(itemManager.getItemById(tradeId).toString());
                separator = ", ";
            }
            for (Integer tradeId : trade.getItemTwoIDs()) {
                ans.append(separator).append(itemManager.getItemById(tradeId).toString());
                separator = ", ";
            }
        }

        return ans.toString();
    }

    /**
     * Returns whether this trade is temporary or permanent.
     *
     * @return Whether this trade is temporary or permanent
     */
    public boolean isPermanent() {
        return trade.isPermanent();
    }

    /**
     * Updates the completion status of this trade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this trade as complete
     */
    public void updateCompletion(int accountID) {
        if (accountID == trade.getTraderOneID())
            trade.setTraderOneCompleted(true);
        else if (accountID == trade.getTraderTwoID())
            trade.setTraderTwoCompleted(true);
        tradeGateway.updateTrade(trade, timePlace);
    }

    /**
     * Returns the date and time of this trade.
     *
     * @return Date and time of this trade
     */
    public LocalDateTime getDateTime() {
        return timePlace.getTime();
    }

}