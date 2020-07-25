package usecases;

import entities.Account;
import entities.Item;
import gateways.AccountGateway;
import gateways.ItemsGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class of useful services for wishlist.
 *
 * @author Tairi, Andrew
 */
public class WishlistManager {


    private AccountRepository accountRepository;

    private ItemUtility itemUtility;

    /**
     * Constructor for WishlistUtility.
     *
     */
    public WishlistManager(AccountRepository accountRepository, ItemUtility itemsUtility) {
        this.accountRepository = accountRepository;
        this.itemUtility = itemsUtility;
    }

    /**
     * Adds an itemID to the given account's wishlist.
     *
     * @param accountID Unique identifier of the account
     * @param itemID Unique identifier of the item
     */
    public void addItemToWishlist(int accountID, int itemID) {
        accountRepository.getAccountFromID(accountID).addToWishlist(itemID);
    }

    /**
     * Removes an itemID from the given account's wishlist.
     *
     * @param itemID Unique identifier of the item
     */
    public void removeItemFromWishlist(int accountID, int itemID) {
        accountRepository.getAccountFromID(accountID).removeFromWishList(itemID);
    }

    /**
     * Gets the wishlist of item ids for the given account.
     *
     * @return Wishlist of the given account
     */
    public List<Integer> getWishlistFromID(int accountID) {
        return accountRepository.getAccountFromID(accountID).getWishlist();
    }

    /**
     * Determines if item corresponding to the itemID is in the current account's wishlist.
     *
     * @param itemID Unique identifier of the item
     * @return Whether the item corresponding to the itemID is in the current account's wishlist
     */
    public boolean isInWishlist(int accountID, int itemID) {
        return accountRepository.getAccountFromID(accountID).getWishlist().contains(itemID);
    }

    /**
     * Get all wishlist items for a specific account.
     *
     * @param accountID The Account ID to look up for
     * @return Wishlist that this account has
     */
    public List<Item> getWishlistItems(int accountID) {
        List<Item> wishlist = new ArrayList<>();
        for (int itemID : accountRepository.getAccountFromID(accountID).getWishlist()) {
            Item item = itemUtility.findItemById(itemID);
            wishlist.add(item);
        }
        return wishlist;
    }

    /**
     * Get the wish lists for all accounts.
     *
     * @return A list containing wishlists of all users
     */
    public List<List<Item>> getAllWishlist() {
        List<List<Item>> all = new ArrayList<>();
        for (Account account : accountRepository.getAccounts()) {
            all.add(getWishlistItems(account.getAccountID()));
        }
        return all;
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
