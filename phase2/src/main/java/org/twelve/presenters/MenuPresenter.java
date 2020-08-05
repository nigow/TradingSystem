package org.twelve.presenters;

public interface MenuPresenter {
    void setInitiateTrade(boolean initiateTrade);
    boolean getInitiateTrade();

    void setModifyRestrictions(boolean modifyRestrictions);
    boolean getModifyRestrictions();

    void setManageAccounts(boolean manageAccounts);
    boolean getManageAccounts();

    void setAddAdmin(boolean addAdmin);
    boolean getAddAdmin();

    void setApproveItems(boolean approveAdmin);
    boolean getApproveItems();
}
