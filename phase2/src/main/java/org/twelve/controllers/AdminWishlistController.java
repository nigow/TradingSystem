package org.twelve.controllers;

import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.AdminWishlistPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.UseCasePool;
import org.twelve.usecases.WishlistManager;

import java.util.ArrayList;
import java.util.List;

public class AdminWishlistController {
    private final WishlistManager wishlistManager;
    private final AccountRepository accountRepository;
    private final ItemManager itemManager;
    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;

    private AdminWishlistPresenter adminWishlistPresenter;

    public AdminWishlistController(UseCasePool useCasePool, GatewayPool gatewayPool) {
        this.wishlistManager = useCasePool.getWishlistManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.accountGateway = gatewayPool.getAccountGateway();
        this.itemsGateway = gatewayPool.getItemsGateway();
    }

    public void setAdminWishlistPresenter(AdminWishlistPresenter adminWishlistPresenter) {
        this.adminWishlistPresenter = adminWishlistPresenter;
    }

    public void changeSelectedItemToRemove(String selectedUser, int itemIndex) {
        int itemID = wishlistManager.getWishlistFromID(accountRepository.getIDFromUsername(selectedUser)).get(itemIndex);
        adminWishlistPresenter.setSelectedItemDescription(itemManager.getItemDescById(itemID));
    }

    public void removeFromWishlist(String selectedUser, int itemIndex) {
        int accountID = accountRepository.getIDFromUsername(selectedUser);
        int itemID = wishlistManager.getWishlistFromID(accountID).get(itemIndex);
        wishlistManager.removeItemFromWishlist(accountID, itemID);
        updateWishlist(selectedUser);
    }

    public void updateWishlist(String selectedUser) {
        accountGateway.populate(accountRepository);
        itemsGateway.populate(itemManager);
        List<String> wishlistItems = new ArrayList<>();
        if (selectedUser != null) {
            for (int id : wishlistManager.getWishlistFromID(accountRepository.getIDFromUsername(selectedUser))) {
                wishlistItems.add(itemManager.getItemNameById(id));
            }
        }
        adminWishlistPresenter.setSelectedItemDescription("");
        adminWishlistPresenter.setWishlistOfUser(wishlistItems);
        adminWishlistPresenter.setAllUsers(accountRepository.getAccountStrings());
    }

    public void changeSelectedUser(int index) {
        int userID = accountRepository.getIDFromUsername(accountRepository.getAccountStrings().get(index));
        List<String> wishlistItems = new ArrayList<>();
        for (int id : wishlistManager.getWishlistFromID(userID)) {
            wishlistItems.add(itemManager.getItemNameById(id));
        }
        adminWishlistPresenter.setSelectedItemDescription("");
        adminWishlistPresenter.setWishlistOfUser(wishlistItems);
    }
}
