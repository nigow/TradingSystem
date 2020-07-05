package controllers;

import gateways.ManualConfig;
import presenters.AppealPresenter;
import usecases.AccountManager;
import usecases.AuthManager;

/**
 * a controller that lets a user appeal to be unfrozen
 * @author Catherine
 */
public class AppealController {
    /**
     * an instance of ManualCOnfig
     */
    private ManualConfig mc;
    /**
     * an instance of authManager to request unfreeze
     */
    private AuthManager authManager;
    /**
     * an instance of appealPresenter to display successful appeal
     */
    private AppealPresenter appealPresenter;
    /**
     * an instance of accountManager
     */
    private AccountManager accountManager;

    /**
     * initializes a constructor for AppealConstructor
     * @param mc an instance of ManualConfiguration
     * @param appealPresenter an instance of AppealPresenter
     */
    public AppealController(ManualConfig mc, AppealPresenter appealPresenter) {
        this.mc = mc;
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        this.appealPresenter = appealPresenter;
    }

    /**
     * lets user request to be unfrozen and let them know that this request was made
     */
    public void run() {
        authManager.requestUnfreeze(accountManager.getCurrAccount());
        appealPresenter.displaySuccessfulAppeal();
        return;
    }

}
