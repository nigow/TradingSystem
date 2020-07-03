package usecases;

import java.util.ArrayList;
import java.util.List;
import entities.Item;
import gateways.ItemsGateway;

/**
 * Represents a manager for items which creates an item or takes in an item to edit
 * @author Isaac
 */

public class ItemManager {

    /**
     * The gateway which deals with items
     */
    private final ItemsGateway itemsGateway;

    /**
     * Constructor for ItemManager which stores an ItemsGateway
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
        Item item = new Item(itemsGateway.generateValidId(), name, description, ownerId);
        this.itemsGateway.updateItem(item);
    }

    /**
     * Deletes an item in the system and returns if item was successfully deleted
     * @param item The item to be deleted
     * @return If the deletion was successful
     */
    public boolean removeItem(Item item){
        boolean result = false;
        if (getAllItems().contains(item)) {
            itemsGateway.deleteItem(item);
            result = true;
        }
        return result;
    }

    /**
     * Get the item with the id entered
     * @param ItemId Id of the item
     * @return The item with the entered Id
     */
    public Item getItemById(int ItemId) {
        return itemsGateway.findById(ItemId);
    }

    /**
     * Update the owner of the item
     * @param item the item being updated
     * @param ownerId the new owner of the item
     */
    public void updateOwner(Item item, int ownerId){
        item.setOwnerID(ownerId);
        itemsGateway.updateItem(item);
    }

    /**
     * Update the approval status of the item
     * @param item the item being updated
     * @param approval the new approval status of the item
     */
    public void updateApproval(Item item, boolean approval){
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

    /**
     * Retrieves all items stored in persistent storage in string format
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
