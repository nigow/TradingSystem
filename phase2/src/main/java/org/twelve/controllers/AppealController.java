package org.twelve.controllers;

import org.twelve.presenters.AppealPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.FreezingUtility;
import org.twelve.usecases.PermissionManager;
import org.twelve.usecases.SessionManager;

/**
 * Controller that makes appeal requests.
 *
 * @author Catherine
 */
public class AppealController {
    /**
     * An instance of FreezingUtility to request unfreeze.
     */
    private final FreezingUtility freezingUtility;
    /**
     * An instance of AppealPresenter to display message to user.
     */
    private final AppealPresenter appealPresenter;

    private final SessionManager sessionManager;

    /**
     * Initializes constructor with necessary use cases and presenter.
     *
     * @param useCasePool     An instance of UseCasePool to get use cases
     * @param appealPresenter An instance of AppealPresenter to display information
     */
    public AppealController(UseCasePool useCasePool, AppealPresenter appealPresenter) {
        sessionManager = useCasePool.getSessionManager();
        freezingUtility = useCasePool.getFreezingUtility();
        this.appealPresenter = appealPresenter;
    }

    /**
     * Requests unfreeze appeal and lets user know that the request was made.
     */
    public void run() {
        freezingUtility.requestUnfreeze(sessionManager.getCurrAccountID());
        appealPresenter.displaySuccessfulAppeal(sessionManager.getCurrAccountUsername());
    }

}