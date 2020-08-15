package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class of useful services for wishlist.
 *
 * @author Tairi, Andrew
 */
public class WishlistManager {

    private final AccountRepository accountRepository;
    private final ItemUtility itemUtility;

    /**
     * Constructor for WishlistManager.
     *
     * @param accountRepository the repository for storing accounts
     * @param itemUtility utility class for items
     */
    public WishlistManager(AccountRepository accountRepository, ItemUtility itemUtility) {
        this.accountRepository = accountRepository;
        this.itemUtility = itemUtility;
    }

    /**
     * Adds an itemID to the given account's wishlist.
     *
     * @param accountID Unique identifier of the account
     * @param itemID Unique identifier of the item
     */
    public void addItemToWishlist(int accountID, int itemID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addToWishlist(itemID);
        accountRepository.updateToAccountGateway(account, false);
    }

    /**
     * Removes an itemID from the given account's wishlist.
     *
     * @param accountID Unique identifier of the account
     * @param itemID Unique identifier of the item
     */
    public void removeItemFromWishlist(int accountID, int itemID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removeFromWishList(itemID);
        accountRepository.updateToAccountGateway(account, false);
    }

    /**
     * Gets the wishlist of item ids for the given account.
     *
     * @param accountID Unique identifier of the account
     * @return Wishlist of the given account
     */
    public List<Integer> getWishlistFromID(int accountID) {
        return accountRepository.getAccountFromID(accountID).getWishlist();
    }

    /**
     * Get all wishlist items for a specific account.
     *
     * @param accountID The Account ID to look up for
     * @return Wishlist that this account has
     */
    protected List<Item> getWishlistItems(int accountID) {
        List<Item> wishlist = new ArrayList<>();
        for (int itemID : accountRepository.getAccountFromID(accountID).getWishlist()) {
            Item item = itemUtility.findItemById(itemID);
            wishlist.add(item);
        }
        return wishlist;
    }

    /**
     * String representation of all wishlist items for a specific account.
     *
     * @param accountID Unique identifier of account
     * @return String representation of all wishlist items
     */
    public List<String> getWishlistToString(int accountID) {
        List<String> rep = new ArrayList<>();
        for (Item item : getWishlistItems(accountID)) rep.add(item.toString());
        return rep;
    }
}
