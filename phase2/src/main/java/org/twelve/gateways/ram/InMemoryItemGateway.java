package org.twelve.gateways.ram;

import org.twelve.entities.Item;
import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.ItemManager;

import java.util.*;

public class InMemoryItemGateway implements ItemsGateway {

    public final Map<Integer, org.twelve.entities.Item> itemMap;

    public InMemoryItemGateway(Map<Integer, org.twelve.entities.Item> itemMap) {
        this.itemMap = itemMap;
    }

    @Override
    public void populate(ItemManager itemManager) {
        List<Integer> existingIds = itemManager.getAllItemIds();
        for (Item item : this.itemMap.values()) {
            if (!existingIds.contains(item.getItemID())) {
                itemManager.addToItems(item.getItemID(), item.getName(), item.getDescription(), item.getOwnerID(),
                        item.isApproved());
            }
        }
    }

    @Override
    public void save(int itemId, String name, String description, boolean isApproved, int ownerId) {
        org.twelve.entities.Item item = new Item(itemId, name, description, ownerId);
        if (isApproved) {
            item.approve();
        }
        itemMap.put(item.getItemID(), item);
    }
}
