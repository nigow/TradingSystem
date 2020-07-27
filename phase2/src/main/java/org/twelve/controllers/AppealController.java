package org.twelve.controllers;

import org.twelve.presenters.AppealPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.FreezingUtility;
import org.twelve.usecases.PermissionManager;

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
    /**
     * An instance of ???? to get current account. #TODO: How to get current account?
     */
    private final AccountRepository accountRepository;

    /**
     * Initializes constructor with necessary use cases and presenter.
     *
     * @param useCasePool     An instance of UseCasePool to get use cases
     * @param appealPresenter An instance of AppealPresenter to display information
     */
    public AppealController(UseCasePool useCasePool, AppealPresenter appealPresenter) {
        freezingUtility = useCasePool.getFreezingUtility();
        accountRepository = useCasePool.getAccountRepository();
        this.appealPresenter = appealPresenter;
    }

    /**
     * Requests unfreeze appeal and lets user know that the request was made.
     */
    public void run() {
        //TODO: Replace these with the SessionManager usecase when it's written.
        freezingUtility.requestUnfreeze(accountRepository.getCurrAccount());
        appealPresenter.displaySuccessfulAppeal(accountRepository.getCurrAccountUsername());

    }

}
