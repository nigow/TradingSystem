package org.twelve.controllers;


import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.usecases.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with the trade creation view.
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class TradeCreatorController {
    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final ItemManager itemManager;
    private final WishlistManager wishlistManager;
    private final SessionManager sessionManager;

    private TradeCreatorPresenter tradeCreatorPresenter;

    /**
     * Create a controller for the trade creation screen.
     *
     * @param useCasePool           Repository of use cases.
     */
    public TradeCreatorController(UseCasePool useCasePool) {

        this.tradeManager = useCasePool.getTradeManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.wishlistManager = useCasePool.getWishlistManager();
        this.sessionManager = useCasePool.getSessionManager();
    }

    /**
     * Set the presenter for this controller
     * @param tradeCreatorPresenter the presenter that is set
     */
    public void setTradeCreatorPresenter(TradeCreatorPresenter tradeCreatorPresenter) {
        this.tradeCreatorPresenter = tradeCreatorPresenter;
    }

    /**
     * Update all the lists in tradeCreatorPresenter
     * @param username the username of the account being traded with (null if there's no selected account)
     */
    public void updateLists(String username) {
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
     * @param username the username of the trade initiator.
     * @param itemLendIndex the index of the item to lend in the approved inventory list.
     * @param itemBorrowIndex the index of the item to borrow in the approved inventory list.
     * @param isPermanent whether or not the trade is permanent.
     * @param location the location of the trade.
     * @param time the time of the trade.
     */
    public void createTrade(String username, int itemLendIndex, int itemBorrowIndex,
                            boolean isPermanent, String location, LocalDateTime time) {
        List<Integer> itemIDs = new ArrayList<>();
        List<Integer> accountIDs = new ArrayList<>();
        int peerID = accountRepository.getIDFromUsername(username);
        if (itemLendIndex >= 0) {
            itemIDs.add(itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemLendIndex));
        }
        if (itemBorrowIndex >= 0) {
            itemIDs.add(itemManager.getApprovedInventoryOfAccount(peerID).get(itemBorrowIndex));
        }
        accountIDs.add(peerID);
        accountIDs.add(sessionManager.getCurrAccountID());
        tradeManager.createTrade(time, location, isPermanent, accountIDs, itemIDs);
    }


}
