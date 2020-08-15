package org.twelve.gateways.ram;

import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.item.ItemManager;

import java.util.Map;

public class InMemoryItemGateway implements ItemsGateway {

    /**
     * pseudo-external storage of items
     */
    private final Map<Integer, String[]> itemMap;

    /**
     * Initialize the gateway
     *
     * @param itemMap a key-pair set of an item id and item object
     */
    public InMemoryItemGateway(Map<Integer, String[]> itemMap) {
        this.itemMap = itemMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(ItemManager itemManager) {
        for (Integer itemId : this.itemMap.keySet()) {
            String[] item = itemMap.get(itemId);
            String name = item[0];
            String desc = item[1];
            boolean isApproved = Boolean.parseBoolean(item[2]);
            int ownerId = Integer.parseInt(item[3]);
            itemManager.addToItems(itemId, name, desc, ownerId, isApproved);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int itemId, String name, String description, boolean isApproved, int ownerId, boolean newItem) {
        String[] item = new String[4];
        item[0] = name;
        item[1] = description;
        item[2] = String.valueOf(isApproved);
        item[3] = String.valueOf(ownerId);
        itemMap.put(itemId, item);
        return true;
    }
}
