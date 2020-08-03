package org.twelve.controllers;

import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.WishlistPresenter;
import org.twelve.usecases.*;

import java.util.ArrayList;
import java.util.List;

public class WishlistController {

    private WishlistManager wishlistManager;
    private SessionManager sessionManager;
    private ItemManager itemManager;
    private WishlistPresenter wishlistPresenter;
    private AccountRepository accountRepository;

    private AccountGateway accountGateway;
    private ItemsGateway itemsGateway;

    public WishlistController(UseCasePool useCasePool, GatewayPool gatewayPool) {

        this.wishlistManager = useCasePool.getWishlistManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.itemManager = useCasePool.getItemManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.accountGateway = gatewayPool.getAccountGateway();
        this.itemsGateway = gatewayPool.getItemsGateway();

    }

    public void updateItems() {

        accountGateway.populate(accountRepository);
        itemsGateway.populate(itemManager);

        List<String> wishlistItems = new ArrayList<>();

        for (int id : wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID())) {

            wishlistItems.add(itemManager.getItemNameById(id));

        }

        List<String> warehouseItems = new ArrayList<>();

        for (int id : itemManager.getNotInAccountIDs(sessionManager.getCurrAccountID())) {

            warehouseItems.add(itemManager.getItemNameById(id));

        }

        wishlistPresenter.setItemLists(wishlistItems, warehouseItems);

    }

    public void setWishlistPresenter(WishlistPresenter wishlistPresenter) {
        this.wishlistPresenter = wishlistPresenter;
    }

    public void addToWishlist(int itemIndex) {

        wishlistManager.addItemToWishlist(sessionManager.getCurrAccountID(),
                itemManager.getNotInAccountIDs(sessionManager.getCurrAccountID()).get(itemIndex));

        updateItems();

    }

    public void removeFromWishlist(int itemIndex) {

        wishlistManager.removeItemFromWishlist(sessionManager.getCurrAccountID(),
                wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()).get(itemIndex));

        updateItems();

    }

    public void changeSelectedWishlistItem(int itemIndex) {

        String name = itemIndex >= 0 ?
                itemManager.getItemNameById(wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()).get(itemIndex)) : "";
        String desc = itemIndex >= 0 ?
                itemManager.getItemDescById(wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()).get(itemIndex)) : "";

        wishlistPresenter.setSelectedItemInfo(name, desc);

    }

    public void changeSelectedWarehouseItem(int itemIndex) {

        String name = itemIndex >= 0 ?
                itemManager.getItemNameById(itemManager.getNotInAccountIDs(sessionManager.getCurrAccountID()).get(itemIndex)) : "";
        String desc = itemIndex >= 0 ?
                itemManager.getItemDescById(itemManager.getNotInAccountIDs(sessionManager.getCurrAccountID()).get(itemIndex)) : "";

        wishlistPresenter.setSelectedItemInfo(name, desc);

    }

}
