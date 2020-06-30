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
    /**
     * An instance of HomePresenter to present options.
     */
    private HomePresenter homePresenter;

    /**
     * An instance of AccountManager to create an account or get information about an accout.
     */
    private AccountManager accountManager;

    /**
     * An instance of AuthManager to check login information or permissions.
     */
    private AuthManager authManager;

    /**
     * An instance of MenuFacade to be called according to a user's permissions.
     */
    private MenuFacade menuFacade;

    /**
     * Initializes AuthController based on information from ManualConfig and creates
     * instances of HomePresenter and MenuFacade.
     */
    public AuthController() {
        ManualConfig mc = new ManualConfig();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        homePresenter = new ConsoleHomePresenter();
        menuFacade = new MenuFacade();
    }

    /**
     * Calls the presenter with options for the user to login or create an account.
     */
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
            homePresenter.invalidInput();
            run();
        }
    }

    /**
     * Calls the presenter and logs in a user based on their information.
     */
    private void logIn() {
        String[] accountInfo = homePresenter.logIn();
        if (authManager.authenticateLogin(accountInfo[0], accountInfo[1])) {
            Account account = accountManager.getAccountFromUsername(accountInfo[0]);
            menuFacade.run(account.getPermissions());
        } else {
            homePresenter.invalidInput();
            logIn();
        }
    }

    /**
     * Calls the presenter and creates an account for the user.
     */
    private void createAccount() {
        String[] accountInfo = homePresenter.newAccount();
        if (accountManager.createAccount(accountInfo[0], accountInfo[1])) {
            Account account = accountManager.getAccountFromUsername(accountInfo[0]);
            menuFacade.run(account.getPermissions());
        } else {
            homePresenter.invalidInput();
            createAccount();
        }
    }

}