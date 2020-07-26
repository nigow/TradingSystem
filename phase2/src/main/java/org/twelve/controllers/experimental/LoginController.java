package org.twelve.controllers.experimental;


import org.twelve.controllers.InputHandler;
import org.twelve.controllers.MenuFacade;
import org.twelve.controllers.UseCasePool;
import org.twelve.presenters.HomePresenter;
import org.twelve.usecases.AccountManager;
import org.twelve.usecases.AuthManager;

/**
 *  A controller to handle logins
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 */
public class LoginController {

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
     * Initializes LoginController with the necessary presenter and use cases.
     *
     * @param useCasePool   An instance of UseCasePool to get the necessary use cases
     * @param homePresenter An instance of HomePresenter to display information and interact with the user
     * @param menuFacade    An instance of MenuFacade to take user's to the next menu
     */
    public LoginController(UseCasePool useCasePool, HomePresenter homePresenter,
                           MenuFacade menuFacade) {
        accountManager = useCasePool.getAccountManager();
        authManager = useCasePool.getAuthManager();
        this.menuFacade = menuFacade;
        this.homePresenter = homePresenter;
        inputHandler = new InputHandler();
    }


    /**
     * Takes in the username and password from the view and logs in the user
     * @param username the username the user enters
     * @param password the password the user enters
     */
    public boolean logIn(String username, String password) {
        while (true) {
            if (inputHandler.isValidUserPass(username, password) &&
                    authManager.authenticateLogin(username, password)) {
                accountManager.setCurrAccount(username);
                return true;
            } else
                // homePresenter.displayIncorrectInfo();
                return false;
        }
    }

}