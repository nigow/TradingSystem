package org.twelve.presenters.ui;

public interface MenuPresenter {
    void setInitiateTrade(boolean initiateTrade);
    boolean getInitiateTrade();

    void setModifyRestrictions(boolean modifyRestrictions);
    boolean getModifyRestrictions();

    void setManageAccounts(boolean manageAccounts);
    boolean getManageAccounts();

    void setAddAdmin(boolean addAdmin);
    boolean getAddAdmin();

    void approveItems(boolean approveAdmin);
    boolean approveItems();
}
