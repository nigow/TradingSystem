package controllers;

import gateways.ManualConfig;
import presenters.AdminCreatorPresenter;
import usecases.AccountManager;

/**
 * controller to create new admin accounts
 *
 * @author Catherine
 */
public class AdminCreatorController {
    /**
     * an instance of AdminCreatorPresenter to let user know what to do
     */
    private final AdminCreatorPresenter adminPresenter;
    /**
     * an instance of AccountManager to create new account
     */
    private final AccountManager accountManager;
    /**
     * an instance of ControllerInputValidator to check for valid input
     */
    private final InputHandler inputHandler;

    /**
     * initializes AdminCreatorController with necessary presenter and use cases
     *
     * @param mc             an instance of ManualConfig to get use cases
     * @param adminPresenter an instance of AdminPresenter to display information
     */
    public AdminCreatorController(ManualConfig mc, AdminCreatorPresenter adminPresenter) {
        accountManager = mc.getAccountManager();
        this.adminPresenter = adminPresenter;
        inputHandler = new InputHandler();
    }

    /**
     * calls presenters to get username and password for new admin account
     */
    public void run() {
        while (true) {
            String username = adminPresenter.createAdminUsername();
            if (inputHandler.isExitStr(username))
                return;
            String password = adminPresenter.createAdminPassword();
            if (inputHandler.isExitStr(password))
                return;
            if (!inputHandler.isValidUserPass(username, password))
                adminPresenter.showMessage("The characters in that username and password are illegal.");
            else {
                if (accountManager.createAdminAccount(username, password)) {
                    adminPresenter.showMessage("You have added a new admin account.");
                    return;
                } else
                    adminPresenter.showMessage("That username is taken.");
            }
        }
    }

}
