package org.twelve.usecases;

import java.util.*;

import org.twelve.entities.Account;
import org.twelve.entities.Item;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ItemsGateway;

/**
 * Manager for items which edits items in the system.
 *
 * @author Isaac
 */

public class ItemManager extends ItemUtility {

    private ItemsGateway itemsGateway;

    /**
     * Constructor for ItemManager which stores an ItemsGateway.
     *
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     */
    public ItemManager(ItemsGateway itemsGateway, AccountRepository accountRepository) {
        super(accountRepository);
        this.itemsGateway = itemsGateway;
        itemsGateway.populate(this);
    }

    public void switchToDemoMode(ItemsGateway itemsGateway) {
        this.itemsGateway = itemsGateway;
        for (Item item : items.values()) {
            updateToItemsGateway(item, true);
        }
    }

    public void switchToNormalMode(ItemsGateway itemsGateway) {
        this.itemsGateway = itemsGateway;
        items.clear();
        itemsGateway.populate(this);
    }

    /**
     * Adds a item to the local storage
     *
     * @param id          the id of the item to be added
     * @param name        the name of the item to be added
     * @param description the description of the item to be added
     * @param ownerId     the id of the owner of the item to be added
     * @param isApproved  whether this item was approved by an admin
     */
    public void addToItems(int id, String name, String description, int ownerId, boolean isApproved) {
        Item item = new Item(id, name, description, ownerId);
        if(isApproved) item.approve();
        items.put(id, item);
    }

    /**
     * Updates the item to the gateway
     *
     * @param item  the item being updated to the gateway
     */
    private void updateToItemsGateway(Item item, boolean newItem) {
        itemsGateway.save(item.getItemID(), item.getName(), item.getDescription(),
                item.isApproved(), item.getOwnerID(), newItem);
    }

    /**
     * Creates a new item and stores in local storage.
     *
     * @param name        The name of the item
     * @param description The description of the item
     * @param ownerID     The id of the owner of the item
     */
    public void createItem(String name, String description, int ownerID) {
        int id = (items.isEmpty() ? 1 : Collections.max(items.keySet()) + 1);
        Item item = new Item(id, name, description, ownerID);
        this.items.put(id, item);
        updateToItemsGateway(item, true);
    }

    /**
     * Deletes an item in the system and returns if item was successfully deleted.
     *
     * @param itemId The item to be deleted
     * @return Whether the deletion was successful
     */
    public boolean removeItem(int itemId) {
        boolean result = false;
        if (items.containsKey(itemId)) {
            items.get(itemId).setOwnerID(-1);
            result = true;
            updateToItemsGateway(items.get(itemId), false);
            this.items.remove(itemId);
        }
        return result;
    }

    /**
     * Get the string of item with the id entered.
     *
     * @param itemID    ID of the item with info being returned
     * @return String of item with the entered ID
     */
    public String getItemStringById(int itemID) {
        return findItemById(itemID).toString();
    }

    /**
     * Get the name of item with the id entered.
     *
     * @param itemID    ID of the item with info being returned
     * @return Name of item with the entered ID
     */
    public String getItemNameById(int itemID) {
        return findItemById(itemID).getName();
    }

    /**
     * Get the desc of item with the id entered.
     *
     * @param itemID    ID of the item with info being returned
     * @return Desc of item with the entered ID
     */
    public String getItemDescById(int itemID) {
        return findItemById(itemID).getDescription();
    }

    /**
     * Gets the approval status of the item.
     *
     * @param itemId    Id of the item with info being returned
     * @return approval status of the item
     */
    public boolean isApproved(int itemId) {
        return items.get(itemId).isApproved();
    }

    /**
     * Update the owner of the item.
     *
     * @param itemId    Id of the item with info being returned
     * @param ownerID   ID of the new owner of the item
     */
    public void updateOwner(int itemId, int ownerID) {
        items.get(itemId).setOwnerID(ownerID);
        updateToItemsGateway(items.get(itemId), false);
    }

    /**
     * Update the approval status of the item.
     *
     * @param itemId     Id of the item with info being returned
     * @param approval new approval status of the item
     */
    public void updateApproval(int itemId, boolean approval) {
        if (approval) {
            items.get(itemId).approve();
        } else {
            items.get(itemId).disapprove();
        }
        updateToItemsGateway(items.get(itemId), false);
    }

    /**
     * Retrieves all items stored in local storage.
     *
     * @return List of all items
     */
    protected List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            if (this.items.get(entry.getKey()).getOwnerID() != -1) {
                items.add(this.items.get(entry.getKey()));
            }
        }
        return items;
    }

    /**
     * Retrieves all items stored in local storage in string format.
     *
     * @return List of all items in string format
     */
    public List<String> getAllItemsString() {
        List<String> stringItems = new ArrayList<>();
        for (Item item : getAllItems()) {
            stringItems.add(item.toString());
        }
        return stringItems;
    }

    public List<Integer> getAllItemIds(){
        return new ArrayList<>(items.keySet());
    }

}
