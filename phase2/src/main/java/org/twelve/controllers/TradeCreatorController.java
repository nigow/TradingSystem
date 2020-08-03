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
    private final int traderOneId;

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
        this.traderOneId = sessionManager.getCurrAccountID();
    }

    public void setTradeCreatorPresenter(TradeCreatorPresenter tradeCreatorPresenter) {
        this.tradeCreatorPresenter = tradeCreatorPresenter;
    }

    public void updateLists(int accountIndex) {
        List<String> itemsToReceive = new ArrayList<>();
        List<String> itemsToGive = new ArrayList<>();
        List<String> allUsers = new ArrayList<>();
        for (String s : itemManager.getAllInventoryOfAccountString(accountRepository.getAccountIDs().get(accountIndex))) {
            itemsToReceive.add(s);
        }
        for (String s : itemManager.getAllInventoryOfAccountString(sessionManager.getCurrAccountID())) {
            itemsToGive.add(s);
        }
        for (String s : accountRepository.getAccountStrings()) {
            allUsers.add(s);
        }
    }

    public void changeSelectedUser(int accountIndex) {
        String name = accountIndex >= 0 ? accountRepository.getAccountStrings().get(accountIndex) : "";
        tradeCreatorPresenter.setSelectedUser(name);
    }

    public void changeSelectedItemToLend(int itemIndex) {
        String name = itemIndex >= 0 ? itemManager.getAllInventoryOfAccountString(sessionManager.getCurrAccountID()).get(itemIndex) : "";
        tradeCreatorPresenter.setSelectedItemToLend(name);
    }

    public void changeSelectedItemToBorrow(int itemIndex, int accountIndex) {
        String name = itemIndex >= 0 ? itemManager.getAllInventoryOfAccountString(accountIndex).get(itemIndex) : "";
        tradeCreatorPresenter.setSelectedItemToBorrow(name);
    }

    public void createTrade(int accountIndex, int itemLendIndex, int itemBorrowIndex,
                            boolean isPermanent, String location, LocalDateTime time) {
        List<Integer> itemIDs = new ArrayList<>();
        List<Integer> accountIDs = new ArrayList<>();
        int peerID = accountRepository.getAccountIDs().get(accountIndex);
        if (itemLendIndex >= 0) {
            itemIDs.add(itemManager.getAllInventoryOfAccountIDs(sessionManager.getCurrAccountID()).get(itemLendIndex));
        }
        if (itemBorrowIndex >= 0) {
            itemIDs.add(itemManager.getAllInventoryOfAccountIDs(peerID).get(itemBorrowIndex));
        }
        accountIDs.add(peerID, sessionManager.getCurrAccountID());
        tradeManager.createTrade(time, location, isPermanent, accountIDs, itemIDs);
    }


}
