package controllers;

import gateways.ManualConfig;
import presenters.AdminPresenter;
import usecases.AccountManager;

public class AdminCreator {

    private AdminPresenter adminPresenter;

    private AccountManager accountManager;

    private ManualConfig mc;

    public AdminCreator(ManualConfig mc, AdminPresenter adminPresenter) {
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
