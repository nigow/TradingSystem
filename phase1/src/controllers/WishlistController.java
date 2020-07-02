package controllers;

import entities.Item;
import gateways.ManualConfig;
import presenters.ConsoleWishlistPresenter;
import presenters.WishlistPresenter;
import usecases.AccountManager;
import usecases.ItemManager;
import usecases.WishlistUtility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class WishlistController {

    private WishlistPresenter wishlistPresenter;
    private AccountManager accountManager;
    private Map<String, Runnable> actions;
    private WishlistUtility wishlistUtility;
    private ManualConfig manualConfig;
    private ItemManager itemManager;

    private ControllerHelper controllerHelper;

    public WishlistController(ManualConfig manualConfig) {

        this.manualConfig = manualConfig;

        this.accountManager = manualConfig.getAccountManager();
        this.wishlistUtility = manualConfig.getWishlistUtility();
        this.itemManager = manualConfig.getItemManager();

        wishlistPresenter = new ConsoleWishlistPresenter();
        actions = new LinkedHashMap<>();

        actions.put("Start trade.", this::startTrade); // todo: check if user has perms to do this, if not hide it
        actions.put("Remove item from wishlist.", this::removeFromWishlist);
        actions.put("Back.", () -> {});

        controllerHelper = new ControllerHelper();

    }

    public void run() {

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

        } while (!input.equals("2"));

    }

    private void startTrade() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccount().getAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String input = wishlistPresenter.startTrade();
        if (controllerHelper.isNum(input)) {

            List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
            int index = Integer.parseInt(input);

            // todo: move improved input validation loop from TradeCreatorController to here
            if (index < wishlistIds.size()) {
                int itemId = wishlistIds.get(Integer.parseInt(input));
                new TradeCreatorController(manualConfig, itemManager.getItemById(itemId).getOwnerID(), itemId).run();
            } else {
                wishlistPresenter.invalidInput();
            }
        }


    }

    private void removeFromWishlist() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccount().getAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String input = wishlistPresenter.removeFromWishlist();
        if (controllerHelper.isNum(input)) {

            List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
            int index = Integer.parseInt(input);

            if (index < wishlistIds.size()) {
                accountManager.removeItemFromWishlist(wishlistIds.get(index));
            } else {
                wishlistPresenter.invalidInput();
            }
        }
    }

}
