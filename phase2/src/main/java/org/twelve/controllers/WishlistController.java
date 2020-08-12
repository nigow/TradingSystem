package org.twelve.controllers;

import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.WishlistPresenter;
import org.twelve.usecases.*;

import java.util.ArrayList;
import java.util.List;

public class WishlistController {

    private final WishlistManager wishlistManager;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private WishlistPresenter wishlistPresenter;
    private final AccountRepository accountRepository;

    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;

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

        List<String> localItems = new ArrayList<>();

        for (int id : itemManager.getLocalItems(sessionManager.getCurrAccountID())) {

            localItems.add(itemManager.getItemNameById(id));

        }

        wishlistPresenter.setWishlistItems(wishlistItems);
        wishlistPresenter.setWarehouseItems(warehouseItems);
        wishlistPresenter.setLocalItems(localItems);

    }

    public void setWishlistPresenter(WishlistPresenter wishlistPresenter) {
        this.wishlistPresenter = wishlistPresenter;
    }

    public void addToWishlist(int itemIndex) {

        wishlistManager.addItemToWishlist(sessionManager.getCurrAccountID(),
                itemManager.getLocalItems(sessionManager.getCurrAccountID()).get(itemIndex));

        updateItems();

    }

    public void removeFromWishlist(int itemIndex) {

        wishlistManager.removeItemFromWishlist(sessionManager.getCurrAccountID(),
                wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()).get(itemIndex));

        updateItems();

    }

    public void changeSelectedWishlistItem(int itemIndex) {

        int itemId = wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()).get(itemIndex);

        wishlistPresenter.setSelectedItemName(itemManager.getItemNameById(itemId));
        wishlistPresenter.setSelectedItemDesc(itemManager.getItemDescById(itemId));

    }

    public void changeSelectedWarehouseItem(int itemIndex) {

        int itemId = itemManager.getLocalItems(sessionManager.getCurrAccountID()).get(itemIndex);

        wishlistPresenter.setSelectedItemName(itemManager.getItemNameById(itemId));
        wishlistPresenter.setSelectedItemDesc(itemManager.getItemDescById(itemId));

    }

}
