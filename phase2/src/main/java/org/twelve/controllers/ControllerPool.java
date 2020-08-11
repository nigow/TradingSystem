package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.GatewayPoolFactory;
import org.twelve.usecases.UseCasePool;

import java.util.Locale;

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
    private final TradeCreatorController tradeCreatorController;
    private final TradeCollectionController tradeCollectionController;
    private final LandingController landingController;

    public ControllerPool(Locale selectedLanguage, boolean demoMode) {
        GatewayPool gatewayPool = new GatewayPoolFactory().getGatewayPool("json");
        UseCasePool useCasePool = new UseCasePool(gatewayPool);
        if (demoMode) {
            gatewayPool = new GatewayPoolFactory().getGatewayPool("ram");
            useCasePool.switchMode(gatewayPool, true);
        }

        landingController = new LandingController(selectedLanguage, demoMode);
        loginController = new LoginController(useCasePool, gatewayPool);
        registrationController = new RegistrationController(useCasePool, gatewayPool);
        menuController = new MenuController(useCasePool);
        warehouseController = new WarehouseController(useCasePool, gatewayPool);
        wishlistController = new WishlistController(useCasePool, gatewayPool);
        profileController = new ProfileController(useCasePool);
        inventoryController = new InventoryController(useCasePool, gatewayPool);
        thresholdController = new ThresholdController(useCasePool, gatewayPool);
        freezingController = new FreezingController(useCasePool);
        tradeCreatorController = new TradeCreatorController(useCasePool);
        tradeCollectionController = new TradeCollectionController(useCasePool);

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

    public TradeCreatorController getTradeCreatorController() {return tradeCreatorController;}

    public TradeCollectionController getTradeCollectionController() {
        return tradeCollectionController;
    }

    public LandingController getLandingController() {
        return landingController;
    }
}
