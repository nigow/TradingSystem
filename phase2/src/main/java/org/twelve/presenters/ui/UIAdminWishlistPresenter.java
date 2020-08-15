package org.twelve.presenters.ui;

import org.twelve.presenters.AdminWishlistPresenter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Presenter for the admins wishlist menu
 */
public class UIAdminWishlistPresenter extends ObservablePresenter implements AdminWishlistPresenter {

    private List<String> allUsers;
    private List<String> wishlistOfUser;
    private String selectedItemDescription;

    private final ResourceBundle localizedResources;

    /**
     * Constructor for the presenter
     * @param localizedResources pack containing any localized strings.
     */
    public UIAdminWishlistPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setAllUsers(new ArrayList<>());
        setWishlistOfUser(new ArrayList<>());
        setSelectedItemDescription("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWishlistOfUser(List<String> wishlistItems) {
        List<String> oldWishlistItems = this.wishlistOfUser;
        this.wishlistOfUser = wishlistItems;
        propertyChangeSupport.firePropertyChange("wishlistOfUser", oldWishlistItems, this.wishlistOfUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getWishlistOfUser() {
        return wishlistOfUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllUsers(List<String> allUsers) {
        List<String> oldUsers = this.allUsers;
        this.allUsers = allUsers;
        propertyChangeSupport.firePropertyChange("allUsers", oldUsers, this.allUsers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllUsers() {
        return allUsers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedItemDescription(String desc) {
        String oldItemDesc = this.selectedItemDescription;
        this.selectedItemDescription = MessageFormat.format(localizedResources.getString("itemDescription"), desc);
        propertyChangeSupport.firePropertyChange("selectedItemDescription", oldItemDesc, this.selectedItemDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemDescription() {
        return selectedItemDescription;
    }
}
