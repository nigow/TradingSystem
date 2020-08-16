package org.twelve.usecases.item;

import org.twelve.entities.Account;
import org.twelve.entities.Item;
import org.twelve.usecases.account.AccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Utility class for items to access certain types of items.
 */

abstract public class ItemUtility {

    final Map<Integer, Item> items;
    final AccountRepository accountRepository;
    private final int FAKE_ACCOUNT_ID = -1;

    /**
     * Constructor for ItemUtility.
     *
     * @param accountRepository The repository for storing all accounts in the system.
     */
    ItemUtility(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        items = new HashMap<>();
    }

    /**
     * Gets the ID of the owner of the item.
     *
     * @param itemID ID of the item which information is being returned about
     * @return ID of the owner of the item (or a fake id if the item is not found).
     */
    public int getOwnerId(int itemID) {
        Item item = findItemById(itemID);
        if (item != null)
            return item.getOwnerID();
        return FAKE_ACCOUNT_ID;
    }

    /**
     * Retrieves all approved items in the system.
     *
     * @return List of all approved items in the system
     */
    List<Item> getApproved() {
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
            if (items.get(entry.getKey()).isApproved() && items.get(entry.getKey()).getOwnerID() != FAKE_ACCOUNT_ID) {
                approvedItems.add(items.get(entry.getKey()).getItemID());
            }
        }
        return approvedItems;
    }

    /**
     * Retrieves all non-approved items in the system.
     *
     * @return List of all non-approved items in the system
     */
    List<Item> getDisapproved() {
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
     * Retrieves all approved items for a certain account.
     *
     * @param accountID Account ID which the items are retrieved for
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
     * Retrieves all items not in a certain account/the account's wishlist.
     *
     * @param accountID Account ID which the items are not retrieved from
     * @return List of all items not in a certain account
     */
    List<Item> getNotInAccount(int accountID) {
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
     * Retrieves an item with a certain id, if item does not exist return null
     *
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
            if (account != null) {
                if (location.equals(account.getLocation())) {
                    localItems.add(item.getItemID());
                }
            }
        }
        return localItems;
    }

    /**
     * Get the name of item with the id entered.
     *
     * @param itemID ID of the item with info being returned
     * @return Name of item with the entered ID
     */
    public String getItemNameById(int itemID) {
        return findItemById(itemID).getName();
    }

    /**
     * Get the desc of item with the id entered.
     *
     * @param itemID ID of the item with info being returned
     * @return Desc of item with the entered ID
     */
    public String getItemDescById(int itemID) {
        return findItemById(itemID).getDescription();
    }
}
