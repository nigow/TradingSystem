package controllers;

import gateways.ManualConfig;
import presenters.AppealPresenter;
import usecases.AccountManager;
import usecases.AuthManager;

/**
 * controller that makes appeal request
 *
 * @author Catherine
 */
public class AppealController {
    /**
     * an instance of AuthManager to request unfreeze
     */
    private final AuthManager authManager;
    /**
     * an instance of AppealPresenter to display message to user
     */
    private final AppealPresenter appealPresenter;
    /**
     * an instance of AccountManager to get current account
     */
    private final AccountManager accountManager;

    /**
     * initializes constructor with necessary use cases and presenter
     *
     * @param mc              an instance of ManualConfig to get use cases
     * @param appealPresenter an instance of AppealPresenter to display information
     */
    public AppealController(ManualConfig mc, AppealPresenter appealPresenter) {
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        this.appealPresenter = appealPresenter;
    }

    /**
     * requests unfreeze appeal and lets user know that the request was made
     */
    public void run() {
        authManager.requestUnfreeze(accountManager.getCurrAccount());
        appealPresenter.displaySuccessfulAppeal(accountManager.getCurrAccountUsername());
    }

}
