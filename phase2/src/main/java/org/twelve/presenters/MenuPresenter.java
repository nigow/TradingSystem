package org.twelve.presenters;

public interface MenuPresenter {
    void setInitiateTrade(boolean initiateTrade);
    boolean getInitiateTrade();

    void setModifyThresholds(boolean modifyThresholds);
    boolean getModifyThresholds();

    void setManageAccounts(boolean manageAccounts);
    boolean getManageAccounts();

    void setAddAdmin(boolean addAdmin);
    boolean getAddAdmin();

    void setApproveItems(boolean approveAdmin);
    boolean getApproveItems();

    void setAdminWishlist(boolean adminWishlist);
    boolean getAdminWishlist();
}
