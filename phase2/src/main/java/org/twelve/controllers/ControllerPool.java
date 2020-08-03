package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.presenters.WarehousePresenter;
import org.twelve.usecases.UseCasePool;

public class ControllerPool {

    private final LoginController loginController;
    private final RegistrationController registrationController;
    private final MenuController menuController;
    private final WarehouseController warehouseController;
    private final WishlistController wishlistController;
    private final ProfileController profileController;
    private final InventoryController inventoryController;
    private final ThresholdController thresholdController;
    private final FreezingController freezingController;

    public ControllerPool(GatewayPool gatewayPool) {

        UseCasePool useCasePool = new UseCasePool(gatewayPool);

        // todo: add refactored controllers and their getters

        loginController = new LoginController(useCasePool);
        registrationController = new RegistrationController(useCasePool);
        menuController = new MenuController(useCasePool);
        warehouseController = new WarehouseController(useCasePool);
        wishlistController = new WishlistController(useCasePool);
        profileController = new ProfileController(useCasePool);
        inventoryController = new InventoryController(useCasePool, gatewayPool.getItemsGateway());
        thresholdController = new ThresholdController(useCasePool, gatewayPool.getThresholdsGateway());
        freezingController = new FreezingController(useCasePool);

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

    public WishlistController getWishlistController() {
        return wishlistController;
    }

    public ProfileController getProfileController() {
        return profileController;
    }

    public InventoryController getInventoryController() {
        return inventoryController;
    }

    public ThresholdController getThresholdController() {return thresholdController;}

    public FreezingController getFreezingController() {
        return freezingController;
    }
}
