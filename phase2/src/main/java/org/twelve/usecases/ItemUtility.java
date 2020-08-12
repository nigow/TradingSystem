package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Utility class for items to access certain types of items.
 *
 * @author Isaac
 */

abstract public class ItemUtility {

    protected Map<Integer, Item> items;

    protected AccountRepository accountRepository;

    /**
     * Constructor for ItemUtility.
     */
    public ItemUtility(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        items = new HashMap<>();
    }

    /**
     * Gets the ID of the owner of the item.
     *
     * @param itemID    ID of the item which information is being returned about
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
    protected List<Item> getApproved() {
        List<Item> approvedItems = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).isApproved()) {
                approvedItems.add(items.get(entry.getKey()));
            }
        }
        return approvedItems;
    }

    /**
     * Retrieves all approved item ids in the system.
     *
     * @return List of all approved item ids in the system.
     */
    public List<Integer> getApprovedIDs() {
        List<Integer> approvedItems = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).isApproved() && items.get(entry.getKey()).getOwnerID() != -1) {
                approvedItems.add(items.get(entry.getKey()).getItemID());
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
    protected List<Item> getDisapproved() {
        List<Item> disapprovedItems = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (!items.get(entry.getKey()).isApproved()) {
                disapprovedItems.add(items.get(entry.getKey()));
            }
        }
        return disapprovedItems;
    }

    /**
     * Retrieves IDs of all non-approved items in the system.
     *
     * @return List of IDs of all non-approved items in the system
     */
    public List<Integer> getDisapprovedIDs() {
        List<Integer> disapprovedItems = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (!items.get(entry.getKey()).isApproved()) {
                disapprovedItems.add(items.get(entry.getKey()).getItemID());
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
     * @param accountID     Account ID which the items are retrieved for
     * @return List of items for account
     */
    protected List<Item> getAllInventoryOfAccount(int accountID) {
        List<Item> inventory = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).getOwnerID() == accountID)
                inventory.add(items.get(entry.getKey()));
        }
        return inventory;
    }

    /**
     * Retrieves IDs of all items for a certain account.
     *
     * @param accountID     Account ID which the items are retrieved for
     * @return List of IDs of items for account
     */
    public List<Integer> getAllInventoryOfAccountIDs(int accountID) {
        List<Integer> inventory = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).getOwnerID() == accountID)
                inventory.add(items.get(entry.getKey()).getItemID());
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID     Account ID which the items are retrieved for
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
     * @param accountID     Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Integer> getApprovedInventoryOfAccount(int accountID) {
        List<Integer> inventory = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() == accountID) {
                inventory.add(item.getItemID());
            }
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account in string format.
     *
     * @param accountID     Account ID which the items are retrieved for
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

    //TODO to be deleted cause never used? idk
    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID     Account ID which the items are retrieved for
     * @return List of items for account
     */
    protected List<Item> getDisprovedItemInventoryOfAccount(int accountID) {
        List<Item> inventory = new ArrayList<>();
        for (Item item : getDisapproved()) {
            if (item.getOwnerID() == accountID) {
                inventory.add(item);
            }
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account.
     *
     * @param accountID     Account ID which the items are retrieved for
     * @return List of items for account
     */
    public List<Integer> getDisprovedInventoryOfAccount(int accountID) {
        List<Integer> inventory = new ArrayList<>();
        for (Item item : getDisapproved()) {
            if (item.getOwnerID() == accountID) {
                inventory.add(item.getItemID());
            }
        }
        return inventory;
    }

    /**
     * Retrieves all items for a certain account in string format.
     *
     * @param accountID     Account ID which the items are retrieved for
     * @return List of items for account in string format
     */
    public List<String> getDisprovedInventoryOfAccountString(int accountID) {
        List<String> items = new ArrayList<>();
        for (Item item : getDisapproved()) {
            if (item.getOwnerID() == accountID) {
                items.add(item.toString());
            }
        }
        return items;
    }

    /**
     * Retrieves all items not in a certain account/the account's wishlist.
     *
     * @param accountID       Account ID which the items are not retrieved from
     * @return List of all items not in a certain account
     */
    protected List<Item> getNotInAccount(int accountID) {
        List<Integer> currentWishlist = accountRepository.getAccountFromID(accountID).getWishlist();
        List<Item> items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() != accountID && !currentWishlist.contains(item.getItemID())) {
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Retrieves IDs of all items not in a certain account/the account's wishlist.
     *
     * @param accountID       Account ID which the items are not retrieved from
     * @return List of IDs of all items not in a certain account
     */
    public List<Integer> getNotInAccountIDs(int accountID) {
        List<Integer> currentWishlist = accountRepository.getAccountFromID(accountID).getWishlist();
        List<Integer> items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() != accountID && !currentWishlist.contains(item.getItemID())) {
                items.add(item.getItemID());
            }
        }
        return items;
    }

    /**
     * Retrieves all items not in a certain account in string format.
     *
     * @param accountID       Account ID which the items are not retrieved from
     * @return List of all items not in a certain account string format
     */
    public List<String> getNotInAccountString(int accountID) {
        List<Integer> currentWishlist = accountRepository.getAccountFromID(accountID).getWishlist();
        List<String> items = new ArrayList<>();
        for (Item item : getApproved()) {
            if (item.getOwnerID() != accountID && !currentWishlist.contains(item.getItemID())) {
                items.add(item.toString());
            }
        }
        return items;
    }

    /**
     * Retrieves an item with a certain id, if item does not exist return null
     * @param itemId    Id of item to be retrieved
     * @return The item with the id in question
     */
    protected Item findItemById(int itemId) {
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            if (items.get(entry.getKey()).getItemID() == itemId) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Retrieves all items with the same home location as the account with the id entered
     *
     * @param accountId The Id of the account
     * @return A list of all item Ids in the same location
     */
    public List<Integer> getLocalItems(int accountId) {
        List<Integer> localItems = new ArrayList<>();
        String location = accountRepository.getAccountFromID(accountId).getLocation();
        for (Item item : getNotInAccount(accountId)) {
            Account account = accountRepository.getAccountFromID(item.getOwnerID());
            if (location.equals(account.getLocation())) {
                localItems.add(item.getItemID());
            }
        }
        return localItems;
    }

    /**
     * Retrieves all items with the same home location as the account with the id entered in
     * String format
     *
     * @param accountId The Id of the account
     * @return A list of all item Ids in the same location
     */
    public List<String> getLocalItemsString(int accountId) {
        List<String> localItems = new ArrayList<>();
        Account account = accountRepository.getAccountFromID(accountId);
        for (Item item : items.values()) {
            Account account2 = accountRepository.getAccountFromID(item.getOwnerID());
            if (account.getLocation().equals(account2.getLocation())) {
                localItems.add(item.toString());
            }
        }
        return localItems;
    }

    /**
     * Retrieves an item with a certain id in string format, if item does not
     * exist return null
     * @param itemId    Id of item to be retrieved
     * @return The item with the id in question
     */
    public String findItemByIdString(int itemId) {
        return findItemById(itemId).toString();
    }

}
