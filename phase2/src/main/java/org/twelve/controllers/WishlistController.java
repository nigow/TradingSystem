package org.twelve.controllers;

import org.twelve.presenters.WishlistPresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;
import org.twelve.usecases.WishlistManager;

import java.util.ArrayList;
import java.util.List;

public class WishlistController {

    private WishlistManager wishlistManager;
    private SessionManager sessionManager;
    private ItemManager itemManager;
    private WishlistPresenter wishlistPresenter;

    public WishlistController(UseCasePool useCasePool) {

        this.wishlistManager = useCasePool.getWishlistManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.itemManager = useCasePool.getItemManager();


    }

    public void updateItems() {

        List<String> wishlistItems = new ArrayList<>();

        for (int id : wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID())) {

            wishlistItems.add(itemManager.getItemNameById(id));

        }

        List<String> warehouseItems = new ArrayList<>();

        for (int id : itemManager.getAllItemIds()) {

            if (!wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()).contains(id) &&
                    itemManager.getOwnerId(id) != sessionManager.getCurrAccountID())
                warehouseItems.add(itemManager.getItemNameById(id));

        }

        wishlistPresenter.setItemLists(wishlistItems, warehouseItems);

    }

    public void setWishlistPresenter(WishlistPresenter wishlistPresenter) {
        this.wishlistPresenter = wishlistPresenter;
    }

}
