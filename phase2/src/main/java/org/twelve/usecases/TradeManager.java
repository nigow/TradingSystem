package org.twelve.usecases;

import org.twelve.entities.*;
import org.twelve.gateways.TradeGateway;

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

    private final WishlistManager wishlistManager;
    private final TradeGateway tradeGateway;

    public TradeManager(TradeGateway tradeGateway, ThresholdRepository thresholdRepository, AccountRepository accountRepository,
                             ItemManager itemManager, WishlistManager wishlistManager) {
        super(itemManager, accountRepository, thresholdRepository);
        this.wishlistManager = wishlistManager;
        this.tradeGateway = tradeGateway;
        tradeGateway.populate(this);
    }

    public void addToTrades(int id, boolean isPermanent, List<Integer> traderIDs, List<Integer> itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.add(trade);
        timePlaces.add(timePlace);
    }

    private void updateToGateway(Trade trade) {
        TimePlace timePlace = getTimePlaceByID(trade.getId());
        tradeGateway.save(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds(),
                trade.getEditedCounter(), trade.getStatus().toString(), trade.getTradeCompletions(),
                timePlace.getTime().toString(), timePlace.getPlace(), false);
    }

    private void createToGateway(Trade trade) {
        TimePlace timePlace = getTimePlaceByID(trade.getId());
        tradeGateway.save(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds(),
                trade.getEditedCounter(), trade.getStatus().toString(), trade.getTradeCompletions(),
                timePlace.getTime().toString(), timePlace.getPlace(), true);
    }

    /**
     * Creates a new Trade object to be edited.
     *
     * @param time           Time of the Trade
     * @param place          Location of the Trade
     * @param isPermanent    Whether the Trade is permanent or not
     */
    public int createTrade(LocalDateTime time, String place, boolean isPermanent,
                            List<Integer> tradersIds, List<Integer> itemsIds) {
        int id = trades.size() + 1;
        TimePlace timePlace = new TimePlace(id, time, place);
        Trade trade = new Trade(id, isPermanent, tradersIds, itemsIds);
        trades.add(trade);
        timePlaces.add(timePlace);
        for (int accountID : tradersIds) {
            for (int itemID : itemsOfNextTrader(accountID, trade))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        createToGateway(trade);
        return id;
    }

    /**
     * Initiates a reverse Trade.
     *
     */
    public int reverseTrade(int id) {
        TimePlace timePlace = getTimePlaceByID(id);
        Trade trade = getTradeByID(id);
        List<Integer> reverseTraders = new ArrayList<>();
        reverseTraders.addAll(trade.getTraderIds());
        List<Integer> reverseItems = new ArrayList<>();
        reverseItems.addAll(trade.getItemsIds());
        Collections.reverse(reverseTraders);
        return createTrade(timePlace.getTime().plusDays(thresholdRepository.getNumberOfDays()),
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

    public void rejectTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setStatus(TradeStatus.REJECTED);
        updateToGateway(trade);
    }

    public void confirmTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setStatus(TradeStatus.CONFIRMED);
        makeTrade(tradeID);
        if (!isPermanent(tradeID)) {
            int new_id = reverseTrade(tradeID);
            confirmTrade(new_id);
        }
        updateToGateway(trade);
    }

    /**
     * Updates the completion status of this Trade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this Trade as complete
     */
    public void completeTrade(int accountID, int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setCompletedOfTrader(accountID, true);
        updateToGateway(trade);
    }

    public void adminCancelTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        if (isConfirmed(tradeID) || isCompleted(tradeID))
            unmakeTrade(tradeID);
        trade.setStatus(TradeStatus.ADMIN_CANCELLED);
    }

    /**
     * Completes the action of making a trade.
     *
     */
    public void makeTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        List<Integer> firstAccountItems = itemsTraderHas(trade.getTraderIds().get(0), trade);
        for (int i = 0; i < trade.getTraderIds().size() - 1; i++) {
            int accountID = trade.getTraderIds().get(i);
            for (int itemID : itemsOfNextTrader(accountID, trade)) {
                itemManager.updateOwner(itemID, accountID);
            }
        }
        int lastID = trade.getTraderIds().get(trade.getTraderIds().size() - 1);
        for (int itemID : firstAccountItems)
            itemManager.updateOwner(itemID, lastID);
    }

    /**
     * Compares the number of edits done to the trade vs. the restriction limit.
     * @param tradeID ID of the trade.
     */
    public boolean canBeEdited(int tradeID) {
        return getEditedCounter(tradeID) <
                thresholdRepository.getNumberOfEdits() * getTradeByID(tradeID).getTraderIds().size();
    }

    public void unmakeTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        int lastID = trade.getTraderIds().get(trade.getTraderIds().size() - 1);
        List<Integer> lastAccountItems = itemsTraderHas(lastID, trade);
        for (int i = trade.getTraderIds().size() - 1; i > 0; i--) {
            int accountID = trade.getTraderIds().get(i);
            for (int itemID : itemsOfPreviousTrader(accountID, trade)) {
                itemManager.updateOwner(itemID, accountID);
            }
        }
        for (int itemID : lastAccountItems)
            itemManager.updateOwner(itemID, trade.getTraderIds().get(0));
    }
}
