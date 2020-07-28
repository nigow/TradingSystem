package org.twelve.controllers.console;

import org.twelve.controllers.InputHandler;
import org.twelve.entities.Permissions;
import org.twelve.presenters.HomePresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.LoginManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.Arrays;
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

    private final AccountRepository accountRepository;

    private final LoginManager loginManager;

    /**
     * An instance of MenuFacade to be called according to a user's permissions.
     */
    private final MenuFacade menuFacade;

    /**
     * An instance of ControllerInputValidator to check a user's input is valid.
     */
    private final InputHandler inputHandler;

    private final SessionManager sessionManager;

    /**
     * Initializes HomeController with the necessary presenter and use cases.
     *
     * @param useCasePool   An instance of UseCasePool to get the necessary use cases
     * @param homePresenter An instance of HomePresenter to display information and interact with the user
     * @param menuFacade    An instance of MenuFacade to take user's to the next menu
     */
    public HomeController(UseCasePool useCasePool, HomePresenter homePresenter,
                          MenuFacade menuFacade) {
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();
        accountRepository = useCasePool.getAccountRepository();
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
                    loginManager.authenticateLogin(username, password)) {
                sessionManager.login(username);
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
                List<Permissions> perms = Arrays.asList(Permissions.LOGIN,
                        Permissions.CREATE_ITEM,
                        Permissions.ADD_TO_WISHLIST,
                        Permissions.LEND,
                        Permissions.BORROW,
                        Permissions.BROWSE_INVENTORY,
                        Permissions.REQUEST_VACATION);
                if (accountRepository.createAccount(username, password, perms)) {
                    homePresenter.displaySuccessfulAccount();
                    menuFacade.run();
                    return;
                } else
                    homePresenter.displayOverlappingInfo();
            }
        }
    }
}
