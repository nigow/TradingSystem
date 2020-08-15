package org.twelve.usecases;

import org.twelve.entities.Item;
import org.twelve.gateways.ItemsGateway;

import java.util.Collections;

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
     * @param accountRepository Repository for storing all accounts in the system
     */
    public ItemManager(ItemsGateway itemsGateway, AccountRepository accountRepository) {
        super(accountRepository);
        this.itemsGateway = itemsGateway;
        itemsGateway.populate(this);
    }

    /**
     * Update to item gateway with demo mode.
     *
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     */
    void switchToDemoMode(ItemsGateway itemsGateway) {
        this.itemsGateway = itemsGateway;
        for (Item item : items.values()) {
            updateToItemsGateway(item, true);
        }
    }

    /**
     * Update to item gateway with normal mode.
     *
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     */
    void switchToNormalMode(ItemsGateway itemsGateway) {
        this.itemsGateway = itemsGateway;
        items.clear();
        itemsGateway.populate(this);
    }

    /**
     * Adds an item that exists in the DB to local storage.
     *
     * @param id          the id of the item to be added
     * @param name        the name of the item to be added
     * @param description the description of the item to be added
     * @param ownerId     the id of the owner of the item to be added
     * @param isApproved  whether this item was approved by an admin
     */
    public void addToItems(int id, String name, String description, int ownerId, boolean isApproved) {
        Item item = new Item(id, name, description, ownerId, isApproved);
        items.put(id, item);
    }

    private void updateToItemsGateway(Item item, boolean newItem) {
        itemsGateway.save(item.getItemID(), item.getName(), item.getDescription(),
                item.isApproved(), item.getOwnerID(), newItem);
    }

    /**
     * Creates a new item and stores in local storage.
     *
     * @param name The name of the item
     * @param description The description of the item
     * @param ownerID The id of the owner of the item
     */
    public void createItem(String name, String description, int ownerID) {
        int id = (items.isEmpty() ? 1 : Collections.max(items.keySet()) + 1);
        Item item = new Item(id, name, description, ownerID);
        this.items.put(id, item);
        updateToItemsGateway(item, true);
    }

    /**
     * Deletes an item in the system, if possible.
     *
     * @param itemId The item to be deleted
     */
    public void removeItem(int itemId) {
        if (items.containsKey(itemId)) {
            items.get(itemId).setOwnerID(-1);
            updateToItemsGateway(items.get(itemId), false);
            this.items.remove(itemId);
        }
    }

    /**
     * Update the owner of the item.
     *
     * @param itemId Id of the item with info being returned
     * @param ownerID ID of the new owner of the item
     */
    public void updateOwner(int itemId, int ownerID) {
        items.get(itemId).setOwnerID(ownerID);
        updateToItemsGateway(items.get(itemId), false);
    }

    /**
     * Update the approval status of the item.
     *
     * @param itemId Id of the item with info being returned
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
}
