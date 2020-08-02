package org.twelve.controllers;

import org.twelve.presenters.ProfilePresenter;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;

public class ProfileController {

    private StatusManager statusManager;
    private ProfilePresenter profilePresenter;
    private SessionManager sessionManager;

    public ProfileController(UseCasePool useCasePool) {

        statusManager = useCasePool.getStatusManager();
        sessionManager = useCasePool.getSessionManager();
    }

    public void updateProfile() {

        profilePresenter.setVacationStatus(statusManager.isVacationing(sessionManager.getCurrAccountID()));
        profilePresenter.setCanVacation(statusManager.canVacation(sessionManager.getCurrAccountID()));
        profilePresenter.setCanRequestUnfreeze(statusManager.isFrozen(sessionManager.getCurrAccountID()));

    }

    public void setProfilePresenter(ProfilePresenter profilePresenter) {

        this.profilePresenter = profilePresenter;

    }

    public void changePassword(String oldPassword, String newPassword) {

        // todo: waiting for use case support

    }

    public void changeLocation(String newLocation) {

        // todo: waiting for use case support

    }

    public void changeVacationStatus(boolean vacationStatus) {

        if (vacationStatus) {
            statusManager.requestVacation(sessionManager.getCurrAccountID());
        } else {
            statusManager.completeVacation(sessionManager.getCurrAccountID());
        }

    }

    public void requestUnfreeze() {

        statusManager.requestUnfreeze(sessionManager.getCurrAccountID());

    }

}
