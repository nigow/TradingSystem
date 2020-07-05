package controllers;

import gateways.ManualConfig;
import presenters.AdminCreatorPresenter;
import usecases.AccountManager;

/**
 * controller that lets admins create an account
 */
public class AdminCreatorController {
    /**
     * an instance of AdminCreatorPresenter to display information
     */
    private AdminCreatorPresenter adminPresenter;
    /**
     * an instance of AccountManager to validate username and password
     */
    private AccountManager accountManager;

    /**
     * an instance of ManualConfig
     */
    private ManualConfig mc;

    /**
     * initializes a constructor for AdminCreatorController
     * @param mc an instance of ManualConfiguration
     * @param adminPresenter an instance of adminPresenter
     */
    public AdminCreatorController(ManualConfig mc, AdminCreatorPresenter adminPresenter) {
        this.mc = mc;
        accountManager = mc.getAccountManager();
        this.adminPresenter = adminPresenter;
    }

    /**
     * shows user necessary steps to create an admin creator
     */
    public void run() {
        boolean isValid = false;
        String[] info;
        while (!isValid) {
            info = adminPresenter.createAdmin();
            isValid = accountManager.createAdminAccount(info[0], info[1]);
            if (!isValid) {
                adminPresenter.invalidInput();
            }
        }
        //TODO return to home
    }

}
