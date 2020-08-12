package org.twelve.presenters.ui;

import org.twelve.presenters.WishlistPresenter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIWishlistPresenter extends ObservablePresenter implements WishlistPresenter {

    private List<String> wishlistItems;
    private List<String> localItems;
    private String selectedItemName;
    private String selectedItemDesc;

    private final ResourceBundle localizedResources;

    public UIWishlistPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setWishlistItems(new ArrayList<>());
        setLocalItems(new ArrayList<>());
        setSelectedItemName("");
        setSelectedItemDesc("");
    }

    @Override
    public void setWishlistItems(List<String> wishlistItems) {
        List<String> oldWishlistItems = this.wishlistItems;
        this.wishlistItems = wishlistItems;
        propertyChangeSupport.firePropertyChange("wishlistItems", oldWishlistItems, this.wishlistItems);
    }

    @Override
    public List<String> getWishlistItems() {
        return wishlistItems;
    }

    @Override
    public void setLocalItems(List<String> localItems) {
        List<String> oldLocalItems = this.wishlistItems;
        this.localItems = localItems;
        propertyChangeSupport.firePropertyChange("localItems", oldLocalItems, this.localItems);
    }

    @Override
    public List<String> getLocalItems() {
        return localItems;
    }

    @Override
    public void setSelectedItemName(String name) {
        String oldItemName = this.selectedItemName;
        this.selectedItemName = MessageFormat.format(localizedResources.getString("itemName"), name);
        propertyChangeSupport.firePropertyChange("selectedItemName", oldItemName, this.selectedItemName);
    }

    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    @Override
    public void setSelectedItemDesc(String desc) {
        String oldItemDesc = this.selectedItemDesc;
        this.selectedItemDesc = MessageFormat.format(localizedResources.getString("itemDesc"), desc);
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldItemDesc, this.selectedItemDesc);
    }

    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }

}
