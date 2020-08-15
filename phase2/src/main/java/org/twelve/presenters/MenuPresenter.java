package org.twelve.presenters;

/**
 * Interface for presenters that deal with the main menu
 */
public interface MenuPresenter {

    /**
     * Set if the user can access the initiate trade view
     *
     * @param initiateTrade whether or not the user can access the initiate trade view
     */
    void setInitiateTrade(boolean initiateTrade);

    /**
     * Get if the user can access the initiate trade view
     *
     * @return whether or not the user can access the initiate trade view
     */
    boolean getInitiateTrade();

    /**
     * Set if the user can access the manage accounts view
     *
     * @param manageAccounts whether or not the user can access the manage accounts view
     */
    void setManageAccounts(boolean manageAccounts);

    /**
     * Get if the user can access the manage accounts view
     *
     * @return whether or not the user can access the manage accounts view
     */
    boolean getManageAccounts();

    /**
     * Set if the user can access the add admins view
     *
     * @param addAdmin whether or not the user can access the add admins view
     */
    void setAddAdmin(boolean addAdmin);

    /**
     * Get if the user can access the add admins view
     *
     * @return if the user can access the add admins view
     */
    boolean getAddAdmin();

    /**
     * Set if the user can access the approve items view
     *
     * @param approveAdmin whether or not the user can access the approve item view
     */
    void setApproveItems(boolean approveAdmin);

    /**
     * Get if the user can access the approve items view
     *
     * @return if the user can access the approve items view
     */
    boolean getApproveItems();

    /**
     * Set if the user can access the admin wishlist view
     *
     * @param adminWishlist whether or not the user can access the admin wishlist view
     */
    void setAdminWishlist(boolean adminWishlist);

    /**
     * Get if the user can access the admin wishlist view
     *
     * @return whether or not the user can access the admin wishlist view
     */
    boolean getAdminWishlist();

    /**
     * Set if the user can access the cancel trades view
     *
     * @param cancelTrades whether or not the user can access the cancel trades view
     */
    void setCancelTrades(boolean cancelTrades);

    /**
     * Get if the user can access the cancel trades view
     *
     * @return if the user can access the cancel trades view
     */
    boolean getCancelTrades();

    /**
     * Set the username of the current user
     *
     * @param user the username of the current user
     */
    void setCurrentUser(String user);

    /**
     * Get the username of the current user
     *
     * @return the username of the current user
     */
    String getCurrentUser();
}
