package controllers;

import gateways.ManualConfig;
import presenters.AdminCreatorPresenter;
import usecases.AccountManager;

public class AdminCreatorController {

    private AdminCreatorPresenter adminPresenter;

    private AccountManager accountManager;

    private ManualConfig mc;

    public AdminCreatorController(ManualConfig mc, AdminCreatorPresenter adminPresenter) {
        this.mc = mc;
        accountManager = mc.getAccountManager();
        this.adminPresenter = adminPresenter;
    }

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
        //TODO returns user to main menu
    }

}
