package controllers;

import gateways.ManualConfig;
import presenters.AdminCreatorPresenter;
import usecases.AccountManager;

/**
 * Controller that creates new admin accounts.
 *
 * @author Catherine
 */
public class AdminCreatorController {
    /**
     * An instance of AdminCreatorPresenter to let user know what to do.
     */
    private final AdminCreatorPresenter adminPresenter;
    /**
     * An instance of AccountManager to create new account.
     */
    private final AccountManager accountManager;
    /**
     * An instance of ControllerInputValidator to check for valid input.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes AdminCreatorController with necessary presenter and use cases.
     *
     * @param mc             An instance of ManualConfig to get use cases
     * @param adminPresenter An instance of AdminPresenter to display information
     */
    public AdminCreatorController(ManualConfig mc, AdminCreatorPresenter adminPresenter) {
        accountManager = mc.getAccountManager();
        this.adminPresenter = adminPresenter;
        inputHandler = new InputHandler();
    }

    /**
     * Calls presenters to get username and password for new admin account.
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
