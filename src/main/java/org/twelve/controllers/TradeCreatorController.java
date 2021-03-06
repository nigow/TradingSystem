package org.twelve.controllers;

import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.usecases.*;
import org.twelve.usecases.account.AccountRepository;
import org.twelve.usecases.account.WishlistManager;
import org.twelve.usecases.item.ItemManager;
import org.twelve.usecases.system.SessionManager;
import org.twelve.usecases.trade.TradeManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with the trade creation view.
 */
public class TradeCreatorController {
    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final ItemManager itemManager;
    private final WishlistManager wishlistManager;
    private final SessionManager sessionManager;
    private final UseCasePool useCasePool;

    private TradeCreatorPresenter tradeCreatorPresenter;

    /**
     * Create a controller for the trade creation screen.
     *
     * @param useCasePool Repository of use cases.
     */
    public TradeCreatorController(UseCasePool useCasePool) {

        this.tradeManager = useCasePool.getTradeManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.wishlistManager = useCasePool.getWishlistManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Set the presenter for this controller
     *
     * @param tradeCreatorPresenter an instance of a class that implements {@link org.twelve.presenters.TradeCreatorPresenter}
     */
    public void setTradeCreatorPresenter(TradeCreatorPresenter tradeCreatorPresenter) {
        this.tradeCreatorPresenter = tradeCreatorPresenter;
    }

    /**
     * Update all the lists in tradeCreatorPresenter
     *
     * @param username the username of the account being traded with (null if there's no selected account)
     */
    public void updateLists(String username) {
        useCasePool.populateAll();
        if (username != null) {
            List<String> itemsToReceive = new ArrayList<>(itemManager.getApprovedInventoryOfAccountString(accountRepository.getIDFromUsername(username)));
            List<String> peerWishlist = new ArrayList<>(wishlistManager.getWishlistToString(accountRepository.getIDFromUsername(username)));
            tradeCreatorPresenter.setItemsToReceive(itemsToReceive);
            tradeCreatorPresenter.setPeerWishlist(peerWishlist);
        } else {
            tradeCreatorPresenter.setPeerWishlist(new ArrayList<>());
            tradeCreatorPresenter.setItemsToReceive(new ArrayList<>());
        }
        List<String> itemsToGive = new ArrayList<>(itemManager.getApprovedInventoryOfAccountString(sessionManager.getCurrAccountID()));
        tradeCreatorPresenter.setItemsToGive(itemsToGive);

        List<String> allUsers = new ArrayList<>(accountRepository.getTradableAccounts(sessionManager.getCurrAccountUsername()));
        tradeCreatorPresenter.setAllUsers(allUsers);

    }

    /**
     * Processes the data given and passes it to createTrade in tradeManager.
     *
     * @param username        the username of the trade initiator.
     * @param itemLendIndex   the index of the item to lend in the approved inventory list.
     * @param itemBorrowIndex the index of the item to borrow in the approved inventory list.
     * @param isPermanent     whether or not the trade is permanent.
     * @param location        the location of the trade.
     * @param time            the time of the trade.
     */
    public void createTrade(String username, int itemLendIndex, int itemBorrowIndex,
                            boolean isPermanent, String location, LocalDateTime time) {
        List<Integer> itemIDs0 = new ArrayList<>();
        List<Integer> itemIDs1 = new ArrayList<>();
        List<Integer> accountIDs = new ArrayList<>();
        int peerID = accountRepository.getIDFromUsername(username);
        if (itemLendIndex >= 0) {
            itemIDs1.add(itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemLendIndex));
        }
        if (itemBorrowIndex >= 0) {
            itemIDs0.add(itemManager.getApprovedInventoryOfAccount(peerID).get(itemBorrowIndex));
        }
        List<List<Integer>> itemIDs = new ArrayList<>();
        itemIDs.add(itemIDs0);
        itemIDs.add(itemIDs1);
        accountIDs.add(peerID);
        accountIDs.add(sessionManager.getCurrAccountID());
        tradeManager.createTrade(time, location, isPermanent, accountIDs, itemIDs);
    }


}
