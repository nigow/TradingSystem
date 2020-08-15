package org.twelve.gateways;

import org.twelve.usecases.item.ItemManager;

/**
 * A gateway for Items that interacts with external storage.
 */
public interface ItemsGateway {

    /**
     * Method that syncs the external storage information into the in-memory item repository.
     *
     * @param itemManager local item storage to populate
     * @return whether or not the population was successful
     */
    boolean populate(ItemManager itemManager);

    /**
     * Method that syncs a local item's update to the external storage
     *
     * @param itemId      id of the item
     * @param name        name of the item
     * @param description description of the item
     * @param isApproved  whether or not the item is approved by the admin
     * @param ownerId     owner's account id of the item
     * @param newItem     true if it is a newly created item. false if it is meant to update an existing item.
     * @return whether or not the sync was successful
     */
    boolean save(int itemId, String name, String description, boolean isApproved, int ownerId, boolean newItem);

}
