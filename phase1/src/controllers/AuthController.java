package controllers;

import gateways.ManualConfig;
import presenters.ConsoleHomePresenter;
import presenters.HomePresenter;
import usecases.AccountManager;
import usecases.AuthManager;

import javax.swing.*;
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

    private ManualConfig manualConfig;

    /**
     * Initializes AuthController based on information from ManualConfig and creates
     * instances of HomePresenter and MenuFacade.
     * @param mc An instance of ManualConfig
     */
    public AuthController(ManualConfig mc) {
        manualConfig = mc;
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        homePresenter = new ConsoleHomePresenter();
    }

    /**
     * Calls the presenter with options for the user to login or create an account.
     */
    public void run() {
        System.out.println("we are here");
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Login");
            options.add("Create an account");
            options.add("Quit");
            String action = homePresenter.displayHomeOptions(options);
            if (action.equals("0")) {
                logIn();
            } else if (action.equals("1")) {
                createAccount();
            } else if (action.equals("2")) {
                return;
            } else {
                homePresenter.invalidInput();
            }
        }
    }

    /**
     * Calls the presenter and logs in a user based on their information.
     */
    private void logIn() {
        String[] accountInfo = homePresenter.logIn();
        if (authManager.authenticateLogin(accountInfo[0], accountInfo[1])) {
            menuFacade = new MenuFacade(manualConfig); // TODO: bad practice?? shouldn't be a problem until the user logs out
            menuFacade.run();
        } else {
            homePresenter.invalidInput();
            // logIn(); TODO: this is commented out until we implement a good 'back' option
        }
    }

    /**
     * Calls the presenter and creates an account for the user.
     */
    private void createAccount() {
        String[] accountInfo = homePresenter.newAccount();
        if (accountManager.createStandardAccount(accountInfo[0], accountInfo[1])) {
            menuFacade = new MenuFacade(manualConfig); // TODO: bad practice?? shouldn't be a problem until the user logs out
            menuFacade.run();
        } else {
            homePresenter.invalidInput();
            // createAccount(); TODO: this is commented out until we implement a good 'back' option
        }
    }
}
