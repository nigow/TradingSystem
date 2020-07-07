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
            switch (action) {
                case "0":
                    logIn();
                    break;
                case "1":
                    createAccount();
                    break;
                case "2":
                    return;
                default:
                    homePresenter.invalidInput();
                    break;
            }
        }
    }

    /**
     * Calls the presenter and logs in a user based on their information.
     */
    private void logIn() {
        while (true) {
            String username = homePresenter.logInUsername();
            if (controllerInputValidator.isExitStr(username)) {
                return;
            }
            String password = homePresenter.logInPassword();
            if (controllerInputValidator.isExitStr(password))
                return;
            if (controllerInputValidator.isValidUserPass(username, password) &&
                    authManager.authenticateLogin(username, password)) {
                accountManager.setCurrAccount(username);
                menuFacade.run();
                return;
            } else
                homePresenter.showMessage("That username/password combination is incorrect.");
        }
    }

    /**
     * Calls the presenter and creates an account for the user.
     */
    private void createAccount() {
        while (true) {
            String username = homePresenter.newAccountUsername();
            if (controllerInputValidator.isExitStr(username)) {
                return;
            }
            String password = homePresenter.newAccountPassword();
            if (controllerInputValidator.isExitStr(password))
                return;
            if (!controllerInputValidator.isValidUserPass(username, password))
                homePresenter.showMessage("The characters in that username and password are illegal.");
            else {
                if (accountManager.createStandardAccount(username, password)) {
                    homePresenter.showMessage("You have created an account.");
                    menuFacade.run();
                    return;
                } else
                    homePresenter.showMessage("That username is taken.");
            }
        }
    }
}
