package org.twelve.controllers;

import org.twelve.presenters.LoginPresenter;
import org.twelve.usecases.system.LoginManager;
import org.twelve.usecases.system.SessionManager;
import org.twelve.usecases.UseCasePool;

/**
 * A controller to handle logins
 */
public class LoginController {

    private final SessionManager sessionManager;

    private final LoginManager loginManager;

    private LoginPresenter loginPresenter;

    private final UseCasePool useCasePool;

    /**
     * Initializes LoginController with the necessary presenter and use cases.
     *
     * @param useCasePool An instance of UseCasePool to get the necessary use cases
     */
    public LoginController(UseCasePool useCasePool) {
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Set the presenter for this controller
     *
     * @param loginPresenter An instance of a class that implements {@link org.twelve.presenters.LoginPresenter}
     */
    public void setLoginPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    /**
     * Takes in the username and password from the view and logs in the user
     *
     * @param username the username the user enters
     * @param password the password the user enters
     * @return whether login was successful
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