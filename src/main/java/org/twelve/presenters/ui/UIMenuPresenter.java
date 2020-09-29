package org.twelve.presenters.ui;

import org.twelve.presenters.MenuPresenter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Presenter for the main menu view
 */
public class UIMenuPresenter extends ObservablePresenter implements MenuPresenter {

    private boolean initiateTrade;
    private boolean manageAccounts;
    private boolean addAdmin;
    private boolean approveItems;
    private boolean adminWishlist;
    private boolean cancelTrades;
    private String currentUser;
    private final ResourceBundle localizedResources;

    /**
     * Constructor for the presenter for the main menu view
     *
     * @param localizedResources pack containing any localized strings
     */
    public UIMenuPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        setCurrentUser("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInitiateTrade(boolean initiateTrade) {
        boolean oldInitiateTrade = this.initiateTrade;
        this.initiateTrade = initiateTrade;
        propertyChangeSupport.firePropertyChange("initiateTrade", oldInitiateTrade, initiateTrade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getInitiateTrade() {
        return initiateTrade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setManageAccounts(boolean manageAccounts) {
        boolean oldManageAccounts = this.manageAccounts;
        this.manageAccounts = manageAccounts;
        propertyChangeSupport.firePropertyChange("manageAccounts", oldManageAccounts, manageAccounts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getManageAccounts() {
        return manageAccounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAddAdmin(boolean addAdmin) {
        boolean oldAddAdmin = this.addAdmin;
        this.addAdmin = addAdmin;
        propertyChangeSupport.firePropertyChange("addAdmin", oldAddAdmin, addAdmin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAddAdmin() {
        return addAdmin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApproveItems(boolean approveItems) {
        boolean oldApproveItems = this.approveItems;
        this.approveItems = approveItems;
        propertyChangeSupport.firePropertyChange("approveItems", oldApproveItems, approveItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getApproveItems() {
        return approveItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdminWishlist(boolean adminWishlist) {
        boolean oldAdminWishlist = this.adminWishlist;
        this.adminWishlist = adminWishlist;
        propertyChangeSupport.firePropertyChange("adminWishlist", oldAdminWishlist, adminWishlist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAdminWishlist() {
        return adminWishlist;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelTrades(boolean cancelTrades) {
        boolean oldCancelTrades = this.cancelTrades;
        this.cancelTrades = cancelTrades;
        propertyChangeSupport.firePropertyChange("cancelTrades", oldCancelTrades, cancelTrades);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCancelTrades() {
        return cancelTrades;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentUser(String user) {
        String oldCurrentUser = this.currentUser;
        this.currentUser = MessageFormat.format(localizedResources.getString("currentUser"), user);
        propertyChangeSupport.firePropertyChange("currentUser", oldCurrentUser, this.currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentUser() {
        return currentUser;
    }
}
