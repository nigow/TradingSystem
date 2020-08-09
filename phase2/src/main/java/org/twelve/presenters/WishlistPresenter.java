package org.twelve.presenters;

import java.util.List;

public interface WishlistPresenter {

    void setWishlistItems(List<String> wishlistItems);
    List<String> getWishlistItems();
    void setWarehouseItems(List<String> warehouseItems);
    List<String> getWarehouseItems();
    void setSelectedItemName(String name);
    String getSelectedItemName();
    void setSelectedItemDesc(String desc);
    String getSelectedItemDesc();

}
