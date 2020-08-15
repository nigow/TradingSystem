package org.twelve.controllers;

import org.twelve.presenters.LoginPresenter;
import org.twelve.usecases.LoginManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

/**
 *  A controller to handle logins
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 */
public class LoginController {

    /**
     * An instance of SessionManager to create an account or get information about an account.
     */
    private final SessionManager sessionManager;

    /**
     * An instance of LoginManager to check login information or permissions.
     */
    private final LoginManager loginManager;

    private LoginPresenter loginPresenter;

    private final UseCasePool useCasePool;

    /**
     * Initializes LoginController with the necessary presenter and use cases.
     *
     * @param useCasePool   An instance of UseCasePool to get the necessary use cases
     */
    public LoginController(UseCasePool useCasePool) {
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();
        this.useCasePool = useCasePool;
    }

    public void setLoginPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    /**
     * Takes in the username and password from the view and logs in the user
     * @param username the username the user enters
     * @param password the password the user enters
     */
    public boolean logIn(String username, String password) {

        useCasePool.populateAll();

        if (loginManager.authenticateLogin(username, password)) {
            loginPresenter.setError("");
            sessionManager.login(username);
            return true;
        } else {
            loginPresenter.setError("failMessage"); // we can add more specific errors if we want here
            return false;
        }
    }

}