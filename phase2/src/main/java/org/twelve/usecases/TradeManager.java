package org.twelve.usecases;

import org.twelve.entities.*;
import org.twelve.gateways.RestrictionsGateway;
import org.twelve.gateways.experimental.TradeGateway;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO javadoc is fucked :D

/**
 * Manager responsible for creating and editing trades.
 *
 * @author Isaac
 */

public class TradeManager extends TradeUtility{

    private WishlistManager wishlistManager;

    private TradeGateway tradeGateway;

    // TODO this is so bad
    private RestrictionsGateway restrictionsGateway;

    public void TradeManager(TradeGateway tradeGateway, RestrictionsGateway restrictionsGateway, AccountRepository accountRepository,
                             ItemManager itemManager, WishlistManager wishlistManager) {
        this.itemManager = itemManager;
        this.accountRepository = accountRepository;
        this.wishlistManager = wishlistManager;
        this.tradeGateway = tradeGateway;
        this.restrictionsGateway = restrictionsGateway;
    }

    public void addToTrades(int id, boolean isPermanent, List<Integer> traderIDs, List<Integer> itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.add(trade);
        timePlaces.add(timePlace);
        updateToGateway(trade);
    }

    private void updateToGateway(Trade trade) {
        TimePlace timePlace = getTimePlaceByID(trade.getId());
        tradeGateway.save(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds(),
                trade.getEditedCounter(), trade.getStatus().toString(), trade.getTradeCompletions(),
                timePlace.getTime().toString(), timePlace.getPlace());
    }

    /**
     * Creates a new Trade object to be edited.
     *
     * @param time           Time of the Trade
     * @param place          Location of the Trade
     * @param isPermanent    Whether the Trade is permanent or not
     */
    public void createTrade(LocalDateTime time, String place, boolean isPermanent,
                            List<Integer> tradersIds, List<Integer> itemsIds) {
        int id = trades.size() + 1;
        TimePlace timePlace = new TimePlace(id, time, place);
        Trade trade = new Trade(id, isPermanent, tradersIds, itemsIds);
        trades.add(trade);
        timePlaces.add(timePlace);
        for (int accountID : tradersIds) {
            for (int itemID : itemsTraderGets(accountID, trade))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        updateToGateway(trade);
    }

    // TODO this is bad
    /**
     * Initiates a reverse Trade.
     *
     */
    public void reverseTrade(int id, Restrictions restrictions) {
        TimePlace timePlace = getTimePlaceByID(id);
        Trade trade = getTradeByID(id);
        List<Integer> reverseTraders = new ArrayList<>();
        reverseTraders.addAll(trade.getTraderIds());
        List<Integer> reverseItems = new ArrayList<>();
        reverseItems.addAll(trade.getItemsIds());
        Collections.reverse(reverseTraders);
        createTrade(timePlace.getTime().plusDays(restrictions.getNumberOfDays()),
                timePlace.getPlace(), true, reverseTraders, reverseItems);
    }

    /**
     * Changes the TimePlace of the Trade and updates last edit info.
     *
     * @param time     New time of the Trade
     * @param place    New place of the Trade
     */
    public void editTimePlace(int tradeID, LocalDateTime time, String place) {
        TimePlace timePlace = getTimePlaceByID(tradeID);
        Trade trade = getTradeByID(tradeID);
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.incrementEditedCounter();
        updateToGateway(trade);
    }

    /**
     * Updates the status of the Trade.
     *
     * @param tradeStatus New status of the Trade
     */
    public void updateStatus(int tradeID, TradeStatus tradeStatus) {
        Trade trade = getTradeByID(tradeID);
        trade.setStatus(tradeStatus);
        updateToGateway(trade);
    }

    /**
     * Updates the completion status of this Trade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this Trade as complete
     */
    public void updateCompletion(int accountID, int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setCompletedOfTrader(accountID, true);
        updateToGateway(trade);
    }

    /**
     * Completes the action of making a trade.
     *
     */
    public void makeTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        for (int accountID : trade.getTraderIds()) {
            for (int itemID : itemsTraderGets(accountID, trade)) {
                itemManager.updateOwner(itemID, accountID);
            }
        }
    }

    // TODO this is bad, and should also be in TradeUtility
    /**
     * Compares the number of edits done to the trade vs. the restriction limit.
     * @param tradeID ID of the trade.
     */
    public boolean canEdit(int tradeID) {
        return getEditedCounter(tradeID) <
                restrictionsGateway.getRestrictions().getNumberOfEdits() * getTradeByID(tradeID).getTraderIds().size();
    }

    // TODO unused and broken method
//    /**
//     * Completes the action of reversing a Trade which was rejected.
//     *
//     * @param trade          Trade object representing the Trade about to be rejected
//     * @param accountManager Object for managing accounts
//     * @param itemManager    Object for managing items
//     */
//    public void rejectedTrade(Trade trade, AccountManager accountManager, ItemManager itemManager) {
//        Account account = accountManager.getCurrAccount();
//        accountManager.setCurrAccount(accountManager.getAccountFromID(trade.getTraderTwoID()).getUsername());
//        for (Integer itemId : trade.getItemOneIDs()) {
//            accountManager.addItemToWishlist(itemId);
//            itemManager.updateOwner(itemManager.findItemById(itemId), trade.getTraderOneID());
//        }
//        accountManager.setCurrAccount(accountManager.getAccountFromID(trade.getTraderOneID()).getUsername());
//        for (Integer itemId : trade.getItemTwoIDs()) {
//            accountManager.addItemToWishlist(itemId);
//            itemManager.updateOwner(itemManager.findItemById(itemId), trade.getTraderTwoID());
//        }
//        accountManager.setCurrAccount(account.getUsername());
//    }
}
