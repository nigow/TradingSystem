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
    private TradeGateway tradeGateway;

    /**
     * The Constructor for TradeManager
     *
     * @param tradeGateway the gateway dealing with trades
     * @param thresholdRepository Repository for storing all threshold values of the program.
     * @param accountRepository Repository for storing all accounts in the system.
     * @param itemManager the manager dealing with items
     * @param wishlistManager the manager dealing with the wishlist of a user
     */
    public TradeManager(TradeGateway tradeGateway, ThresholdRepository thresholdRepository, AccountRepository accountRepository,
                             ItemManager itemManager, WishlistManager wishlistManager) {
        super(itemManager, accountRepository, thresholdRepository);
        this.wishlistManager = wishlistManager;
        this.tradeGateway = tradeGateway;
        tradeGateway.populate(this);
    }

    /**
     * Switch trade changes to reflect in demo mode.
     * @param tradeGateway An instance of TradeGateway
     */
    void switchToDemoMode(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
        for (Trade trade : trades.values()) {
            updateToGateway(trade, true);
        }
    }

    /**
     * Switch trade changes to reflect in normal mode.
     * @param tradeGateway An instance of TradeGateway
     */
    void switchToNormalMode(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
        trades.clear();
        timePlaces.clear();
        tradeGateway.populate(this);
    }

    //TODO : remove this
    /**
     * Adds a new trade and its timePlace to local storage
     *
     * @param id id of trade and timePlace
     * @param isPermanent if the trade is permanent
     * @param traderIDs list of all ids of the traders
     * @param itemIDs list of all the ids of the items
     * @param editedCounter counter for how many edits were made
     * @param tradeStatus status of the trade
     * @param tradeCompletions The Completion status of the trades
     * @param time the time of the trade
     * @param location the location of the trade
     */
    public void addToTrades(int id, boolean isPermanent, List<Integer> traderIDs, List<Integer> itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.put(id, trade);
        timePlaces.put(id, timePlace);
    }

    /**
     * Updates the given trade to the tradeGateway as an old trade
     *
     * @param trade the object representing a trade
     */
    private void updateToGateway(Trade trade) {
        updateToGateway(trade, false);
    }

    /**
     * Updates the given trade and if it is a new trade to the tradeGateway
     *
     * @param trade the object representing a trade
     * @param newTrade if the trade is a new trade
     */
    private void updateToGateway(Trade trade, boolean newTrade) {
        TimePlace timePlace = getTimePlaceByID(trade.getId());
        tradeGateway.save(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds(),
                trade.getEditedCounter(), trade.getStatus().toString(), trade.getTradeCompletions(),
                timePlace.getTime().toString(), timePlace.getPlace(), newTrade);
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
                            List<Integer> tradersIds, List<Integer> itemsIds) {
        int id = (trades.isEmpty() ? 1 : Collections.max(trades.keySet()) + 1);
        TimePlace timePlace = new TimePlace(id, time, place);
        Trade trade = new Trade(id, isPermanent, tradersIds, itemsIds);
        trades.put(id, trade);
        timePlaces.put(id, timePlace);
        for (int accountID : tradersIds) {
            for (int itemID : itemsTraderGives(trade.getNextTraderID(accountID), trade))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        updateToGateway(trade, true);
        return id;
    }

    /**
     * Adds a new trade and its timePlace that exist in the database
     *
     * @param id id of trade and timePlace
     * @param isPermanent if the trade is permanent
     * @param traderIDs list of all ids of the traders
     * @param itemIDs list of all the ids of the items
     * @param editedCounter counter for how many edits were made
     * @param tradeStatus status of the trade
     * @param tradeCompletions The Completion status of the trades
     * @param time the time of the trade
     * @param location the location of the trade
     */
    public void createTrade(int id, boolean isPermanent, List<Integer> traderIDs, List<Integer> itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.put(id, trade);
        timePlaces.put(id, timePlace);
    }

    /**
     * Initiates a reverse Trade.
     * @param id The trade id
     */
    public int reverseTrade(int id) {
        TimePlace timePlace = getTimePlaceByID(id);
        Trade trade = getTradeByID(id);
        List<Integer> reverseTraders = new ArrayList<>(trade.getTraderIds());
        List<Integer> reverseItems = new ArrayList<>(trade.getItemsIds());
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
        TimePlace timePlace = getTimePlaceByID(tradeID);
        Trade trade = getTradeByID(tradeID);
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.incrementEditedCounter();
        updateToGateway(trade);
    }

    /**
     * A method allowing accounts to reject a trade.
     * @param tradeID The ID of the trade being cancelled.
     */
    public void rejectTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setStatus(TradeStatus.REJECTED);
        updateToGateway(trade);
    }

    /**
     * Confirm the creation of a trade with a given ID
     * @param tradeID The id of the trade being confirmed.
     */
    public void confirmTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setStatus(TradeStatus.CONFIRMED);
