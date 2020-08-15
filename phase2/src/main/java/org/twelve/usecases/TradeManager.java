package org.twelve.usecases;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.entities.TradeStatus;
import org.twelve.gateways.TradeGateway;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final AccountRepository accountRepository;


    /**
     * The Constructor for TradeManager
     *
     * @param accountRepository the gateway dealing with trades
     * @param thresholdRepository Repository for storing all threshold values of the program.
     * @param wishlistManager the manager dealing with the wishlist of a user
     */
    public TradeManager(AccountRepository accountRepository, ThresholdRepository thresholdRepository,
                        WishlistManager wishlistManager, TradeGateway tradeGateway, ItemManager itemManager) {
        super(thresholdRepository, tradeGateway);
        this.wishlistManager = wishlistManager;
        this.itemManager = itemManager;
        this.accountRepository = accountRepository;
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
        int id = createTrade(time, place, isPermanent, tradersIds, itemsIds);
        Trade trade = getTradeByID(id);
        for (int accountID : tradersIds) {
            for (int itemID : itemsTraderGives(trade.getNextTraderID(accountID), id))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        return id;
    }


    /**
     * Initiates a reverse Trade.
     * @param id The trade id
     */
    public int reverseTrade(int id) {
        TimePlace timePlace = getTimePlaceByID(id);
        Trade trade = getTradeByID(id);
        List<Integer> reverseTraders = new ArrayList<>(trade.getTraderIds());
        List< List<Integer> > reverseItems = new ArrayList<>();
        int n = trade.getItemsIds().size();
        for (int i = 0; i < n - 1; i++)
            reverseItems.add(trade.getItemsIds().get(n - 2 - i));
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
        TimePlace timePlace = getTimePlaceByID(tradeID);
        Trade trade = getTradeByID(tradeID);
        timePlace.setTime(time);
        timePlace.setPlace(place);
        trade.incrementEditedCounter();
        updateToGateway(trade, false);
    }

    /**
     * A method allowing accounts to reject a trade.
     * @param tradeID The ID of the trade being cancelled.
     */
    public void rejectTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setStatus(TradeStatus.REJECTED);
        updateToGateway(trade, false);
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
        updateToGateway(trade, false);
    }

    /**
     * Updates the completion status of this Trade according to the user's ID.
     *
     * @param accountID The ID of the account who marked this Trade as complete
     */
    public void completeTrade(int accountID, int tradeID) {
        Trade trade = getTradeByID(tradeID);
        trade.setCompletedOfTrader(accountID);
        updateToGateway(trade, false);
    }

    /**
     * remove trade from system
     *
     * @param tradeIndex An index of the trade counting cancelled & unconfirmed trades.
     */
    public void adminCancelTrade(int tradeIndex) {
        int tradeID = getAllTradesIds().get(tradeIndex);
        Trade trade = getTradeByID(tradeID);
        if (trade.getStatus() == TradeStatus.CONFIRMED || trade.getStatus() == TradeStatus.COMPLETED)
            unmakeTrade(tradeID);
        trade.setStatus(TradeStatus.ADMIN_CANCELLED);
        updateToGateway(trade, false);
    }

    private void exchangeItems(int tradeID) {
        Trade trade = getTradeByID(tradeID);
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
        return getEditedCounter(tradeID) <
                thresholdRepository.getNumberOfEdits() * getTradeByID(tradeID).getTraderIds().size();
    }

    /**
     * An Admin-specific method to undo the creation of a trade
     * @param tradeID The trade id being undone.
     */
    public void unmakeTrade(int tradeID) {
        Trade trade = getTradeByID(tradeID);
        for (int i = 0; i < trade.getTraderIds().size(); i++) {
            int accountID = trade.getTraderIds().get(i);
            for (int itemID : trade.getItemsIds().get(i))
                itemManager.updateOwner(itemID, accountID);
        }
    }

    /**
     * Retrieves all trades stored in persistent storage in string format.
     *
     * @return List of trades in string format
     */
    public List<String> getAllTradesString() {
        List<String> stringTrade = new ArrayList<>();
        for (Trade trade : trades.values()) {
            if (trade.getStatus() != TradeStatus.ADMIN_CANCELLED)
                stringTrade.add(tradeAsString(trade));
        }
        return stringTrade;
    }

    /**
     * Returns a user-friendly string representation of a trade.
     *
     * @param trade The Trade whose representation is being returned
     * @return An user-friendly representation of a trade
     */
    public String tradeAsString(Trade trade) {
        TimePlace timePlace = getTimePlaceByID(trade.getTimePlaceID());
        StringBuilder ans = new StringBuilder();

        boolean space = false;
        for (int id : trade.getTraderIds()) {
            if (space)
                ans.append("\n");
            else
                space = true;
            ans.append("Items being traded by ").append(accountRepository.getUsernameFromID(id)).append(": ");
            boolean hasItem = false;
            for (int itemID : itemsTraderGives(id, trade.getId())) {
                ans.append(itemManager.getItemNameById(itemID));
                hasItem = true;
            }
            if (!hasItem)
                ans.append("-");
        }

        ans.append("\nStatus: ").append(trade.getStatus().toString().toLowerCase());
        ans.append("\nType: ");
        ans.append(trade.isPermanent() ? "permanent" : "temporary");
        ans.append("\nLocation: ").append(timePlace.getPlace());
        ans.append("\nTime: ").append(timePlace.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")));

        return ans.toString();
    }

}
