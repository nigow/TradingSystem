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

public class TradeManager extends TradeUtility{

    private final int RETURN_TRADE_DAYS = 30;

    /**
     * An object representing a transaction between 2 users.
     */
    private OldTrade oldTrade;

    /**
     * An object representing the time and place of the oldTrade.
     */
    private TimePlace timePlace;

    /**
     * The gateway for dealing with the storage of accounts.
     */

    /**
     * Constructor for OldTradeManager which Stores a TradeGateway.
     *
     * @param tradeGateway The gateway for dealing with the persistent storage of trades
     */
    public OldTradeManager(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
    }

    /**
     * Constructor for OldTradeManager to edit an existing OldTrade.
     *
     * @param tradeGateway Gateway for dealing with the persistent storage of trades
     * @param oldTrade        Object representing a transaction between 2 users
     * @param timePlace    TimePlace of the oldTrade.
     */
    public OldTradeManager(TradeGateway tradeGateway, OldTrade oldTrade, TimePlace timePlace) {
        this.tradeGateway = tradeGateway;
        this.oldTrade = oldTrade;
        this.timePlace = timePlace;
    }

    /**
     * Creates a new OldTrade object to be edited.
     *
     * @param time           Time of the OldTrade
     * @param place          Location of the OldTrade
     * @param isPermanent    Whether the oldTrade is permanent or not
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
        this.oldTrade = new OldTrade(id, id, isPermanent, traderOneID, traderTwoID,
                itemOneID, itemTwoID, 0);
        tradeGateway.updateTrade(oldTrade, timePlace);
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
     * Initiates a reverse oldTrade.
     *
     * @param accountManager Manager for editing wishlist
     */
    public void reverseTrade(AccountManager accountManager) {
        createTrade(timePlace.getTime().plusDays(RETURN_TRADE_DAYS), timePlace.getPlace(), true, oldTrade.getTraderOneID(),
                oldTrade.getTraderTwoID(), oldTrade.getItemTwoIDs(), oldTrade.getItemOneIDs(), accountManager);
    }

    /**
     * Changes the TimePlace of the oldTrade and updates last edit info.
     *
     * @param time     New time of the oldTrade
     * @param place    New place of the oldTrade
     * @param editorID ID of the person editing the oldTrade
     */
    public void editTimePlace(LocalDateTime time, String place, int editorID) {
        timePlace.setTime(time);
        timePlace.setPlace(place);
        oldTrade.setLastEditorID(editorID);
        oldTrade.incrementEditedCounter();
        tradeGateway.updateTrade(oldTrade, timePlace);
    }

    /**
     * Updates the status of the oldTrade.
     *
     * @param tradeStatus New status of the oldTrade
     */
    public void updateStatus(TradeStatus tradeStatus) {
        oldTrade.setStatus(tradeStatus);
        tradeGateway.updateTrade(oldTrade, timePlace);
    }

    /**
     * Getter for the TimePlace of the oldTrade.
     *
     * @return TimePlace of the oldTrade
     */
    public TimePlace getTimePlace() {
        return this.timePlace;
    }

    /**
     * Gets the status of the oldTrade.
     *
     * @return Current status of the oldTrade
     */
    public TradeStatus getTradeStatus() {
        return oldTrade.getStatus();
    }

    /**
     * Gets the current oldTrade.
     *
     * @return The current oldTrade.
     */
    public OldTrade getOldTrade() {
        return oldTrade;
    }

    /**
     * Gets the number of times this oldTrade has been edited.
     *
     * @return The number of times this oldTrade has been edited.
     */
    public int getEditedCounter() {
        return oldTrade.getEditedCounter();
    }

    /**
     * Sets the current oldTrade.
     *
     * @param oldTrade The current oldTrade.
     */
    public void setOldTrade(OldTrade oldTrade) {
        this.oldTrade = oldTrade;
        timePlace = tradeGateway.findTimePlaceById(oldTrade.getId());
    }

    /**
     * Returns if oldTrade is rejected.
     *
     * @return Whether the oldTrade is rejected
     */
    public boolean isRejected() {
        return oldTrade.getStatus().equals(TradeStatus.REJECTED);
    }

