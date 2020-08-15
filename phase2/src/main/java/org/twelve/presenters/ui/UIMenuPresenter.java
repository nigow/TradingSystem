package org.twelve.presenters.ui;

import org.twelve.presenters.MenuPresenter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class UIMenuPresenter extends ObservablePresenter implements MenuPresenter {

    private boolean initiateTrade;
    private boolean modifyThresholds;
    private boolean manageAccounts;
    private boolean addAdmin;
    private boolean approveItems;
    private boolean adminWishlist;
    private boolean cancelTrades;
    private String currentUser;
    private final ResourceBundle localizedResources;

    public UIMenuPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        setCurrentUser("");
    }

    public void setInitiateTrade(boolean initiateTrade){
        boolean oldInitiateTrade = this.initiateTrade;
        this.initiateTrade = initiateTrade;
        propertyChangeSupport.firePropertyChange("initiateTrade", oldInitiateTrade, initiateTrade);
    }

    @Override
    public boolean getInitiateTrade() {
        return initiateTrade;
    }

    @Override
    public void setManageAccounts(boolean manageAccounts) {
        boolean oldManageAccounts = this.manageAccounts;
        this.manageAccounts = manageAccounts;
        propertyChangeSupport.firePropertyChange("manageAccounts", oldManageAccounts, manageAccounts);
    }

    @Override
    public boolean getManageAccounts() {
        return manageAccounts;
    }

    @Override
    public void setAddAdmin(boolean addAdmin) {
        boolean oldAddAdmin = this.addAdmin;
        this.addAdmin = addAdmin;
        propertyChangeSupport.firePropertyChange("addAdmin", oldAddAdmin, addAdmin);
    }

    @Override
    public boolean getAddAdmin() {
        return addAdmin;
    }

    @Override
    public void setApproveItems(boolean approveItems) {
        boolean oldApproveItems = this.approveItems;
        this.approveItems = approveItems;
        propertyChangeSupport.firePropertyChange("approveItems", oldApproveItems, approveItems);
    }

    @Override
    public boolean getApproveItems() {
        return approveItems;
    }

    @Override
    public void setAdminWishlist(boolean adminWishlist) {
        boolean oldAdminWishlist = this.adminWishlist;
        this.adminWishlist = adminWishlist;
        propertyChangeSupport.firePropertyChange("adminWishlist", oldAdminWishlist, adminWishlist);
    }

    @Override
    public boolean getAdminWishlist() {
        return adminWishlist;
    }

    @Override
    public void setCancelTrades(boolean cancelTrades) {
        boolean oldCancelTrades = this.cancelTrades;
        this.cancelTrades = cancelTrades;
        propertyChangeSupport.firePropertyChange("cancelTrades", oldCancelTrades, cancelTrades);
    }

    @Override
    public boolean getCancelTrades() {
        return cancelTrades;
    }

    @Override
    public void setCurrentUser(String user) {
        String oldCurrentUser = this.currentUser;
        this.currentUser = MessageFormat.format(localizedResources.getString("currentUser"), user);
        propertyChangeSupport.firePropertyChange("currentUser", oldCurrentUser, this.currentUser);
    }

    @Override
    public String getCurrentUser() {
        return currentUser;
    }
}
