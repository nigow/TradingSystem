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
 * @author Tairi
 */
public class WishlistUtility {
    /**
     * The gateway for account.
     */
    public final AccountGateway accountGateway;

    /**
     * The gateway for items.
     */
    public final ItemsGateway itemsGateway;

    /**
     * Constructor for WishlistUtility.
     *
     * @param accountGateway Account gateway to reference accounts
     * @param itemsGateway   Item gateway to reference items
     */
    public WishlistUtility(AccountGateway accountGateway, ItemsGateway itemsGateway) {
        this.accountGateway = accountGateway;
        this.itemsGateway = itemsGateway;
    }

    /**
     * Get all wishlist items for a specific account.
     *
     * @param accountID The Account ID to look up for
     * @return Wishlist that this account has
     */
    public List<Item> wishlistItems(int accountID) {
        List<Item> wishlist = new ArrayList<>();
        for (int itemID : accountGateway.findById(accountID).getWishlist()) {
            Item item = itemsGateway.findById(itemID);
            wishlist.add(item);
        }
        return wishlist;
    }

    /**
     * Get the wishlists for all accounts.
     *
     * @return A list containing wishlists of all users
     */
    public List<List<Item>> allWishlist() {
        List<List<Item>> all = new ArrayList<>();
        for (Account account : accountGateway.getAllAccounts()) {
            all.add(wishlistItems(account.getAccountID()));
        }
        return all;
    }


    /**
     * String representation of all wishlist items for a specific account.
     *
     * @param
     * @return String representation of all wishlist items
     */
    public List<String> wishlistToString(int accountID) {
        List<String> rep = new ArrayList<>();
        for (Item item : wishlistItems(accountID)) rep.add(item.toString());
        return rep;
    }


}
