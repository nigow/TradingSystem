package usecases;

import java.io.IOException;
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
        try {
            item = new Item(itemsGateway.generateValidId(), name, description, ownerId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.itemsGateway.updateItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for ItemManager with an already created item to edit
     * @param itemsGateway The gateway for interacting with the persistent storage of items
     * @param item The item to be edited
     */
    public ItemManager(ItemsGateway itemsGateway, Item item) {
        this.itemsGateway = itemsGateway;
        this.item = item;
    }

    /**
     * Basic Constructor for ItemManager
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
        try {
            item = new Item(itemsGateway.generateValidId(), name, description, ownerId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.itemsGateway.updateItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Need a remove item method in itemsGateway first
     * @param itemId The id of the item to be removed
     */
    public void removeItem(int itemId){

    }

    /**
     * Update the owner of the item
     * @param ownerId the id of the item's new owner
     */
    public void updateOwner(int ownerId){
        item.setOwnerID(ownerId);
        try {
            itemsGateway.updateItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            itemsGateway.updateItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all items stored in persistent storage
     * @return List of all items
     */
    public List<Item> getAllItems() {
        List<Item> allItems = null;
        try {
            allItems = itemsGateway.getAllItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allItems;
    }

}
