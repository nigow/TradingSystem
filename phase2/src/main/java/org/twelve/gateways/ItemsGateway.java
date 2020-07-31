package org.twelve.gateways;

import org.twelve.usecases.ItemManager;
import org.twelve.usecases.ItemUtility;

public interface ItemsGateway {

    boolean populate(ItemManager itemManager);

    // item_id,name,description,is_approved,owner_id
    boolean save(int itemId, String name, String description, boolean isApproved, int ownerId, boolean newItem);

}
