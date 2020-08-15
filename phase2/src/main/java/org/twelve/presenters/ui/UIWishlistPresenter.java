package org.twelve.presenters.ui;

import org.twelve.presenters.WishlistPresenter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Observable wishlist presenter.
 */
public class UIWishlistPresenter extends ObservablePresenter implements WishlistPresenter {

    private List<String> wishlistItems;
    private List<String> localItems;
    private String selectedItemName;
    private String selectedItemDesc;
    private String selectedItemOwner;

    private final ResourceBundle localizedResources;

    /**
     * Constructor for observable wishlist presenter.
     *
     * @param localizedResources Pack containing any localized strings.
     */
    public UIWishlistPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setWishlistItems(new ArrayList<>());
        setLocalItems(new ArrayList<>());
        setSelectedItemName("");
        setSelectedItemDesc("");
        setSelectedItemOwner("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWishlistItems(List<String> wishlistItems) {
        List<String> oldWishlistItems = this.wishlistItems;
        this.wishlistItems = wishlistItems;
        propertyChangeSupport.firePropertyChange("wishlistItems", oldWishlistItems, this.wishlistItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getWishlistItems() {
        return wishlistItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocalItems(List<String> localItems) {
        List<String> oldLocalItems = this.wishlistItems;
        this.localItems = localItems;
        propertyChangeSupport.firePropertyChange("localItems", oldLocalItems, this.localItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getLocalItems() {
        return localItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedItemName(String name) {
        String oldItemName = this.selectedItemName;
        this.selectedItemName = MessageFormat.format(localizedResources.getString("itemName"), name);
        propertyChangeSupport.firePropertyChange("selectedItemName", oldItemName, this.selectedItemName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedItemDesc(String desc) {
        String oldItemDesc = this.selectedItemDesc;
        this.selectedItemDesc = MessageFormat.format(localizedResources.getString("itemDesc"), desc);
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldItemDesc, this.selectedItemDesc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedItemOwner(String owner) {
        String oldOwner = this.selectedItemOwner;
        this.selectedItemOwner = MessageFormat.format(localizedResources.getString("itemOwner"), owner);
        propertyChangeSupport.firePropertyChange("selectedItemOwner", oldOwner, this.selectedItemOwner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemOwner() {
        return selectedItemOwner;
    }

}
