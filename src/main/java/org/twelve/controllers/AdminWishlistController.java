package org.twelve.controllers;

import org.twelve.presenters.AdminWishlistPresenter;
import org.twelve.usecases.account.AccountRepository;
import org.twelve.usecases.item.ItemManager;
import org.twelve.usecases.UseCasePool;
import org.twelve.usecases.account.WishlistManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for admins to manage everyone's wishlists
 */
public class AdminWishlistController {
    private final WishlistManager wishlistManager;
    private final AccountRepository accountRepository;
    private final ItemManager itemManager;
    private final UseCasePool useCasePool;

    private AdminWishlistPresenter adminWishlistPresenter;

    /**
     * Constructor for controller for admins to manage everyone's wishlists
     *
     * @param useCasePool An instance of {@link org.twelve.usecases.UseCasePool}.
     */
    public AdminWishlistController(UseCasePool useCasePool) {
        this.wishlistManager = useCasePool.getWishlistManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Set the presenter for this controller
     *
     * @param adminWishlistPresenter An instance of a class that implements {@link org.twelve.presenters.AdminWishlistPresenter}
     */
    public void setAdminWishlistPresenter(AdminWishlistPresenter adminWishlistPresenter) {
        this.adminWishlistPresenter = adminWishlistPresenter;
    }

    /**
     * Change the selected item to remove in the presenter
     *
     * @param selectedUser the username of the new selected user
     * @param itemIndex    the index of the new selected item in the user's wishlist
     */
    public void changeSelectedItemToRemove(String selectedUser, int itemIndex) {
        int itemID = wishlistManager.getWishlistFromID(accountRepository.getIDFromUsername(selectedUser)).get(itemIndex);
        adminWishlistPresenter.setSelectedItemDescription(itemManager.getItemDescById(itemID));
    }

    /**
     * Remove the selected item from the user's wishlist
     *
     * @param selectedUser the username of the selected user
     * @param itemIndex    the index of the selected item in the user's wishlist
     */
    public void removeFromWishlist(String selectedUser, int itemIndex) {
        int accountID = accountRepository.getIDFromUsername(selectedUser);
        int itemID = wishlistManager.getWishlistFromID(accountID).get(itemIndex);
        wishlistManager.removeItemFromWishlist(accountID, itemID);
        updateWishlist(selectedUser);
    }

    /**
     * Update the data in the presenter
     *
     * @param selectedUser the current selected user
     */
    public void updateWishlist(String selectedUser) {
        useCasePool.populateAll();
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

    /**
     * Change the selected user
     *
     * @param index the index of the user selected
     */
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
