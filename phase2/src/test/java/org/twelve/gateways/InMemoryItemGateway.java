package test.java.org.twelve.gateways;

import org.twelve.entities.Item;
import org.twelve.usecases.ItemManager;

import java.util.*;

public class InMemoryItemGateway implements org.twelve.gateways.ItemsGateway {

    public final Map<Integer, org.twelve.entities.Item> itemMap;

    public InMemoryItemGateway(Map<Integer, org.twelve.entities.Item> itemMap) {
        this.itemMap = itemMap;
    }

    @Override
    public void populate(ItemManager itemManager) {

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
