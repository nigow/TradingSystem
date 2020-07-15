package controllers;

import gateways.UseCasePool;
import presenters.MenuPresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.ItemUtility;
import usecases.TradeUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that manages a user's options according to their permissions.
 *
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

    private final FreezingController freezingController;

    private final InventoryController inventoryController;

    private final LendingController lendingController;

    private final TradeController tradeController;

    private final WishlistController wishlistController;

    private final RestrictionController restrictionsController;

    private final AppealController appealController;

    private final AdminCreatorController adminCreator;

    private final InputHandler inputHandler;

    private final TradeUtility tradeUtility;

    private final ItemUtility itemUtility;

    /**
     * Initializes MenuFacade with the necessary controllers, presenter, and use cases.
     *
     * @param mc                    An instance of ManualConfig to get the necessary use cases
     * @param freezingController    An instance of FreezingController
     * @param inventoryController   An instance of InventoryController
     * @param wishlistController    An instance of WishlistController
     * @param lendingController     An instance of LendingController
     * @param appealController      An instance of AppealController
     * @param tradeController       An instance of TradeController
     * @param adminCreator          An instance of AdminCreator
     * @param restrictionController An instance of RestrictionController
     * @param menuPresenter         An instance of MenuPresenter to display information and interact with the user
     */
    public MenuFacade(UseCasePool mc, FreezingController freezingController,
                      InventoryController inventoryController,
                      WishlistController wishlistController,
                      LendingController lendingController,
                      AppealController appealController,
                      TradeController tradeController,
                      AdminCreatorController adminCreator,
                      RestrictionController restrictionController,
                      MenuPresenter menuPresenter) {
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        tradeUtility = mc.getTradeUtility();
        itemUtility = mc.getItemUtility();

        this.menuPresenter = menuPresenter;

        inputHandler = new InputHandler();

        this.freezingController = freezingController;
        this.inventoryController = inventoryController;
        this.lendingController = lendingController;
        this.tradeController = tradeController;
        this.wishlistController = wishlistController;
        this.restrictionsController = restrictionController;
        this.appealController = appealController;
        this.adminCreator = adminCreator;
    }

    /**
     * Calls the presenter with options for the user based on their permission and executes the action.
     */
    public void run() {

        tradeUtility.setAccount(accountManager.getCurrAccount());

        while (true) {
            List<String> options = new ArrayList<>();
            List<Runnable> method = new ArrayList<>();

            options.add("Manage your existing trades");
            method.add(tradeController::run);

            if (authManager.canBrowseInventory(accountManager.getCurrAccount())) {
                options.add("Browse the inventory");
                method.add(inventoryController::run);
            }

            options.add("Manage your wishlist");
            method.add(wishlistController::run);

            if (authManager.canTrade(accountManager.getCurrAccount()) &&
                    !itemUtility.getApprovedInventoryOfAccount(accountManager.getCurrAccountID()).isEmpty()) {
                options.add("Initiate a trade with a specific account");
                method.add(lendingController::run);
            }

            if (authManager.canChangeRestrictions(accountManager.getCurrAccount())) {
                options.add("Modify the restriction values of the program");
                method.add(restrictionsController::run);
            }

            if (authManager.canFreeze(accountManager.getCurrAccount()) && authManager.canUnfreeze(accountManager.getCurrAccount())) {
                options.add("Manage the frozen accounts");
                method.add(freezingController::run);
            }

            if (authManager.canAddAdmin(accountManager.getCurrAccount())) {
                options.add("Add an admin account");
                method.add(adminCreator::run);
            }

            if (authManager.canRequestUnfreeze(accountManager.getCurrAccount())) {
                options.add("Request to be unfrozen");
                method.add(appealController::run);
            }

            options.add("Logout");

            String action = menuPresenter.displayMenu(options);

            if (inputHandler.isNum(action)) {
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
