package usecases;

import java.util.List;
import entities.Item;
import gateways.ItemsGateway;

/**
 * Represents a manager for items which creates an item or takes in an item to edit
 * @author Isaac
 */

public class ItemManager {

    /**
     * The current item being edited
     */
    private Item item;

    /**
     * The gateway which deals with items
     */
    private final ItemsGateway itemsGateway;

    /**
     * Constructor for ItemManager to create and edit an item
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     */
    public ItemManager(ItemsGateway itemsGateway, String name, String description, int ownerId) {
        this.itemsGateway = itemsGateway;
        item = new Item(itemsGateway.generateValidId(), name, description, ownerId);
        this.itemsGateway.updateItem(item);
    }

    /**
     * Constructor for ItemManager with an existing item to edit and ItemsGateway
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     * @param item The item to be edited
     */
    public ItemManager(ItemsGateway itemsGateway, Item item) {
        this.itemsGateway = itemsGateway;
        this.item = item;
    }

    /**
     * Basic Constructor for ItemManager which stores an ItemsGateway
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     */
    public ItemManager(ItemsGateway itemsGateway) {
        this.itemsGateway = itemsGateway;
    }

    /**
     * Creates a new item and stores in the persistent storage and allows for editing of the
     * new item
     * @param name The name of the item
     * @param description The description of the item
     * @param ownerId The id of the owner of the item
     */
    public void createItem(String name, String description, int ownerId) {
        item = new Item(itemsGateway.generateValidId(), name, description, ownerId);
        this.itemsGateway.updateItem(item);
    }

    /**
     * Sets the item to be edited in ItemManager
     * @param item Item being set
     * @return If the item was successfully set
     */
    public boolean setItem(Item item) {
        if (item != null) {
            this.item = item;
            return true;
        }
        return false;
    }

    /**
     * Need a remove item method in itemsGateway first
     * @param itemId The id of the item to be removed
     */
    public void removeItem(int itemId){
        // todo: Need a remove item method in itemsGateway
    }

    /**
     * Update the owner of the item
     * @param ownerId the id of the item's new owner
     */
    public void updateOwner(int ownerId){
        item.setOwnerID(ownerId);
        itemsGateway.updateItem(item);
    }

    /**
     * Update the approval status of the item
     * @param approval The new approval status of the item
     */
    public void updateApproval(boolean approval){
        if (approval){
            item.approve();
        }
        else {
            item.disapprove();
        }
        itemsGateway.updateItem(item);
    }

    /**
     * Retrieves all items stored in persistent storage
     * @return List of all items
     */
    public List<Item> getAllItems() {
        return itemsGateway.getAllItems();
    }

}
