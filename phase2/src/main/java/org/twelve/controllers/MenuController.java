package org.twelve.controllers;

import org.twelve.entities.Permissions;
import org.twelve.presenters.MenuPresenter;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;

/**
 * Controller for the menu
 */
public class MenuController {

    private final SessionManager sessionManager;
    private final StatusManager statusManager;
    private MenuPresenter menuPresenter;

    /**
     * Initializer for the controller for the menu
     * @param useCasePool an instance of UseCasePool to get all the use cases
     */
    public MenuController(UseCasePool useCasePool) {
        this.sessionManager = useCasePool.getSessionManager();
        statusManager = useCasePool.getStatusManager();

    }

    /**
     * Set the presenter for this controller
     * @param menuPresenter an instance of MenuPresenter
     */
    public void setMenuPresenter(MenuPresenter menuPresenter) {
        this.menuPresenter = menuPresenter;
    }

    /**
     * Set the presenters to display the correct buttons depending on the user's permissions
     */
    public void displayButtons() {
        int accountID = sessionManager.getCurrAccountID();
        menuPresenter.setInitiateTrade(statusManager.hasPermission(accountID, Permissions.TRADE));
        menuPresenter.setManageAccounts(statusManager.hasPermission(accountID, Permissions.FREEZE));
        menuPresenter.setAddAdmin(statusManager.hasPermission(accountID, Permissions.ADD_ADMIN));
        menuPresenter.setApproveItems(statusManager.hasPermission(accountID, Permissions.CONFIRM_ITEM));
        menuPresenter.setAdminWishlist(statusManager.hasPermission(accountID, Permissions.REMOVE_WISHLIST));
        menuPresenter.setCancelTrades(statusManager.hasPermission(accountID, Permissions.CANCEL_TRADE));
        menuPresenter.setCurrentUser(sessionManager.getCurrAccountUsername());
    }

    /**
     * Log out of the current session
     */
    public void logout() {
        sessionManager.logout();
    }

}
