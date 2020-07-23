package usecases;

import entities.Item;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility class for items to access certain types of items.
 *
 * @author Isaac
 */

// TODO add method to parse list of strings to item
public class ItemUtility {

    /**
     * List of all items in the system
     */
    protected List<Item> items;

    /**
     * Constructor for ItemUtility.
     */
    public ItemUtility() {
    }

    /**
     * Retrieves all approved items in the system.
     *
     * @return List of all approved items in the system
     */
    public List<Item> getApproved() {
        List<Item> approvedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.isApproved()) {
                approvedItems.add(item);
            }
        }
        return approvedItems;
    }

    /**
     * Retrieves a string representation of all approved items in the system.
     *
     * @return List of all approved items in the system in string format
     */
    public List<String> getApprovedString() {
        List<String> approvedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.isApproved()) {
                approvedItems.add(item.toString());
            }
        }
        return approvedItems;
    }

    /**
     * Retrieves all non-approved items in the system.
     *
     * @return List of all non-approved items in the system
     */
    public List<Item> getDisapproved() {
        List<Item> disapprovedItems = new ArrayList<>();
        for (Item item : items) {
            if (!item.isApproved()) {
                disapprovedItems.add(item);
            }
        }
        return disapprovedItems;
    }

    /**
     * Retrieves a string representation of all non-approved items in the system.
     *
     * @return List of all non-approved items in the system in string format
     */
    public List<String> getDisapprovedString() {
        List<String> disapprovedItems = new ArrayList<>();
        for (Item item : items) {
            if (!item.isApproved()) {
                disapprovedItems.add(item.toString());
            }
        }
        return disapprovedItems;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Item> getAllInventoryOfAccount(int accountID) {
        List<Item> items = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getOwnerID() == accountID)
                items.add(item);
        }
        return items;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<String> getAllInventoryOfAccountString(int accountID) {
        List<String> items = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getOwnerID() == accountID)
                items.add(item.toString());
        }
        return items;
    }

    /**
     * Retrieves all approved items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Item> getApprovedInventoryOfAccount(int accountID) {
        List<Item> Items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() == accountID) {
                Items.add(item);
            }
        }
        return Items;
    }

    /**
     * Retrieves all items for a certain account in string format.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account in string format
     */
    public List<String> getApprovedInventoryOfAccountString(int accountID) {
        List<String> Items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() == accountID) {
                Items.add(item.toString());
            }
        }
        return Items;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Item> getDisprovedInventoryOfAccount(int accountID) {
        List<Item> Items = new ArrayList<>();
        for (Item item : getDisapproved()) {
            if (item.getOwnerID() == accountID) {
                Items.add(item);
            }
        }
        return Items;
    }

    /**
     * Retrieves all items for a certain account in string format.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account in string format
     */
    public List<String> getDisprovedInventoryOfAccountString(int accountID) {
        List<String> Items = new ArrayList<>();
        for (Item item : getDisapproved()) {
            if (item.getOwnerID() == accountID) {
                Items.add(item.toString());
            }
        }
        return Items;
    }

    /**
     * Retrieves all items not in a certain account/the account's wishlist.
     *
     * @param accountID       Account ID which the items are not retrieved from
     * @param currentWishlist List of itemIDs of items in wishlist of the user
     * @return List of all items not in a certain account
     */
    public List<Item> getNotInAccount(int accountID, List<Integer> currentWishlist) {
        List<Item> Items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() != accountID && !currentWishlist.contains(item.getItemID())) {
                Items.add(item);
            }
        }
        return Items;
    }

    /**
     * Retrieves all items not in a certain account in string format.
     *
     * @param accountID       Account ID which the items are not retrieved from
     * @param currentWishlist List of itemIDs of items in wishlist of the user
     * @return List of all items not in a certain account string format
     */
    public List<String> getNotInAccountString(int accountID, List<Integer> currentWishlist) {
        List<String> Items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() != accountID && !currentWishlist.contains(item.getItemID())) {
                Items.add(item.toString());
            }
        }
        return Items;
    }
}
