package controllers;

import gateways.ManualConfig;
import presenters.ConsoleWishlistPresenter;
import presenters.WishlistPresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.ItemManager;
import usecases.WishlistUtility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WishlistController {

    private ManualConfig manualConfig;
    private WishlistPresenter wishlistPresenter;
    private AccountManager accountManager;
    private WishlistUtility wishlistUtility;
    private ItemManager itemManager;
    private AuthManager authManager;

    private ControllerHelper controllerHelper;

    public WishlistController(ManualConfig manualConfig) {

        this.manualConfig = manualConfig;

        this.accountManager = manualConfig.getAccountManager();
        this.wishlistUtility = manualConfig.getWishlistUtility();
        this.itemManager = manualConfig.getItemManager();
        this.authManager = manualConfig.getAuthManager();

        wishlistPresenter = new ConsoleWishlistPresenter();

        controllerHelper = new ControllerHelper();

    }

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

        if (authManager.canBorrow(accountManager.getCurrAccount()) &&
                authManager.canTrade(manualConfig.getTradeUtility(), accountManager.getCurrAccount()))

            actions.put("Start trade.", this::startTrade);

        actions.put("Remove item from wishlist.", this::removeFromWishlist);
        actions.put("Back.", () -> {});
        return actions;
    }

    private void startTrade() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccount().getAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String itemIndex = wishlistPresenter.startTrade();

        while (!controllerHelper.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistInfo.size()) {

            if (itemIndex.equals("-1")) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.startTrade();

        }

        List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
        int itemId = wishlistIds.get(Integer.parseInt(itemIndex));

        new TradeCreatorController(manualConfig, itemManager.getItemById(itemId).getOwnerID(), itemId).run();

    }

    private void removeFromWishlist() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccount().getAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String itemIndex = wishlistPresenter.removeFromWishlist();

        while (!controllerHelper.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistInfo.size()) {

            if (itemIndex.equals("-1")) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.removeFromWishlist();

        }

        List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
        accountManager.removeItemFromWishlist(wishlistIds.get(Integer.parseInt(itemIndex)));

    }

}
