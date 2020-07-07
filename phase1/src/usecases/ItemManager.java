package usecases;

import java.util.ArrayList;
import java.util.List;

import entities.Item;
import gateways.ItemsGateway;

/**
 * Manager for items which creates an item or takes in an item to edit.
 *
 * @author Isaac
 */

public class ItemManager {

    /**
     * The gateway which deals with items.
     */
    private final ItemsGateway itemsGateway;

    /**
     * Constructor for ItemManager which stores an ItemsGateway.
     *
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     */
    public ItemManager(ItemsGateway itemsGateway) {
        this.itemsGateway = itemsGateway;
    }

    /**
     * Creates a new item and stores in the persistent storage and allows for editing of the
     * new item.
     *
     * @param name        The name of the item
     * @param description The description of the item
     * @param ownerID     The id of the owner of the item
     */
    public void createItem(String name, String description, int ownerID) {
        Item item = new Item(itemsGateway.generateValidId(), name, description, ownerID);
        this.itemsGateway.updateItem(item);
    }

    /**
     * Deletes an item in the system and returns if item was successfully deleted.
     *
     * @param item The item to be deleted
     * @return Whether the deletion was successful
     */
    public boolean removeItem(Item item) {
        boolean result = false;
        if (getAllItems().contains(item)) {
            item.setOwnerID(-1);
            result = true;
            itemsGateway.updateItem(item);
        }
        return result;
    }

    /**
     * Get the item with the ID entered.
     *
     * @param ItemID ID of the item
     * @return item with the entered Id
     */
    public Item getItemById(int ItemID) {
        return itemsGateway.findById(ItemID);
    }

    /**
     * Get the string representation of item with the id entered.
     *
     * @param ItemID ID of the item
     * @return String of item with the entered ID
     */
    public String getItemStringById(int ItemID) {
        return itemsGateway.findById(ItemID).toString();
    }

    /**
     * Gets the ID of an item.
     *
     * @param item item which information is being returned about
     * @return ID of an item
     */
    public int getItemId(Item item) {
        return item.getItemID();
    }

    /**
     * Gets the approval status of the item.
     *
     * @param item item which information is being returned about
     * @return approval status of the item
     */
    public boolean isApproved(Item item) {
        return item.isApproved();
    }

    /**
     * Gets the ID of the owner of the item.
     *
     * @param itemID ID of the item which information is being returned about
     * @return ID of the owner of the item (-1 if no item can be found).
     */
    public int getOwnerId(int itemID) {
        Item item = itemsGateway.findById(itemID);
        if (item != null) return item.getOwnerID();
        return -1;
    }

    /**
     * Update the owner of the item.
     *
     * @param item    item being updated
     * @param ownerID new owner of the item
     */
    public void updateOwner(Item item, int ownerID) {
        item.setOwnerID(ownerID);
        itemsGateway.updateItem(item);
    }

    /**
     * Update the approval status of the item.
     *
     * @param item     item being updated
     * @param approval new approval status of the item
     */
    public void updateApproval(Item item, boolean approval) {
        if (approval) {
            item.approve();
        } else {
            item.disapprove();
        }
        itemsGateway.updateItem(item);
    }

    /**
     * Retrieves all items stored in persistent storage.
     *
     * @return List of all items
     */
    public List<Item> getAllItems() {
        List<Item> Items = new ArrayList<>();
        for (Item item : itemsGateway.getAllItems()) {
            if (item.getOwnerID() != -1) {
                Items.add(item);
            }
        }
        return Items;
    }

    /**
     * Retrieves all items stored in persistent storage in string format.
     *
     * @return List of all items in string format
     */
    public List<String> getAllItemsString() {
        List<String> StringItems = new ArrayList<>();
        for (Item item : getAllItems()) {
            StringItems.add(item.toString());
        }
        return StringItems;
    }

}
