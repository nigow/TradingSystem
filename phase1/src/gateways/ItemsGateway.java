package gateways;

import entities.Item;

import java.util.List;


/**
 * A gateway for interacting with the persistent storage of items.
 */
public interface ItemsGateway {

    /**
     * Given an item's ID, return the corresponding Item object.
     * @param id ID of desired item.
     * @return Item possessing the given ID (null if an invalid ID was given).
     */
    Item findById(int id);

    /**
     * Given an item, save its information to persistent storage.
     * @param item Item being saved.
     */
    boolean updateItem(Item item);

    /**
     * Retrieve every item in the system.
     * @return List of every item in the system.
     */
    List<Item> getAllItems();

    /**
     * Return an ID that does not belong to any item at the time the method is called.
     * @return An unused ID.
     */
    int generateValidId();

    /**
     * Given an item, delete the item and persist the updated info.
     * @param item Item being saved.
     * @return true if the item is deleted.
     */
    boolean deleteItem(Item item);
}
