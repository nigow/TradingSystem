package org.twelve.gateways.ram;

import org.twelve.entities.Item;
import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.ItemManager;

import java.util.*;

public class InMemoryItemGateway implements ItemsGateway {

    public final Map<Integer, Item> itemMap;

    public InMemoryItemGateway(Map<Integer, Item> itemMap) {
        this.itemMap = itemMap;
    }

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

    @Override
    public boolean save(int itemId, String name, String description, boolean isApproved, int ownerId, boolean newItem) {
        Item item = new Item(itemId, name, description, ownerId, isApproved);
        itemMap.put(item.getItemID(), item);
        return true;
    }
}
