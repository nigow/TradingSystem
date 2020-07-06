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

    // TODO: javadoc, but since it's private it's not a priority

    /**
     * Stores all the initializes use cases along with a gateway interface.
     */
    private ManualConfig manualConfig;

    /**
     * Initiates user login/account creation process.
     */
    private final HomeController homeController;

    /**
     * Creates instances of all controllers.
     */
    public MainController() {
        try {
            manualConfig = new ManualConfigCSV();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InventoryController inventoryController = new InventoryController(manualConfig,
                new ConsoleInventoryPresenter());
        WishlistController wishlistController = new WishlistController(new ConsoleWishlistPresenter(), new ConsoleTradeCreatorPresenter(),
                manualConfig);
        AppealController appealController = new AppealController(manualConfig,
                new ConsoleAppealPresenter());
        LendingController lendingController = new LendingController(new ConsoleLendingPresenter(), manualConfig,
                new ConsoleTradeCreatorPresenter());
        FreezingController freezingController = new FreezingController(manualConfig, new ConsoleFreezingPresenter());
        TradeController tradeController = new TradeController(manualConfig, new ConsoleTradePresenter());
        AdminCreatorController adminCreator = new AdminCreatorController(manualConfig, new ConsoleAdminCreatorPresenter());
        RestrictionController restrictionsController = new RestrictionController(manualConfig, new ConsoleRestrictionPresenter());
        MenuFacade menuFacade = new MenuFacade(manualConfig, freezingController, inventoryController, wishlistController,
                lendingController, appealController, tradeController, adminCreator, restrictionsController,
                new ConsoleMenuPresenter());

        homeController = new HomeController(manualConfig,
                    new ConsoleHomePresenter(), menuFacade);

    }

    /**
     * Starts the program and runs the home page.
     */
    public void run() {
        homeController.run();
    }
}
