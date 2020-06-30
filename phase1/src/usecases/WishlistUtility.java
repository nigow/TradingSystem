package usecases;

import entities.Account;
import entities.Item;
import gateways.AccountGateway;
import gateways.CSVAccountGateway;
import gateways.CSVItemsGateway;
import gateways.ItemsGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing useful services for wishlists on presenters and controllers
 */
public class WishlistUtility {
    /**
     * The gateway for account
     */
    public final AccountGateway accountGateway;

    /**
     * The gateway for items
     */
    public final ItemsGateway itemsGateway;

    /**
     * Constructor for WishlistUtility
     * @param accountGateway account gateway to reference accounts
     * @param itemsGateway item gateway to reference items
     */
    public WishlistUtility(AccountGateway accountGateway, ItemsGateway itemsGateway) {
        this.accountGateway = accountGateway;
        this.itemsGateway = itemsGateway;
    }

    /**
     * Get all wishlist items for a specific account
     * @param id the Account id to look up for
     * @return wishlist that this account has
     */
    public List<Item> wishlistItems(int id){
        List<Item> wishlist = new ArrayList<>();
        for(int itemId: accountGateway.findById(id).getWishlist()){
            Item item = itemsGateway.findById(itemId);
            wishlist.add(item);
        }
        return wishlist;
    }

    /**
     * Get all wishlists for all accounts
     * @return A list containing wishlists of all users
     */
    public List<List<Item>> allWishlist(){
        List<List<Item>> all = new ArrayList<>();
        for(Account account: accountGateway.getAllAccounts()){
            all.add(wishlistItems(account.getAccountID()));
        }
        return all;
    }


    /**
     * String representation of all wishlist items for a specific account
     * @return String representation of all wishlist items
     */
    public String wishlistToString(int id){
        String rep = "";
        for(Item item: wishlistItems(id)) rep += item.toString();
        return rep;
    }


}
