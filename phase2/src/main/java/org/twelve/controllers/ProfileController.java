package org.twelve.controllers;

import org.twelve.entities.Roles;
import org.twelve.gateways.CitiesGateway;
import org.twelve.gateways.GatewayPool;
import org.twelve.presenters.ProfilePresenter;
import org.twelve.usecases.*;

public class ProfileController {

    private final StatusManager statusManager;
    private ProfilePresenter profilePresenter;
    private final SessionManager sessionManager;
    private final LoginManager loginManager;
    private final CityManager cityManager;
    private final TradeManager tradeManager;
    private final InputHandler inputHandler;
    private final CitiesGateway citiesGateway;

    public ProfileController(UseCasePool useCasePool, GatewayPool gatewayPool) {

        statusManager = useCasePool.getStatusManager();
        sessionManager = useCasePool.getSessionManager();
        loginManager = useCasePool.getLoginManager();
        tradeManager = useCasePool.getTradeManager();
        cityManager = useCasePool.getCityManager();
        inputHandler = new InputHandler();
        citiesGateway = gatewayPool.getCitiesGateway();

    }

    public void updateProfile() {

        profilePresenter.setVacationStatus(statusManager.getRoleOfAccount(sessionManager.getCurrAccountID()) == Roles.VACATION);
        profilePresenter.setCanVacation(statusManager.canVacation(sessionManager.getCurrAccountID()));
        profilePresenter.setCanBecomeTrusted(tradeManager.canBeTrusted(sessionManager.getCurrAccountID()));
        profilePresenter.setCanRequestUnfreeze(!statusManager.isPending(sessionManager.getCurrAccountID())
                && statusManager.getRoleOfAccount(sessionManager.getCurrAccountID()) == Roles.FROZEN);

        citiesGateway.populate(cityManager);
        profilePresenter.setExistingCities(cityManager.getAllCities());
        profilePresenter.setCurrentLocation(cityManager.getLocationOfAccount(sessionManager.getCurrAccountID()));

    }

    public void setProfilePresenter(ProfilePresenter profilePresenter) {

        this.profilePresenter = profilePresenter;

    }

    public void changePassword(String oldPassword, String newPassword) {

        if (newPassword.isBlank()) {
            profilePresenter.setPasswordError("newPwdError");
        } else if (!loginManager.changePassword(sessionManager.getCurrAccountID(), oldPassword, newPassword)) {
            profilePresenter.setPasswordError("oldPwdError");
        } else {
            profilePresenter.setPasswordError("");
        }

    }

    public void changeLocation(String location) {

        citiesGateway.populate(cityManager);
        if (!cityManager.getAllCities().contains(location)) {
            if (inputHandler.isValidLocation(location)) {
                cityManager.createCity(location);
                cityManager.changeAccountLocation(sessionManager.getCurrAccountID(), location);
            } else {
                profilePresenter.setLocationError("badLocation");
            }
        }

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
