package controllers;

import gateways.ManualConfig;
import presenters.HomePresenter;
import usecases.AccountManager;
import usecases.AuthManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with creating and authorizing accounts.
 * @author Maryam
 */
public class HomeController {
    /**
     * An instance of HomePresenter to present options.
     */
    private final HomePresenter homePresenter;

    /**
     * An instance of AccountManager to create an account or get information about an account.
     */
    private final AccountManager accountManager;

    /**
     * An instance of AuthManager to check login information or permissions.
     */
    private final AuthManager authManager;

    /**
     * An instance of MenuFacade to be called according to a user's permissions.
     */
    private final MenuFacade menuFacade;

    /**
     * An instance of ControllerInputValidator to check a user's input is valid
     */
    private final ControllerInputValidator controllerInputValidator;

    // TODO: javadoc
    /**
     * Initializes HomeController with the necessary presenter and usecases.
     * @param mc An instance of ManualConfig to get the necessary usecases
     * @param homePresenter An instance of HomePresenter to display information and interact with the user
     * @param menuFacade An instance of MenuFacade to take user's to the next menu
     */
    public HomeController(ManualConfig mc, HomePresenter homePresenter,
                          MenuFacade menuFacade) {
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        this.menuFacade = menuFacade;
        this.homePresenter = homePresenter;
        controllerInputValidator = new ControllerInputValidator();
    }

    /**
     * Calls the presenter with options for the user to login or create an account.
     */
    public void run() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Login");
            options.add("Create an account");
            options.add("Quit");
            String action = homePresenter.displayHomeOptions(options);
            if (action.equals("0"))
                logIn();
            else if (action.equals("1"))
                createAccount();
            else if (action.equals("2"))
                return;
            else
                homePresenter.invalidInput();
        }
    }

    /**
     * Calls the presenter and logs in a user based on their information.
     */
    private void logIn() {
        String[] accountInfo = homePresenter.logIn();
        if (controllerInputValidator.isExitStr(accountInfo[0]) || controllerInputValidator.isExitStr(accountInfo[1]))
            return;
        if (authManager.authenticateLogin(accountInfo[0], accountInfo[1])) {
            accountManager.setCurrAccount(accountInfo[0]);
            menuFacade.run();
        } else {
            homePresenter.invalidInput();
        }
    }

    /**
     * Calls the presenter and creates an account for the user.
     */
    private void createAccount() {
        String[] accountInfo = homePresenter.newAccount();
        if (controllerInputValidator.isExitStr(accountInfo[0]) || controllerInputValidator.isExitStr(accountInfo[1]))
            return;
        if (accountManager.createStandardAccount(accountInfo[0], accountInfo[1]))
            menuFacade.run();
        else
            homePresenter.invalidInput();
    }
}
