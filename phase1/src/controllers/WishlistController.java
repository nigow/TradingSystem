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

    public WishlistController(ManualConfig manualConfig) {
        this.accountManager = manualConfig.getAccountManager();
        this.wishlistUtility = manualConfig.getWishlistUtility();

        wishlistPresenter = new ConsoleWishlistPresenter();
        actions = new LinkedHashMap<>();

        actions.put("Start trade.", this::startTrade);
        actions.put("Remove item from wishlist.", this::removeFromWishlist);
        actions.put("Back.", () -> {});

    }

    private boolean isNum(String input) {
        return Pattern.matches("^[0-9]+$", input);
    }

    public void run() {

        String input;

        do {

            List<String> options = new ArrayList<>(actions.keySet());
            input = wishlistPresenter.displayWishlistOptions(options);

            if (isNum(input)) {

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
        if (isNum(input)) {

            List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
            int index = Integer.parseInt(input);

            if (index < wishlistIds.size()) {
                // todo: waiting for TradeCreator
            } else {
                wishlistPresenter.invalidInput();
            }
        }


    }

    private void removeFromWishlist() {

        List<String> wishlistInfo = wishlistUtility.wishlistToString(accountManager.getCurrAccount().getAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);

        String input = wishlistPresenter.removeFromWishlist();
        if (isNum(input)) {

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
