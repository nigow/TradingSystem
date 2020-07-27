package org.twelve.controllers.console;

import org.twelve.presenters.AppealPresenter;
import org.twelve.usecases.*;

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

    //TODO perhaps allow users to request for a vacation(automatic) here,
    // then appealPresenter needs to be updated and there would be options to choose between requesting to unfreeze and vacation

    //TODO new controller for banning/unbanning accounts? or combine it somewhere.
    /**
     * Requests unfreeze appeal and lets user know that the request was made.
     */
    public void run() {
        freezingUtility.requestUnfreeze(sessionManager.getCurrAccountID());
        appealPresenter.displaySuccessfulAppeal(sessionManager.getCurrAccountUsername());
    }

}
