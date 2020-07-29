package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.presenters.PresenterPool;
import org.twelve.usecases.UseCasePool;

public class ControllerPool {

    private final LoginController loginController;
    private final RegistrationController registrationController;
    private final MenuController menuController;
    private final WarehouseController warehouseController;

    public ControllerPool(GatewayPool gatewayPool, PresenterPool presenterPool) {

        UseCasePool useCasePool = new UseCasePool(gatewayPool);

        // todo: add refactored controllers and their getters

        loginController = new LoginController(useCasePool);
        registrationController = new RegistrationController(useCasePool, presenterPool.getRegistrationPresenter());
        menuController = new MenuController(useCasePool);
        warehouseController = new WarehouseController(useCasePool, presenterPool.getWarehousePresenter());

    }

    public LoginController getLoginController() {
        return loginController;
    }

    public RegistrationController getRegistrationController() {
        return registrationController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

}
