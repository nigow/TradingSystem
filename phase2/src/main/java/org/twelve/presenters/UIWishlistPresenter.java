package org.twelve.presenters;

import java.util.List;
import java.util.ResourceBundle;

public class UIWishlistPresenter implements WishlistPresenter {

    private List<String> wishlistItems;
    private List<String> warehouseItems;

    private ResourceBundle localizedResources;

    public UIWishlistPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
    }

    @Override
    public void setItemLists(List<String> wishlistItems, List<String> warehouseItems) {
        this.wishlistItems = wishlistItems;
        this.warehouseItems = warehouseItems;
    }

    @Override
    public List<String> getWishlistItems() {
        return wishlistItems;
    }

    @Override
    public List<String> getWarehouseItems() {
        return warehouseItems;
    }

}
