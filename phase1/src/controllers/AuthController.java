package controllers;

import gateways.ManualConfig;
import presenters.ConsoleHomePresenter;
import presenters.HomePresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import entities.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with creating and authorizing accounts.
 * @author Maryam
 */
public class AuthController {
    private HomePresenter homePresenter;
    private AccountManager accountManager;
    private AuthManager authManager;
    private MenuFacade menuFacade;

    public AuthController() {
        ManualConfig mc = new ManualConfig();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        homePresenter = new ConsoleHomePresenter();
        menuFacade = new MenuFacade();
    }

    public void run() {
        List<String> options = new ArrayList<>();
        options.add("Login");
        options.add("Create an Account");
        String action = homePresenter.displayHomeOptions(options);
        if (action.equals("0")) {
            logIn();
        } else if (action.equals("1")) {
            createAccount();
        } else {
            // TODO show error page, waiting on presenter
            run();
        }
    }

    private void logIn() {
        String[] accountInfo = homePresenter.logIn();
        if (authManager.authenticateLogin(accountInfo[0], accountInfo[1])) {
            Account account = accountManager.getAccountFromUsername(accountInfo[0]);
            menuFacade.run(account.getPermissions());
        } else {
            // TODO show error page, waiting on presenter
            logIn();
        }
    }

    private void createAccount() {
        String[] accountInfo = homePresenter.newAccount();
        if (accountManager.createAccount(accountInfo[0], accountInfo[1])) {
            Account account = accountManager.getAccountFromUsername(accountInfo[0]);
            menuFacade.run(account.getPermissions());
        } else {
            // TODO show error page, waiting on presenter
            createAccount();
        }
    }

}