//        cancelInvalidTrades(trade);
        exchangeItems(tradeID);
        if (!isPermanent(tradeID)) {
            int new_id = reverseTrade(tradeID);
            confirmTrade(new_id);
        }
        updateToGateway(trade);
    }

    // Cancels trades that have the same items with a confirmed trade.
    // TODO unused method
    private void cancelInvalidTrades(Trade trade) {
        for (Trade t : trades.values()) {
            if (t.getStatus() == TradeStatus.ADMIN_CANCELLED || t.getId() == trade.getId())
                continue;
            for (int item : t.getItemsIds())
                if (trade.getItemsIds().contains(item)) {
                    adminCancelTrade(t.getId());
                    break;
                }
        }
    }

    /**
     * Updates the completion status of this Trade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this Trade as complete
     */
    public void completeTrade(int accountID, int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setCompletedOfTrader(accountID);
        updateToGateway(trade);
    }

    /**
     * remove trade from system
     *
     * @param tradeID trade id
     */
    public void adminCancelTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        if (trade.getStatus() == TradeStatus.CONFIRMED || trade.getStatus() == TradeStatus.COMPLETED)
            unmakeTrade(tradeID);
        trade.setStatus(TradeStatus.ADMIN_CANCELLED);
        updateToGateway(trade);
    }

    private void exchangeItems(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        List <Integer> prev_items = itemsTraderOwns(trade.getTraderIds().get(0), trade);
        for (int i = 0; i < trade.getTraderIds().size(); i++) {
            int accountID = trade.getTraderIds().get(i);
            int nextAccountID = trade.getNextTraderID(accountID);
            List<Integer> temp = itemsTraderOwns(nextAccountID, trade);
            for (int itemID : prev_items) {
                itemManager.updateOwner(itemID, nextAccountID);
            }
            prev_items = temp;
        }
    }

    /**
     * Compares the number of edits done to the trade vs. the restriction limit.
     * @param tradeID ID of the trade.
     */
    public boolean canBeEdited(int tradeID) {
        return getEditedCounter(tradeID) <
                thresholdRepository.getNumberOfEdits() * getTradeByID(tradeID).getTraderIds().size();
    }

    /**
     * An Admin-specific method to undo the creation of a trade
     * @param tradeID The trade id being undone.
     */
    public void unmakeTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        int lastID = trade.getTraderIds().get(trade.getTraderIds().size() - 1);
        List <Integer> prev_items = itemsTraderOwns(lastID, trade);
        for (int i = trade.getTraderIds().size() - 1; i >= 0; i--) {
            int accountID = trade.getTraderIds().get(i);
            int prevAccountID = trade.getPreviousTraderID(accountID);
            List<Integer> temp = itemsTraderOwns(prevAccountID, trade);
            for (int itemID : prev_items) {
                itemManager.updateOwner(itemID, prevAccountID);
            }
            prev_items = temp;
        }
    }
}
