package org.twelve.presenters;

import java.util.List;

public interface WishlistPresenter {

    void setItemLists(List<String> wishlistItems, List<String> warehouseItems);
    List<String> getWishlistItems();
    List<String> getWarehouseItems();


}
