package org.twelve.controllers;



import org.twelve.presenters.MenuPresenter;
import org.twelve.usecases.ItemUtility;
import org.twelve.usecases.TradeUtility;

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
     * @param useCasePool           An instance of UseCasePool to get the necessary use cases
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
    public MenuFacade(UseCasePool useCasePool, FreezingController freezingController,
                      InventoryController inventoryController,
                      WishlistController wishlistController,
                      LendingController lendingController,
                      AppealController appealController,
                      TradeController tradeController,
                      AdminCreatorController adminCreator,
                      RestrictionController restrictionController,
                      MenuPresenter menuPresenter) {
        authManager = useCasePool.getAuthManager();
        accountManager = useCasePool.getAccountManager();
        tradeUtility = useCasePool.getOldTradeUtility();
        itemUtility = useCasePool.getItemUtility();

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

            options.add(menuPresenter.manageTrades());
            method.add(tradeController::run);

            if (authManager.canBrowseInventory(accountManager.getCurrAccount())) {
                options.add(menuPresenter.browseInventory());
                method.add(inventoryController::run);
            }

            options.add(menuPresenter.manageWishlist());
            method.add(wishlistController::run);

            if (authManager.canTrade(accountManager.getCurrAccount()) &&
                    !itemUtility.getApprovedInventoryOfAccount(accountManager.getCurrAccountID()).isEmpty()) {
                options.add(menuPresenter.initiateTrade());
                method.add(lendingController::run);
            }

            if (authManager.canChangeRestrictions(accountManager.getCurrAccount())) {
                options.add(menuPresenter.modifyRestrictions());
                method.add(restrictionsController::run);
            }

            if (authManager.canFreeze(accountManager.getCurrAccount()) && authManager.canUnfreeze(accountManager.getCurrAccount())) {
                options.add(menuPresenter.manageFrozen());
                method.add(freezingController::run);
            }

            if (authManager.canAddAdmin(accountManager.getCurrAccount())) {
                options.add(menuPresenter.addAdmin());
                method.add(adminCreator::run);
            }

            if (authManager.canRequestUnfreeze(accountManager.getCurrAccount())) {
                options.add(menuPresenter.requestUnfreeze());
                method.add(appealController::run);
            }

            options.add(menuPresenter.logout());

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
