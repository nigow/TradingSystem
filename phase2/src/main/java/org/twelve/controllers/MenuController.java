package org.twelve.controllers;

import org.twelve.entities.Permissions;
import org.twelve.presenters.MenuPresenter;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;

public class MenuController {

    private final SessionManager sessionManager;
    private final StatusManager statusManager;
    private MenuPresenter menuPresenter;

    public MenuController(UseCasePool useCasePool) {
        this.sessionManager = useCasePool.getSessionManager();
        statusManager = useCasePool.getStatusManager();

    }

    public void setMenuPresenter(MenuPresenter menuPresenter) {
        this.menuPresenter = menuPresenter;
    }

    public void displayButtons() {
        int accountID = sessionManager.getCurrAccountID();
        menuPresenter.setInitiateTrade(statusManager.hasPermission(accountID, Permissions.TRADE));
        menuPresenter.setModifyThresholds(statusManager.hasPermission(accountID, Permissions.CHANGE_THRESHOLDS));
        menuPresenter.setManageAccounts(statusManager.hasPermission(accountID, Permissions.FREEZE));
        menuPresenter.setAddAdmin(statusManager.hasPermission(accountID, Permissions.ADD_ADMIN));
        menuPresenter.setApproveItems(statusManager.hasPermission(accountID, Permissions.CONFIRM_ITEM));
        menuPresenter.setAdminWishlist(statusManager.hasPermission(accountID, Permissions.REMOVE_WISHLIST));
        menuPresenter.setCancelTrades(statusManager.hasPermission(accountID, Permissions.CANCEL_TRADE));
        menuPresenter.setCurrentUser(sessionManager.getCurrAccountUsername());
    }

    public void logout() {
        sessionManager.logout();
    }

}