    /**
     * Returns if oldTrade is confirmed.
     *
     * @return Whether oldTrade is confirmed
     */
    public boolean isConfirmed() {
        return oldTrade.getStatus().equals(TradeStatus.CONFIRMED);
    }

    /**
     * Returns if oldTrade is unconfirmed.
     *
     * @return Whether oldTrade is unconfirmed
     */
    public boolean isUnconfirmed() {
        return oldTrade.getStatus().equals(TradeStatus.UNCONFIRMED);
    }

    /**
     * Returns if oldTrade is completed.
     *
     * @return Whether oldTrade is completed.
     */
    public boolean isCompleted() {
        return oldTrade.getStatus().equals(TradeStatus.COMPLETED);
    }

    /**
     * Returns if it is a accounts turn to edit.
     *
     * @param account The account checked
     * @return Whether it's the account's turn to edit.
     */
    public boolean isEditTurn(Account account) {
        return account.getAccountID() != oldTrade.getLastEditorID();
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
    public List<OldTrade> getAllTrades() {
        return tradeGateway.getAllTrades();
    }


    /**
     * Retrieves all trades stored in persistent storage in string format.
     *
     * @return List of trades in string format
     */
    public List<String> getAllTradesString() {
        List<String> StringTrade = new ArrayList<>();
        for (OldTrade oldTrade : getAllTrades()) {
            StringTrade.add(oldTrade.toString());
        }
        return StringTrade;
    }

    /**
     * Returns whether this oldTrade is temporary or permanent.
     *
     * @return Whether this oldTrade is temporary or permanent
     */
    public boolean isPermanent() {
        return oldTrade.isPermanent();
    }

    /**
     * Updates the completion status of this oldTrade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this oldTrade as complete
     */
    public void updateCompletion(int accountID) {
        if (accountID == oldTrade.getTraderOneID())
            oldTrade.setTraderOneCompleted(true);
        else if (accountID == oldTrade.getTraderTwoID())
            oldTrade.setTraderTwoCompleted(true);
        tradeGateway.updateTrade(oldTrade, timePlace);
    }

    /**
     * Returns the date and time of this oldTrade.
     *
     * @return Date and time of this oldTrade
     */
    public LocalDateTime getDateTime() {
        return timePlace.getTime();
    }

    /**
     * Completes the action of making a oldTrade.
     *
     * @param oldTrade          OldTrade object representing the oldTrade about to be made
     * @param accountManager Manager for accounts
     * @param itemManager    Manager for items
     */
    public void makeTrade(OldTrade oldTrade, AccountManager accountManager, ItemManager itemManager) {
        Account account = accountManager.getCurrAccount();
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderTwoID()).getUsername());
        for (Integer itemId : oldTrade.getItemOneIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemManager.getApprovedInventoryOfAccount(oldTrade.getTraderOneID()).contains(itemManager.getItemById(itemId))) {
                itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderTwoID());
            }
        }
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderOneID()).getUsername());
        for (Integer itemId : oldTrade.getItemTwoIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemManager.getApprovedInventoryOfAccount(oldTrade.getTraderTwoID()).contains(itemManager.getItemById(itemId))) {
                itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderOneID());
            }
        }
        accountManager.setCurrAccount(account.getUsername());
    }

    /**
     * Completes the action of reversing a oldTrade which was rejected.
     *
     * @param oldTrade          OldTrade object representing the oldTrade about to be rejected
     * @param accountManager Object for managing accounts
     * @param itemManager    Object for managing items
     */
    public void rejectedTrade(OldTrade oldTrade, AccountManager accountManager, ItemManager itemManager) {
        Account account = accountManager.getCurrAccount();
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderTwoID()).getUsername());
        for (Integer itemId : oldTrade.getItemOneIDs()) {
            accountManager.addItemToWishlist(itemId);
            itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderOneID());
        }
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderOneID()).getUsername());
        for (Integer itemId : oldTrade.getItemTwoIDs()) {
            accountManager.addItemToWishlist(itemId);
            itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderTwoID());
        }
        accountManager.setCurrAccount(account.getUsername());
    }

}
