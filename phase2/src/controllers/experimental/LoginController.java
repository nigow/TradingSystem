package controllers.experimental;

import controllers.InputHandler;
import controllers.MenuFacade;
import gateways.UseCasePool;
import presenters.HomePresenter;
import usecases.AccountManager;
import usecases.AuthManager;



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
     * Initializes HomeController with the necessary presenter and use cases.
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
     * Calls the presenter and logs in a user based on their information.
     */
    public void logIn(String username, String password) {
        while (true) {
            if (inputHandler.isValidUserPass(username, password) &&
                    authManager.authenticateLogin(username, password)) {
                accountManager.setCurrAccount(username);
                return;
            } else
                homePresenter.displayIncorrectInfo();
        }
    }

}