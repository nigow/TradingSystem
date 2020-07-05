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

    // TODO javadoc

    private ManualConfig manualConfig;
    private final AuthController authController;
    private final AppealController appealController;
    private final AdminCreatorController adminCreator;
    private final FreezingController freezingController;
    private final InventoryController inventoryController;
    private final LendingController lendingController;
    private final WishlistController wishlistController;
    private final TradeController tradeController;
    private final RestrictionController restrictionsController;
    private final MenuFacade menuFacade;

    public MainController() {
        try {
            manualConfig = new ManualConfigCSV();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        inventoryController = new InventoryController(manualConfig,
                new ConsoleInventoryPresenter());
        wishlistController = new WishlistController(new ConsoleWishlistPresenter(), new ConsoleTradeCreatorPresenter(),
                manualConfig);
        appealController = new AppealController(manualConfig,
                new ConsoleAppealPresenter());
        lendingController = new LendingController(new ConsoleLendingPresenter(), manualConfig,
                new ConsoleTradeCreatorPresenter());
        freezingController = new FreezingController(manualConfig, new ConsoleFreezingPresenter());
        tradeController = new TradeController(manualConfig, new ConsoleTradePresenter());
        adminCreator = new AdminCreatorController(manualConfig, new ConsoleAdminCreatorPresenter());
        restrictionsController = new RestrictionController(manualConfig, /*new ConsoleRestrictionsPresenter()*/ null);
        // TODO commented ^ until cat pushes
        menuFacade = new MenuFacade(manualConfig, freezingController, inventoryController, wishlistController,
                lendingController, appealController, tradeController, adminCreator, restrictionsController,
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
