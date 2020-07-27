package org.twelve.controllers.experimental;


import org.twelve.controllers.InputHandler;
import org.twelve.controllers.MenuFacade;
import org.twelve.controllers.UseCasePool;
import org.twelve.presenters.HomePresenter;
import org.twelve.usecases.LoginManager;
import org.twelve.usecases.SessionManager;

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
     * An instance of SessionManager to create an account or get information about an account.
     */
    private final SessionManager sessionManager;

    /**
     * An instance of LoginManager to check login information or permissions.
     */
    private final LoginManager loginManager;

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
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();
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
                    loginManager.authenticateLogin(username, password)) {
                sessionManager.login(username);
                return true;
            } else
                // homePresenter.displayIncorrectInfo();
                return false;
        }
    }

}