package controllers;

import gateways.UseCasePool;
import presenters.HomePresenter;
import usecases.AccountManager;
import usecases.AuthManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with creating and authorizing accounts.
 *
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
     * An instance of ControllerInputValidator to check a user's input is valid.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes HomeController with the necessary presenter and use cases.
     *
     * @param useCasePool   An instance of UseCasePool to get the necessary use cases
     * @param homePresenter An instance of HomePresenter to display information and interact with the user
     * @param menuFacade    An instance of MenuFacade to take user's to the next menu
     */
    public HomeController(UseCasePool useCasePool, HomePresenter homePresenter,
                          MenuFacade menuFacade) {
        accountManager = useCasePool.getAccountManager();
        authManager = useCasePool.getAuthManager();
        this.menuFacade = menuFacade;
        this.homePresenter = homePresenter;
        inputHandler = new InputHandler();
    }

    /**
     * Calls the presenter with options for the user to login or create an account.
     */
    public void run() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add(homePresenter.logIn());
            options.add(homePresenter.createAccount());
            options.add(homePresenter.quit());
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
            if (inputHandler.isExitStr(username)) {
                return;
            }
            String password = homePresenter.logInPassword();
            if (inputHandler.isExitStr(password))
                return;
            if (inputHandler.isValidUserPass(username, password) &&
                    authManager.authenticateLogin(username, password)) {
                accountManager.setCurrAccount(username);
                menuFacade.run();
                return;
            } else
                homePresenter.displayIncorrectInfo();
        }
    }

    /**
     * Calls the presenter and creates an account for the user.
     */
    private void createAccount() {
        while (true) {
            String username = homePresenter.newAccountUsername();
            if (inputHandler.isExitStr(username)) {
                return;
            }
            String password = homePresenter.newAccountPassword();
            if (inputHandler.isExitStr(password))
                return;
            if (!inputHandler.isValidUserPass(username, password))
                homePresenter.displayInvalidInfo();
            else {
                if (accountManager.createStandardAccount(username, password)) {
                    homePresenter.displaySuccessfulAccount();
                    menuFacade.run();
                    return;
                } else
                    homePresenter.displayOverlappingInfo();
            }
        }
    }
}
