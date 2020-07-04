package controllers;

import gateways.ManualConfig;
import presenters.ConsoleWishlistPresenter;
import presenters.TradeCreatorPresenter;
import presenters.WishlistPresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.ItemManager;
import usecases.WishlistUtility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the wishlist screen.
 */
public class WishlistController {

    private WishlistPresenter wishlistPresenter;
    private TradeCreatorPresenter tradeCreatorPresenter;

    private ManualConfig manualConfig;
    private AccountManager accountManager;
    private WishlistUtility wishlistUtility;
    private ItemManager itemManager;
    private AuthManager authManager;

    private ControllerHelper controllerHelper;

    /**
     * Create a controller for the wishlist screen.
     * @param wishlistPresenter A presenter for this controller.
     * @param tradeCreatorPresenter A presenter for {@link controllers.TradeCreatorController}.
     * @param manualConfig Repository of use cases.
     */
    public WishlistController(WishlistPresenter wishlistPresenter, TradeCreatorPresenter tradeCreatorPresenter,
                              ManualConfig manualConfig) {

        this.manualConfig = manualConfig;

        this.accountManager = manualConfig.getAccountManager();
        this.wishlistUtility = manualConfig.getWishlistUtility();
        this.itemManager = manualConfig.getItemManager();
        this.authManager = manualConfig.getAuthManager();

        this.wishlistPresenter = wishlistPresenter;
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        controllerHelper = new ControllerHelper();

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

            if (controllerHelper.isNum(input)) {

                int action = Integer.parseInt(input);
                if (action < actions.size()) {
                    actions.values().toArray(new Runnable[0])[action].run();
                } else {
                    wishlistPresenter.invalidInput();
                }

            }

        } while (!input.equals(String.valueOf(actions.size() - 1)));

    }

    private Map<String, Runnable> generateActions() {

        Map<String, Runnable> actions = new LinkedHashMap<>();

        //TODO just a note that canTrade() checks that an account isn't frozen(which checks if account can lend and borrow so the canBorrow() may be removed as it is redundant
        if (authManager.canBorrow(accountManager.getCurrAccount()) &&
                authManager.canTrade(manualConfig.getTradeUtility(), accountManager.getCurrAccount()))

            actions.put("Start trade.", this::startTrade);

        actions.put("Remove item from wishlist.", this::removeFromWishlist);
        actions.put("Back.", () -> {});
        return actions;
    }

    private void startTrade() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String itemIndex = wishlistPresenter.startTrade();

        while (!controllerHelper.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistInfo.size()) {

            if (controllerHelper.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.startTrade();

        }

        List<Integer> wishlistIds = accountManager.getCurrWishlist();
        int itemId = wishlistIds.get(Integer.parseInt(itemIndex));

        new TradeCreatorController(tradeCreatorPresenter,
                manualConfig, itemManager.getItemById(itemId).getOwnerID(), itemId).run();

    }

    private void removeFromWishlist() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String itemIndex = wishlistPresenter.removeFromWishlist();

        while (!controllerHelper.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistInfo.size()) {

            if (controllerHelper.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.removeFromWishlist();

        }

        List<Integer> wishlistIds = accountManager.getCurrWishlist();
        accountManager.removeItemFromWishlist(wishlistIds.get(Integer.parseInt(itemIndex)));

    }

}
