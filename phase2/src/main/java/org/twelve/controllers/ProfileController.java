package org.twelve.controllers;

import org.twelve.entities.Roles;
import org.twelve.entities.Trade;
import org.twelve.presenters.ProfilePresenter;
import org.twelve.usecases.*;

public class ProfileController {

    private final StatusManager statusManager;
    private ProfilePresenter profilePresenter;
    private final SessionManager sessionManager;
    private final LoginManager loginManager;
    private final CityManager cityManager;
    private final TradeManager tradeManager;

    public ProfileController(UseCasePool useCasePool) {

        statusManager = useCasePool.getStatusManager();
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();
        tradeManager = useCasePool.getTradeManager();
        cityManager = useCasePool.getCityManager();
    }

    public void updateProfile() {

        profilePresenter.setVacationStatus(statusManager.getRoleOfAccount(sessionManager.getCurrAccountID()) == Roles.VACATION);
        profilePresenter.setCanVacation(statusManager.canVacation(sessionManager.getCurrAccountID()));
        profilePresenter.setCanBecomeTrusted(tradeManager.canBeTrusted(sessionManager.getCurrAccountID()));
        profilePresenter.setCanRequestUnfreeze(!statusManager.isPending(sessionManager.getCurrAccountID())
                && statusManager.getRoleOfAccount(sessionManager.getCurrAccountID()) == Roles.FROZEN);
        profilePresenter.setExistingCities(cityManager.getAllCities());

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

    public void becomeTrusted() {
        statusManager.trustAccount(sessionManager.getCurrAccountID());
    }
}
