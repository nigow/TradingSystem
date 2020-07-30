package org.twelve.controllers.console;

import org.twelve.entities.Permissions;
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

    /**
     * Requests unfreeze appeal and lets user know that the request was made.
     */
    public void run() {

        while(true){
            List<String> options = new ArrayList<>();
            addCorrectOption(options);
            String action = appealPresenter.displayRequestOptions(options);
            switch (action) {
                case "0":
                    chooseCorrectOption();
                    break;
                case "1":
                    return;
                default:
                    appealPresenter.invalidInput();
                    break;
            }
        }
    }

    private void chooseCorrectOption(){

        if (statusManager.hasPermission(sessionManager.getCurrAccountID(), Permissions.REQUEST_UNFREEZE)){
            statusManager.requestUnfreeze(sessionManager.getCurrAccountID());
            appealPresenter.displayUnfreezeAppeal(sessionManager.getCurrAccountUsername());
        }
        else if(statusManager.canVacation(sessionManager.getCurrAccountID())){
            statusManager.requestVacation(sessionManager.getCurrAccountID());
            appealPresenter.displayVacationAppeal(sessionManager.getCurrAccountUsername());
        }
        else {
            statusManager.completeVacation(sessionManager.getCurrAccountID());
            appealPresenter.displayVacationCompletion(sessionManager.getCurrAccountUsername());
        }

    }

    private void addCorrectOption(List<String> options){
        if (statusManager.hasPermission(sessionManager.getCurrAccountID(), Permissions.REQUEST_UNFREEZE)){
            options.add(appealPresenter.requestUnfreeze());
        }
        else if(statusManager.canVacation(sessionManager.getCurrAccountID())){
            options.add(appealPresenter.requestVacation());
        }
        else {
            options.add(appealPresenter.completeVacation());
        }
        options.add(appealPresenter.returnToMainMenu());
    }

}
