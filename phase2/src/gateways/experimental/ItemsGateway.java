package gateways.experimental;

import usecases.ItemUtility;

public interface ItemsGateway {

    void populate(ItemUtility itemUtility);

    // item_id,name,description,is_approved,owner_id
    void save(int itemId, String name, String description, boolean isApproved, int ownerId);

}
