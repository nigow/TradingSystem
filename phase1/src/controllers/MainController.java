package controllers;

import gateways.ManualConfig;
import gateways.ManualConfigCSV;
import presenters.*;


import java.io.IOException;

/**
 * A class that initiates the program.
 * @author Maryam
 */
public class MainController {
    ManualConfig manualConfig;
    AuthController authController;
    AppealController appealController;
    AdminCreator adminCreator;
    FreezingController freezingController;
    InventoryController inventoryController;
    LendingController lendingController;
    MenuFacade menuFacade;
    WishlistController wishlistController;

    public MainController() {
        try {
            manualConfig = new ManualConfigCSV();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        inventoryController = new InventoryController(manualConfig,
                new ConsoleInventoryPresenter());
        wishlistController = new WishlistController(new ConsoleWishlistPresenter(),
                new ConsoleTradeCreatorPresenter(), manualConfig);

        // TODO: LendingController should be called with TradeCreatorPresenter.
        lendingController = new LendingController(new ConsoleLendingPresenter(),
                manualConfig);
        // TODO: FreezingController should have a presenter as input.
        freezingController = new FreezingController(manualConfig);

        // TODO: Not all controllers are sent in for completion reasons.
        menuFacade = new MenuFacade(manualConfig, freezingController,
                inventoryController, wishlistController, lendingController,
                new ConsoleMenuPresenter());

        authController = new AuthController(manualConfig,
                    new ConsoleHomePresenter(), menuFacade);

    }

    /**
     * Starts the program and initiates ManualConfig and AuthController.
     */
    public void run() {
        authController.run();
    }
}
