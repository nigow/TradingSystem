package controllers;

import gateways.ManualConfig;
import presenters.TradeCreatorPresenter;
import presenters.WishlistPresenter;
import usecases.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the wishlist screen.
 */
public class WishlistController {

    private final WishlistPresenter wishlistPresenter;
    private final TradeCreatorPresenter tradeCreatorPresenter;

    private final ManualConfig manualConfig;
    private final AccountManager accountManager;
    private final WishlistUtility wishlistUtility;
    private final ItemManager itemManager;
    private final AuthManager authManager;
    private final TradeUtility tradeUtility;

    private final ControllerInputValidator controllerInputValidator;

    /**
     * Create a controller for the wishlist screen.
     *
     * @param wishlistPresenter     A presenter for this controller.
     * @param tradeCreatorPresenter A presenter for {@link controllers.TradeCreatorController}.
     * @param manualConfig          Repository of use cases.
     */
    public WishlistController(WishlistPresenter wishlistPresenter, TradeCreatorPresenter tradeCreatorPresenter,
                              ManualConfig manualConfig) {

        this.manualConfig = manualConfig;

        this.tradeUtility = manualConfig.getTradeUtility();
        this.accountManager = manualConfig.getAccountManager();
        this.wishlistUtility = manualConfig.getWishlistUtility();
        this.itemManager = manualConfig.getItemManager();
        this.authManager = manualConfig.getAuthManager();

        this.wishlistPresenter = wishlistPresenter;
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        controllerInputValidator = new ControllerInputValidator();

    }

    /**
     * Run the controller and allow it to take over current screen.
     */
    public void run() {
        Map<String, Runnable> actions = generateActions();

        String input;

        do {

            List<String> options = new ArrayList<>(actions.keySet());
            input = wishlistPresenter.displayWishlistOptions(options);

            if (controllerInputValidator.isNum(input) && Integer.parseInt(input) < actions.size()) {

                actions.values().toArray(new Runnable[0])[Integer.parseInt(input)].run();

            } else {

                wishlistPresenter.invalidInput();

            }

        } while (!input.equals(String.valueOf(actions.size() - 1)));

    }

    private Map<String, Runnable> generateActions() {

        Map<String, Runnable> actions = new LinkedHashMap<>();

        actions.put("View wishlist.", () -> {

           List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccountID());
           wishlistPresenter.displayWishlist(wishlistInfo);

        });
        actions.put("Remove item from wishlist.", this::removeFromWishlist);

        // tradecreatorcontroller will handle if initiator has to give item in return
        if (authManager.canTrade(tradeUtility, accountManager.getCurrAccount()))

            actions.put("Start trade.", this::startTrade);

        actions.put("Back.", () -> {});
        return actions;
    }

    private void startTrade() {

        String itemIndex = wishlistPresenter.startTrade();
        List<Integer> wishlistIds = accountManager.getCurrWishlist();

        while (!controllerInputValidator.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistIds.size()) {

            if (controllerInputValidator.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.startTrade();

        }

        int itemId = wishlistIds.get(Integer.parseInt(itemIndex));

        new TradeCreatorController(tradeCreatorPresenter, manualConfig, itemManager.getOwnerId(itemId), itemId,
                !authManager.lentMoreThanBorrowed(tradeUtility)).run();

    }

    private void removeFromWishlist() {

        String itemIndex = wishlistPresenter.removeFromWishlist();
        List<Integer> wishlistIds = accountManager.getCurrWishlist();

        while (!controllerInputValidator.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistIds.size()) {

            if (controllerInputValidator.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.removeFromWishlist();

        }

        accountManager.removeItemFromWishlist(wishlistIds.get(Integer.parseInt(itemIndex)));

    }

}
