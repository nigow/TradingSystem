package gateways;

import entities.Item;

import java.util.List;

import java.io.IOException;

/**
 * A gateway for interacting with the persistent storage of items.
 */
public interface ItemsGateway {

    /**
     * Given an item's ID, return the corresponding Item object.
     * @param id ID of desired item.
     * @return Item possessing the given ID (null if an invalid ID was given).
     */
    Item findById(int id) throws IOException;

    /**
     * Given an item, save its information to persistent storage.
     * @param item Item being saved.
     */
    void updateItem(Item item) throws IOException;

    /**
     * Retrieve every item in the system.
     * @return List of every item in the system.
     */
    List<Item> getAllItems() throws IOException;

    /**
     * Return an ID that does not belong to any item at the time the method is called.
     * @return An unused ID.
     */
    int generateValidId();

}
