package controllers;

import gateways.ManualConfig;
import presenters.AppealPresenter;
import usecases.AccountManager;
import usecases.AuthManager;

public class AppealController {

    private final AuthManager authManager;

    private final AppealPresenter appealPresenter;

    private final AccountManager accountManager;

    public AppealController(ManualConfig mc, AppealPresenter appealPresenter) {
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        this.appealPresenter = appealPresenter;
    }

    public void run() {
        authManager.requestUnfreeze(accountManager.getCurrAccount());
        appealPresenter.displaySuccessfulAppeal();
    }

}
