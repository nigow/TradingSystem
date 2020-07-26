package org.twelve.usecases;

import org.twelve.entities.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Utility class for items to access certain types of items.
 *
 * @author Isaac
 */

abstract public class ItemUtility {

    /**
     * List of all items in the system
     */
    protected Map<Integer, Item> items;

    /**
     * Constructor for ItemUtility.
     */
    public ItemUtility() {
    }

    /**
     * Gets the ID of the owner of the item.
     *
     * @param itemID ID of the item which information is being returned about
     * @return ID of the owner of the item (-1 if no item can be found).
     */
    public int getOwnerId(int itemID) {
        Item item = findItemById(itemID);
        if (item != null) return item.getOwnerID();
        return -1;
    }

    /**
     * Retrieves all approved items in the system.
     *
     * @return List of all approved items in the system
     */
    public List<Item> getApproved() {
        List<Item> approvedItems = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).isApproved()) {
                approvedItems.add(items.get(entry.getKey()));
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
        for (Item item : getApproved()) {
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
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (!items.get(entry.getKey()).isApproved()) {
                disapprovedItems.add(items.get(entry.getKey()));
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
        for (Item item : getDisapproved()) {
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
        List<Item> inventory = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).getOwnerID() == accountID)
                inventory.add(items.get(entry.getKey()));
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<String> getAllInventoryOfAccountString(int accountID) {
        List<String> inventory = new ArrayList<>();
        for (Item item : getAllInventoryOfAccount(accountID)) {
            if (item.getOwnerID() == accountID)
                inventory.add(item.toString());
        }
        return inventory;
    }

    /**
     * Retrieves all approved items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Item> getApprovedInventoryOfAccount(int accountID) {
        List<Item> inventory = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() == accountID) {
                inventory.add(item);
            }
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account in string format.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account in string format
     */
    public List<String> getApprovedInventoryOfAccountString(int accountID) {
        List<String> inventory = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() == accountID) {
                inventory.add(item.toString());
            }
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Item> getDisprovedInventoryOfAccount(int accountID) {
        List<Item> inventory = new ArrayList<>();
        for (Item item : getDisapproved()) {
            if (item.getOwnerID() == accountID) {
                inventory.add(item);
            }
        }
        return inventory;
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

    /**
     * Retrieves an item with a certain id, if item does not exist return null
     * @param itemId Id of item to be retrieved
     * @return The item with the id in question
     */
    public Item findItemById(int itemId) {
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).getItemID() == itemId) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String findItemByIdString(int itemId) {
        return findItemById(itemId).toString();
    }

}
