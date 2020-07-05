package controllers;

import gateways.ManualConfig;
import presenters.AppealPresenter;
import usecases.AccountManager;
import usecases.AuthManager;

public class AppealController {

    private ManualConfig mc;

    private AuthManager authManager;

    private AppealPresenter appealPresenter;

    private AccountManager accountManager;

    public AppealController(ManualConfig mc, AppealPresenter appealPresenter) {
        this.mc = mc;
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        this.appealPresenter = appealPresenter;
    }

    public void run() {
        authManager.requestUnfreeze(accountManager.getCurrAccount());
        appealPresenter.displaySuccessfulAppeal();
    }

}
