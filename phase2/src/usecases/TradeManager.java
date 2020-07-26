package usecases;

import entities.*;
import gateways.experimental.TradeGateway;

import java.sql.Time;
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

    public void addToTrades(int id, boolean isPermanent, List<Integer> traderIDs, List< List<Integer> > itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.add(trade);
        timePlaces.add(timePlace);
        updateToGateway(trade);
    }

    public void updateToGateway(Trade trade) {
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
                            List<Integer> tradersIds, List< List<Integer> > itemsIds) {
        int id = generateValidID();
        TimePlace timePlace = new TimePlace(id, time, place);
        Trade trade = new Trade(id, isPermanent, tradersIds, itemsIds);
        trades.add(trade);
        timePlaces.add(timePlace);
        for (int accountID : tradersIds) {
            for (int itemID : trade.itemsTraderGets(accountID))
                wishlistManager.removeItemFromWishlist(accountID, itemID);
        }
        updateToGateway(trade);
    }

    /**
     * Initiates a reverse Trade.
     *
     */
    public void reverseTrade(int id) {
        TimePlace timePlace = getTimePlaceByID(id);
        Trade trade = getTradeByID(id);
        List<Integer> reverseTraders = new ArrayList<>();
        reverseTraders.addAll(trade.getTraderIds());
        List< List<Integer> > reverseItems = new ArrayList<>();
        reverseItems.addAll(trade.getItemsIds());
        Collections.reverse(reverseTraders);
        Collections.reverse(reverseItems);
        createTrade(timePlace.getTime().plusDays(RETURN_TRADE_DAYS),
                timePlace.getPlace(), true, reverseTraders, reverseItems);
    }

    /**
     * Changes the TimePlace of the Trade and updates last edit info.
     *
     * @param time     New time of the Trade
     * @param place    New place of the Trade
     * @param editorID ID of the person editing the Trade
     */
    public void editTimePlace(int tradeID, LocalDateTime time, String place, int editorID) {
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
     * Returns if it is a accounts turn to edit.
     *
     * @return Whether it's the account's turn to edit.
     */
    public boolean isEditTurn(int accountID, int tradeID) {
        return getTradeByID(tradeID).isEditTurn(accountID);
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
     * @param trade          Trade object representing the trade about to be made
     */
    public void makeTrade(Trade trade) {
        for (int accountID : trade.getTraderIds()) {
            for (int itemID : trade.itemsTraderGets(accountID)) {
                itemManager.updateOwner(itemManager.findItemById(itemID), accountID);
            }
        }
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
