package controllers;

import gateways.ManualConfig;
import presenters.AdminCreatorPresenter;
import usecases.AccountManager;

public class AdminCreatorController {

    private final AdminCreatorPresenter adminPresenter;

    private final AccountManager accountManager;

    private final ControllerInputValidator controllerInputValidator;

    public AdminCreatorController(ManualConfig mc, AdminCreatorPresenter adminPresenter) {
        accountManager = mc.getAccountManager();
        this.adminPresenter = adminPresenter;
        controllerInputValidator = new ControllerInputValidator();
    }

    public void run() {
        while (true) {
            String username = adminPresenter.createAdminUsername();
            if (controllerInputValidator.isExitStr(username))
                return;
            String password = adminPresenter.createAdminPassword();
            if (controllerInputValidator.isExitStr(password))
                return;
            if (!controllerInputValidator.isValidUserPass(username, password))
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
