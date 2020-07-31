package org.twelve.presenters.ui;

import org.twelve.presenters.WishlistPresenter;

import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIWishlistPresenter extends ObservablePresenter implements WishlistPresenter {

    private List<String> wishlistItems;
    private List<String> warehouseItems;
    private String selectedItemName;
    private String selectedItemDesc;

    private final ResourceBundle localizedResources;

    public UIWishlistPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setItemLists(new ArrayList<>(), new ArrayList<>());
        setSelectedItemInfo("", "");
    }

    @Override
    public void setItemLists(List<String> wishlistItems, List<String> warehouseItems) {

        List<String> oldWishlistItems = this.wishlistItems;
        this.wishlistItems = wishlistItems;
        propertyChangeSupport.firePropertyChange("wishlistItems", oldWishlistItems, this.wishlistItems);

        List<String> oldWarehouseItems = this.warehouseItems;
        this.warehouseItems = warehouseItems;
        propertyChangeSupport.firePropertyChange("warehouseItems", oldWarehouseItems, this.warehouseItems);

    }

    @Override
    public List<String> getWishlistItems() {
        return wishlistItems;
    }

    @Override
    public List<String> getWarehouseItems() {
        return warehouseItems;
    }

    @Override
    public void setSelectedItemInfo(String name, String desc) {

        String oldItemName = this.selectedItemName;
        this.selectedItemName = MessageFormat.format(localizedResources.getString("itemName"), name);
        propertyChangeSupport.firePropertyChange("selectedItemName", oldItemName, this.selectedItemName);

        String oldItemDesc = this.selectedItemDesc;
        this.selectedItemDesc = MessageFormat.format(localizedResources.getString("itemDesc"), desc);
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldItemDesc, this.selectedItemDesc);

    }

    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }

}
