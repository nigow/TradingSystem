package org.twelve.controllers.console;


import org.twelve.gateways.csv.CSVGatewayPool;
import org.twelve.gateways.json.JsonGatewayPool;
import org.twelve.presenters.*;
import org.twelve.usecases.UseCasePool;

import java.io.IOException;

/**
 * Main controller that initiates the program.
 *
 * @author Maryam
 */
public class MainController {

    /**
     * Creates an instance of controllers, starts the program, and runs the home page.
     */
    public void run() {
        UseCasePool useCasePool = new UseCasePool(new JsonGatewayPool());

        /* might be getting deprecated for good
        try {
            useCasePool = new CSVUseCasePool();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        */

        InventoryController inventoryController =
                new InventoryController(useCasePool, new ConsoleInventoryPresenter());

        WishlistController wishlistController =
                new WishlistController(new ConsoleWishlistPresenter(), new ConsoleTradeCreatorPresenter(), useCasePool);

        AppealController appealController =
                new AppealController(useCasePool, new ConsoleAppealPresenter());

        LendingController lendingController =
                new LendingController(new ConsoleLendingPresenter(), useCasePool, new ConsoleTradeCreatorPresenter());

        FreezingController freezingController =
                new FreezingController(useCasePool, new ConsoleFreezingPresenter());

        TradeController tradeController =
                new TradeController(useCasePool, new ConsoleTradePresenter());

        AdminCreatorController adminCreator =
                new AdminCreatorController(useCasePool, new ConsoleAdminCreatorPresenter());

        ThresholdController restrictionsController =
                new ThresholdController(useCasePool, new ConsoleThresholdPresenter());

        MenuFacade menuFacade = new MenuFacade(
                useCasePool,
                freezingController,
                inventoryController,
                wishlistController,
                lendingController,
                appealController,
                tradeController,
                adminCreator,
                restrictionsController,
                new ConsoleMenuPresenter());

        HomeController homeController = new HomeController(useCasePool, new ConsoleHomePresenter(), menuFacade);

        homeController.run();
    }
}
