package org.twelve.usecases;

import org.twelve.entities.*;
import org.twelve.gateways.TradeGateway;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manager responsible for creating and editing trades.
 *
 * @author Isaac
 */

public class TradeManager extends TradeUtility{

    private final WishlistManager wishlistManager;
    private final ItemManager itemManager;

    /**
     * The Constructor for TradeManager
     *
     * @param tradeGateway the gateway dealing with trades
     * @param thresholdRepository Repository for storing all threshold values of the program.
     * @param wishlistManager the manager dealing with the wishlist of a user
     */
    public TradeManager(TradeGateway tradeGateway, ThresholdRepository thresholdRepository, WishlistManager wishlistManager, TradeRepository tradeRepository, ItemManager itemManager) {
        super(thresholdRepository, tradeRepository);
        this.wishlistManager = wishlistManager;
        this.itemManager = itemManager;
    }

    /**
     * Creates a new Trade object to be edited.
     *
     * @param time          Time of the Trade
     * @param place         Location of the Trade
     * @param isPermanent   Whether the Trade is permanent or not
     * @param tradersIds    An ordered list of each trader participating
     * @param itemsIds      The items involved in this trade.
     */
    public int createTrade(LocalDateTime time, String place, boolean isPermanent,
                            List<Integer> tradersIds, List< List<Integer> > itemsIds) {
        int id = tradeRepository.createTrade(time, place, isPermanent, tradersIds, itemsIds);
        Trade trade = tradeRepository.getTradeByID(id);
        for (int accountID : tradersIds) {
            for (int itemID : tradeRepository.itemsTraderGives(trade.getNextTraderID(accountID), id))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        return id;
    }


    /**
     * Initiates a reverse Trade.
     * @param id The trade id
     */
    public int reverseTrade(int id) {
        TimePlace timePlace = tradeRepository.getTimePlaceByID(id);
        Trade trade = tradeRepository.getTradeByID(id);
        List<Integer> reverseTraders = new ArrayList<>(trade.getTraderIds());
        List< List<Integer> > reverseItems = new ArrayList<>();
        int n = trade.getItemsIds().size();
        for (int i = 0; i < n - 1; i++)
            reverseItems.add(trade.getItemsIds().get(n - 2 - i)); // TODO copy these instead of aliasing but it should be fine for now
        reverseItems.add(trade.getItemsIds().get(n - 1));
        Collections.reverse(reverseTraders);
        return createTrade(timePlace.getTime().plusDays(thresholdRepository.getNumberOfDays()),
                timePlace.getPlace(), true, reverseTraders, reverseItems);
    }

    /**
     * Changes the TimePlace of the Trade and updates last edit info.
     *
     * @param tradeID  The ID of the trade being edite.d
     * @param time     New time of the Trade
     * @param place    New place of the Trade
     */
    public void editTimePlace(int tradeID, LocalDateTime time, String place) {
        TimePlace timePlace = tradeRepository.getTimePlaceByID(tradeID);
        Trade trade = tradeRepository.getTradeByID(tradeID);
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.incrementEditedCounter();
        tradeRepository.updateToGateway(trade, false);
    }

    /**
     * A method allowing accounts to reject a trade.
     * @param tradeID The ID of the trade being cancelled.
     */
    public void rejectTrade(int tradeID) {
        Trade trade = tradeRepository.getTradeByID(tradeID);
        trade.setStatus(TradeStatus.REJECTED);
        tradeRepository.updateToGateway(trade, false);
    }

    /**
     * Confirm the creation of a trade with a given ID
     * @param tradeID The id of the trade being confirmed.
     */
    public void confirmTrade(int tradeID) {
        Trade trade = tradeRepository.getTradeByID(tradeID);
        trade.setStatus(TradeStatus.CONFIRMED);
//        cancelInvalidTrades(trade);
        exchangeItems(tradeID);
        if (!tradeRepository.isPermanent(tradeID)) {
            int new_id = reverseTrade(tradeID);
            confirmTrade(new_id);
        }
        tradeRepository.updateToGateway(trade, false);
    }

    /**
     * Updates the completion status of this Trade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this Trade as complete
     */
    public void completeTrade(int accountID, int tradeID) {
        Trade trade = tradeRepository.getTradeByID(tradeID);
        trade.setCompletedOfTrader(accountID);
        tradeRepository.updateToGateway(trade, false);
    }

    /**
     * remove trade from system
     *
     * @param tradeID trade id
     */
    public void adminCancelTrade(int tradeID) {
        Trade trade = tradeRepository.getTradeByID(tradeID);
        if (trade.getStatus() == TradeStatus.CONFIRMED || trade.getStatus() == TradeStatus.COMPLETED)
            unmakeTrade(tradeID);
        trade.setStatus(TradeStatus.ADMIN_CANCELLED);
        tradeRepository.updateToGateway(trade, false);
    }

    private void exchangeItems(int tradeID) {
        Trade trade = tradeRepository.getTradeByID(tradeID);
        for (int i = 0; i < trade.getTraderIds().size(); i++) {
            int accountID = trade.getTraderIds().get(i);
            int nextAccountID = trade.getNextTraderID(accountID);
            for (int itemID : trade.getItemsIds().get(i))
                itemManager.updateOwner(itemID, nextAccountID);
        }
    }

    /**
     * Compares the number of edits done to the trade vs. the restriction limit.
     * @param tradeID ID of the trade.
     */
    public boolean canBeEdited(int tradeID) {
        return tradeRepository.getEditedCounter(tradeID) <
                thresholdRepository.getNumberOfEdits() * tradeRepository.getTradeByID(tradeID).getTraderIds().size();
    }

    /**
     * An Admin-specific method to undo the creation of a trade
     * @param tradeID The trade id being undone.
     */
    public void unmakeTrade(int tradeID) {
        Trade trade = tradeRepository.getTradeByID(tradeID);
        for (int i = 0; i < trade.getTraderIds().size(); i++) {
            int accountID = trade.getTraderIds().get(i);
            int prevAccountID = trade.getPreviousTraderID(accountID);
            for (int itemID : trade.getItemsIds().get(i))
                itemManager.updateOwner(itemID, prevAccountID);
        }
    }
}
