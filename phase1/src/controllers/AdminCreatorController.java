package controllers;

import gateways.ManualConfig;
import presenters.AdminCreatorPresenter;
import usecases.AccountManager;

public class AdminCreatorController {

    private final AdminCreatorPresenter adminPresenter;

    private final AccountManager accountManager;

    public AdminCreatorController(ManualConfig mc, AdminCreatorPresenter adminPresenter) {
        accountManager = mc.getAccountManager();
        this.adminPresenter = adminPresenter;
    }

    public void run() {
        boolean isValid = false;
        String[] info = new String[2];
        while (!isValid) {
            info[0] = adminPresenter.createAdminUsername();
            if (info[0].equals("-1")) {
                return;
            }
            info[1] = adminPresenter.createAdminPassword();
            if (info[1].equals("-1")) {
                return;
            }
            isValid = accountManager.createAdminAccount(info[0], info[1]);
            if (!isValid) {
                adminPresenter.invalidInput();
            }
        }
    }

}
