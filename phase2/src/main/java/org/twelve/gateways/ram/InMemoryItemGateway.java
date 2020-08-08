package org.twelve.gateways.ram;

import org.twelve.entities.Item;
import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.ItemManager;

import java.util.*;

public class InMemoryItemGateway implements ItemsGateway {

    /**
     * pseudo-external storage of items
     */
    public final Map<Integer, Item> itemMap;

    /**
     * Initialize the gateway
     * @param itemMap a key-pair set of an item id and item object
     */
    public InMemoryItemGateway(Map<Integer, Item> itemMap) {
        this.itemMap = itemMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(ItemManager itemManager) {
        List<Integer> existingIds = itemManager.getAllItemIds();
        for (Item item : this.itemMap.values()) {
            if (!existingIds.contains(item.getItemID())) {
                itemManager.addToItems(item.getItemID(), item.getName(), item.getDescription(), item.getOwnerID(),
                        item.isApproved());
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int itemId, String name, String description, boolean isApproved, int ownerId, boolean newItem) {
        Item item = new Item(itemId, name, description, ownerId, isApproved);
        itemMap.put(item.getItemID(), item);
        return true;
    }
}
