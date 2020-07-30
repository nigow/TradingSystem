package org.twelve.controllers.console;

import org.twelve.presenters.AppealPresenter;
import org.twelve.usecases.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that makes appeal requests.
 *
 * @author Catherine
 */
public class AppealController {
    /**
     * An instance of StatusManager to request unfreeze.
     */
    private final StatusManager statusManager;
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
        statusManager = useCasePool.getStatusManager();
        this.appealPresenter = appealPresenter;
    }

    //TODO perhaps allow users to request for a vacation(automatic) here,
    // then appealPresenter needs to be updated and there would be options to choose between requesting to unfreeze and vacation

    //TODO new controller for banning/unbanning accounts? or combine it somewhere.
    /**
     * Requests unfreeze appeal and lets user know that the request was made.
     */
    public void run() {

        switch (action) {
            case "0":
                statusManager.requestUnfreeze(sessionManager.getCurrAccountID());
                appealPresenter.displayUnfreezeAppeal(sessionManager.getCurrAccountUsername());
                break;
            case "1":
                statusManager.requestVacation(sessionManager.getCurrAccountID());
                appealPresenter.displayVacationAppeal(sessionManager.getCurrAccountUsername());
                break;
            case "2":
                statusManager.completeVacation(sessionManager.getCurrAccountID());
                appealPresenter.displayVacationCompletion(sessionManager.getCurrAccountUsername());
                break;
            case "3":
                return;
            default:
                appealPresenter.invalidInput();
                break;
        }
    }

}
