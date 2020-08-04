package org.twelve.presenters.ui;

import java.util.ResourceBundle;

public class UIMenuPresenter extends ObservablePresenter implements MenuPresenter{


    boolean initiateTrade;
    boolean modifyRestrictions;
    boolean manageAccounts;
    boolean addAdmin;
    boolean approveItems;

    public void setInitiateTrade(boolean initiateTrade){
        boolean oldInitiateTrade = initiateTrade;
        propertyChangeSupport.firePropertyChange("initiateTrade", oldInitiateTrade, initiateTrade);
        this.initiateTrade = initiateTrade;
    }

    @Override
    public boolean getInitiateTrade() {
        return initiateTrade;
    }

    @Override
    public void setModifyRestrictions(boolean modifyRestrictions) {
        boolean oldModifyRestrictions = modifyRestrictions;
        propertyChangeSupport.firePropertyChange("modifyRestrictions", oldModifyRestrictions, modifyRestrictions);
        this.modifyRestrictions = modifyRestrictions;
    }

    @Override
    public boolean getModifyRestrictions() {
        return modifyRestrictions;
    }

    @Override
    public void setManageAccounts(boolean manageAccounts) {
        boolean oldManageAccounts = manageAccounts;
        propertyChangeSupport.firePropertyChange("manageAccounts", oldManageAccounts, manageAccounts);
        this.manageAccounts = manageAccounts;
    }

    @Override
    public boolean getManageAccounts() {
        return manageAccounts;
    }

    @Override
    public void setAddAdmin(boolean addAdmin) {
        boolean oldAddAdmin = addAdmin;
        propertyChangeSupport.firePropertyChange("addAdmin", oldAddAdmin, addAdmin);
        this.addAdmin = addAdmin;
    }

    @Override
    public boolean getAddAdmin() {
        return addAdmin;
    }

    @Override
    public void approveItems(boolean approveItems) {
        boolean oldApproveItems = approveItems;
        propertyChangeSupport.firePropertyChange("approveItems", oldApproveItems, approveItems);
        this.approveItems = approveItems;
    }

    @Override
    public boolean approveItems() {
        return approveItems;
    }
}
