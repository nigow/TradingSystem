package org.twelve.presenters.ui;

import org.twelve.presenters.MenuPresenter;

public class UIMenuPresenter extends ObservablePresenter implements MenuPresenter {

    private boolean initiateTrade;
    private boolean modifyRestrictions;
    private boolean manageAccounts;
    private boolean addAdmin;
    private boolean approveItems;
    private boolean adminWishlist;

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
    public void setModifyRestrictions(boolean modifyRestrictions) {
        boolean oldModifyRestrictions = this.modifyRestrictions;
        this.modifyRestrictions = modifyRestrictions;
        propertyChangeSupport.firePropertyChange("modifyRestrictions", oldModifyRestrictions, modifyRestrictions);
    }

    @Override
    public boolean getModifyRestrictions() {
        return modifyRestrictions;
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
}
