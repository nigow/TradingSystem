package usecases;

import controllers.WishlistController;
import entities.*;
import gateways.TradeGateway;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO javadoc is fucked :D

/**
 * Manager responsible for creating and editing trades.
 *
 * @author Isaac
 */

public class TradeManager extends TradeUtility{

    private final int RETURN_TRADE_DAYS = 30;

    private int generateValidIDCounter;

    private WishlistManager wishlistManager;
    private ItemManager itemManager;

    private TradeGateway tradeGateway;

    public void TradeManager(TradeGateway tradeGateway, AccountRepository accountRepository,
                             ItemManager itemManager, WishlistManager wishlistManager) {
        generateValidIDCounter = 1;
        this.itemUtility = itemManager;
        this.accountRepository = accountRepository;
        this.wishlistManager = wishlistManager;
        this.itemManager = itemManager;
        this.tradeGateway = tradeGateway;
    }

    public int generateValidID() {
        return generateValidIDCounter++;
    }

    /**
     * Creates a new OldTrade object to be edited.
     *
     * @param time           Time of the OldTrade
     * @param place          Location of the OldTrade
     * @param isPermanent    Whether the oldTrade is permanent or not
     */
    public void createTrade(LocalDateTime time, String place, boolean isPermanent,
                            List<Integer> tradersIds, List< List<Integer> > itemsIds) {
        int id = generateValidID(); // TODO use the gateway generator
        TimePlace timePlace = new TimePlace(id, time, place);
        Trade trade = new Trade(id, isPermanent, tradersIds, itemsIds);
        trades.add(trade);
        timePlaces.add(timePlace);
        for (int accountID : tradersIds) {
            for (int itemID : trade.itemsTraderGets(accountID))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        // TODO call save for gateway
    }

    /**
     * Initiates a reverse oldTrade.
     *
     * @param accountManager Manager for editing wishlist
     */
    public void reverseTrade(int id) {
        TimePlace timePlace = getTimePlaceByID(id);
        OldTrade oldTrade = getTradeByID(id);
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
    public void editTimePlace(int tradeID, LocalDateTime time, String place, int editorID) {
        TimePlace timePlace = getTimePlaceByID(tradeID);
        OldTrade oldTrade = getTradeByID(tradeID);
        timePlace.setTime(time);
        timePlace.setPlace(place);
        oldTrade.setLastEditorID(editorID);
        oldTrade.incrementEditedCounter();
        // TODO call gateway save
    }

    /**
     * Updates the status of the oldTrade.
     *
     * @param tradeStatus New status of the oldTrade
     */
    public void updateStatus(int tradeID, TradeStatus tradeStatus) {
        OldTrade oldTrade = getTradeByID(tradeID);
        oldTrade.setStatus(tradeStatus);
        // TODO call gateway save
    }

    /**
     * Returns if it is a accounts turn to edit.
     *
     * @return Whether it's the account's turn to edit.
     */
    public boolean isEditTurn(int accountID, int tradeID) {
        return accountID != getTradeByID(tradeID).getLastEditorID();
    }

    /**
     * Updates the completion status of this oldTrade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this oldTrade as complete
     */
    public void updateCompletion(int accountID, int tradeID) {
        OldTrade oldTrade = getTradeByID(tradeID);
        if (accountID == oldTrade.getTraderOneID())
            oldTrade.setTraderOneCompleted(true);
        else if (accountID == oldTrade.getTraderTwoID())
            oldTrade.setTraderTwoCompleted(true);
        // TODO call gateway save
    }

    /**
     * Completes the action of making a oldTrade.
     *
     * @param oldTrade          OldTrade object representing the oldTrade about to be made
     */
    public void makeTrade(OldTrade oldTrade) {
        for (Integer itemId : oldTrade.getItemOneIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemManager.getApprovedInventoryOfAccount(oldTrade.getTraderOneID()).contains(itemManager.findItemById(itemId))) {
                itemManager.updateOwner(itemManager.findItemById(itemId), oldTrade.getTraderTwoID());
            }
        }
        for (Integer itemId : oldTrade.getItemTwoIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemManager.getApprovedInventoryOfAccount(oldTrade.getTraderTwoID()).contains(itemManager.findItemById(itemId))) {
                itemManager.updateOwner(itemManager.findItemById(itemId), oldTrade.getTraderOneID());
            }
        }
    }

    // TODO unused and broken method
//    /**
//     * Completes the action of reversing a oldTrade which was rejected.
//     *
//     * @param oldTrade          OldTrade object representing the oldTrade about to be rejected
//     * @param accountManager Object for managing accounts
//     * @param itemManager    Object for managing items
//     */
//    public void rejectedTrade(OldTrade oldTrade, AccountManager accountManager, ItemManager itemManager) {
//        Account account = accountManager.getCurrAccount();
//        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderTwoID()).getUsername());
//        for (Integer itemId : oldTrade.getItemOneIDs()) {
//            accountManager.addItemToWishlist(itemId);
//            itemManager.updateOwner(itemManager.findItemById(itemId), oldTrade.getTraderOneID());
//        }
//        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderOneID()).getUsername());
//        for (Integer itemId : oldTrade.getItemTwoIDs()) {
//            accountManager.addItemToWishlist(itemId);
//            itemManager.updateOwner(itemManager.findItemById(itemId), oldTrade.getTraderTwoID());
//        }
//        accountManager.setCurrAccount(account.getUsername());
//    }
}
