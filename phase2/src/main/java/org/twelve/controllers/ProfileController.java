package org.twelve.controllers;

import org.twelve.entities.Roles;
import org.twelve.presenters.ProfilePresenter;
import org.twelve.usecases.LoginManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;

public class ProfileController {

    private final StatusManager statusManager;
    private ProfilePresenter profilePresenter;
    private final SessionManager sessionManager;
    private final LoginManager loginManager;

    public ProfileController(UseCasePool useCasePool) {

        statusManager = useCasePool.getStatusManager();
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();

    }

    public void updateProfile() {

        profilePresenter.setVacationStatus(statusManager.getRoleOfAccount(sessionManager.getCurrAccountID()) == Roles.VACATION);
        profilePresenter.setCanVacation(statusManager.canVacation(sessionManager.getCurrAccountID()));
        profilePresenter.setCanRequestUnfreeze(!statusManager.isPending(sessionManager.getCurrAccountID())
                && statusManager.getRoleOfAccount(sessionManager.getCurrAccountID()) == Roles.FROZEN);

    }

    public void setProfilePresenter(ProfilePresenter profilePresenter) {

        this.profilePresenter = profilePresenter;

    }

    public void changePassword(String oldPassword, String newPassword) {

        if (newPassword.isBlank()) {
            profilePresenter.setError("newPwdError");
        } else if (!loginManager.changePassword(sessionManager.getCurrAccountID(), oldPassword, newPassword)) {
            profilePresenter.setError("oldPwdError");
        } else {
            profilePresenter.setError("");
        }

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
