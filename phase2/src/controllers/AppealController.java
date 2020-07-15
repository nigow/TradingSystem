package controllers;

import gateways.UseCasePool;
import presenters.AppealPresenter;
import usecases.AccountManager;
import usecases.AuthManager;

/**
 * Controller that makes appeal requests.
 *
 * @author Catherine
 */
public class AppealController {
    /**
     * An instance of AuthManager to request unfreeze.
     */
    private final AuthManager authManager;
    /**
     * An instance of AppealPresenter to display message to user.
     */
    private final AppealPresenter appealPresenter;
    /**
     * An instance of AccountManager to get current account.
     */
    private final AccountManager accountManager;

    /**
     * Initializes constructor with necessary use cases and presenter.
     *
     * @param mc              An instance of ManualConfig to get use cases
     * @param appealPresenter An instance of AppealPresenter to display information
     */
    public AppealController(UseCasePool mc, AppealPresenter appealPresenter) {
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        this.appealPresenter = appealPresenter;
    }

    /**
     * Requests unfreeze appeal and lets user know that the request was made.
     */
    public void run() {
        authManager.requestUnfreeze(accountManager.getCurrAccount());
        appealPresenter.displaySuccessfulAppeal(accountManager.getCurrAccountUsername());
    }

}
