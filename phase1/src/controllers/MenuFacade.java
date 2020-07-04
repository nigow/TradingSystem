package controllers;

import gateways.ManualConfig;
import presenters.ConsoleMenuPresenter;
import presenters.MenuPresenter;
import usecases.AccountManager;
import usecases.AuthManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages a user's options according to their permissions.
 * @author Maryam
 */
public class MenuFacade {

    /**
     * An instance of AuthManager to access a user's permissions.
     */
    private final AuthManager authManager;

    /**
     * An instance of MenuPresenter to display options.
     */
    private final MenuPresenter menuPresenter;

    private final AccountManager accountManager;

    // TODO: 1. waiting on the following classes to be fixed/created 2. javadoc

    private final FreezingController freezingController;
    private final InventoryController inventoryController;
    private final LendingController lendingController;
    private final TradeController tradeController;
    private final WishlistController wishlistController;
//    private RestrictionsController restrictionsController;
    private final AppealController appealController;
//    private AdminCreatorController adminCreatorController;

    private final ControllerHelper helper;

    /**
     * Initializes MenuFacade based on information from ManualConfig and creates instances of
     * AuthManager and necessary controllers.
     * @param mc An instance of ManualConfig
     */
    public MenuFacade(ManualConfig mc, FreezingController freezingController,
                      InventoryController inventoryController,
                      WishlistController wishlistController,
                      LendingController lendingController,
                      AppealController appealController,
                      MenuPresenter menuPresenter) {
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();

        this.menuPresenter = menuPresenter;
        helper = new ControllerHelper();

        this.freezingController = freezingController;
        this.inventoryController = inventoryController;
        this.lendingController = lendingController;
        tradeController = new TradeController(mc);
        this.wishlistController = wishlistController;
//        restrictionsController = new RestrictionController(mc);
        this.appealController = appealController;
//        adminCreatorController = new AdminCreatorController(mc);
    }

    /**
     * Calls the presenter with options for the user based on their permission and executes the action.
     */
    public void run() {
        while (true) {
            List<String> options = new ArrayList<>();
            List<Runnable> method = new ArrayList<>();

            options.add("Manage your existing trades");
            method.add(tradeController::run);

            options.add("Browse the inventory");
            method.add(inventoryController::run);

            options.add("Manage your wishlist");
            method.add(wishlistController::run);

            if (authManager.canLend(accountManager.getCurrAccount())) {
                options.add("Initiate a trade with a specific account");
                method.add(lendingController::run);
            }

            if (authManager.canChangeRestrictions(accountManager.getCurrAccount())) {
//                options.add("Modify the restriction values of the program");
//                method.add(restrictionsController::run);
            }

            if (authManager.canFreeze(accountManager.getCurrAccount()) && authManager.canUnfreeze(accountManager.getCurrAccount())) {
                options.add("Manage the frozen accounts");
                method.add(freezingController::run);
            }

            if (authManager.canAddAdmin(accountManager.getCurrAccount())) {
//                options.add("Add an admin account");
//                method.add(adminCreatorController::run);
            }

            if (authManager.isFrozen(accountManager.getCurrAccount())) {
//                options.add("Request to be unfrozen");
//                method.add(appealController::run);
            }

            options.add("Logout");

            String action = menuPresenter.displayMenu(options);

            if (helper.isNum(action)) {
                int i = Integer.parseInt(action);
                if (0 <= i && i < method.size())
                    method.get(i).run();
                else if (i == method.size())
                    return;
                else
                    menuPresenter.invalidInput();
            } else
                menuPresenter.invalidInput();
        }
    }
}
