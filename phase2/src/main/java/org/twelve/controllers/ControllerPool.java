package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.GatewayPoolFactory;
import org.twelve.usecases.UseCasePool;

import java.util.Locale;

/**
 * Initializes controllers and provides a pool for accessing them
 */
public class ControllerPool {

    private final LoginController loginController;
    private final RegistrationController registrationController;
    private final MenuController menuController;
    private final WarehouseController warehouseController;
    private final WishlistController wishlistController;
    private final ProfileController profileController;
    private final InventoryController inventoryController;
    private final ThresholdController thresholdController;
    private final AccountsController accountsController;
    private final TradeCreatorController tradeCreatorController;
    private final TradeListController tradeListController;
    private final LandingController landingController;
    private final AdminWishlistController adminWishlistController;
    private final TradeEditorController tradeEditorController;
    private final TradeCancellationController tradeCancellationController;

    /**
     * Creates all the required controllers for the program
     *
     * @param selectedLanguage the selected language the controllers should use
     * @param demoMode         whether or not the program should run in demo mode
     */
    public ControllerPool(Locale selectedLanguage, boolean demoMode) {
        GatewayPool gatewayPool = new GatewayPoolFactory().getGatewayPool("json");
        UseCasePool useCasePool = new UseCasePool(gatewayPool);
        if (demoMode) {
            gatewayPool = new GatewayPoolFactory().getGatewayPool("ram");
            useCasePool.switchMode(gatewayPool, true);
        }

        landingController = new LandingController(selectedLanguage, demoMode, useCasePool);
        loginController = new LoginController(useCasePool);
        registrationController = new RegistrationController(useCasePool);
        menuController = new MenuController(useCasePool);
        warehouseController = new WarehouseController(useCasePool);
        wishlistController = new WishlistController(useCasePool);
        profileController = new ProfileController(useCasePool);
        inventoryController = new InventoryController(useCasePool);
        thresholdController = new ThresholdController(useCasePool);
        accountsController = new AccountsController(useCasePool);
        tradeCreatorController = new TradeCreatorController(useCasePool);
        tradeListController = new TradeListController(useCasePool);
        adminWishlistController = new AdminWishlistController(useCasePool);
        tradeEditorController = new TradeEditorController(useCasePool);
        tradeCancellationController = new TradeCancellationController(useCasePool);

    }

    /**
     * Getter for LoginController
     *
     * @return LoginController
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * Getter for RegistrationController
     *
     * @return RegistrationController
     */
    public RegistrationController getRegistrationController() {
        return registrationController;
    }

    /**
     * Getter for MenuController
     *
     * @return MenuController
     */
    public MenuController getMenuController() {
        return menuController;
    }

    /**
     * Getter for WarehouseController
     *
     * @return WarehouseController
     */
    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    /**
     * Getter for WishlistController
     *
     * @return WishlistController
     */
    public WishlistController getWishlistController() {
        return wishlistController;
    }

    /**
     * Getter for ProfileController
     *
     * @return ProfileController
     */
    public ProfileController getProfileController() {
        return profileController;
    }

    /**
     * Getter for InventoryController
     *
     * @return InventoryController
     */
    public InventoryController getInventoryController() {
        return inventoryController;
    }

    /**
     * Getter for ThresholdController
     *
     * @return ThresholdController
     */
    public ThresholdController getThresholdController() {
        return thresholdController;
    }

    /**
     * Getter for AccountsController
     *
     * @return AccountsController
     */
    public AccountsController getAccountsController() {
        return accountsController;
    }

    /**
     * Getter for TradeCreatorController
     *
     * @return TradeCreatorController
     */
    public TradeCreatorController getTradeCreatorController() {
        return tradeCreatorController;
    }

    /**
     * Getter for TradeListController
     *
     * @return TradeListController
     */
    public TradeListController getTradeListController() {
        return tradeListController;
    }

    /**
     * Getter for LandingController
     *
     * @return LandingController
     */
    public LandingController getLandingController() {
        return landingController;
    }

    /**
     * Getter for AdminWishlistController
     *
     * @return AdminWishlistController
     */
    public AdminWishlistController getAdminWishlistController() {
        return adminWishlistController;
    }

    /**
     * Getter for TradeEditorController
     *
     * @return TradeEditorController
     */
    public TradeEditorController getTradeEditorController() {
        return tradeEditorController;
    }

    /**
     * Getter for TradeCancellationController
     *
     * @return TradeCancellationController
     */
    public TradeCancellationController getTradeCancellationController() {
        return tradeCancellationController;
    }
}
