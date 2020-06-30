package controllers;

import entities.Item;
import presenters.ConsoleWishlistPresenter;
import presenters.WishlistPresenter;
import usecases.AccountManager;
import usecases.ItemManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class WishlistController {

    private WishlistPresenter wishlistPresenter;
    private AccountManager accountManager;
    private Map<String, Runnable> actions;
    private ItemManager itemManager;

    public WishlistController(AccountManager accountManager, ItemManager itemManager) {
        this.accountManager = accountManager;
        this.itemManager = itemManager;

        wishlistPresenter = new ConsoleWishlistPresenter();
        actions = new LinkedHashMap<String, Runnable>(){{

            put("0. Start trade.", () -> startTrade());
            put("1. Remove item from wishlist.", () -> removeFromWishlist());
            put("2. Back.", () -> {});

        }};

    }

    public void run() {

        String input;

        do {

            List<String> options = new ArrayList<>(actions.keySet());
            input = wishlistPresenter.displayWishlistOptions(options);

            if (Pattern.matches("^[0-9]+$", input)) {

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

        // this is placeholder for something that will be a use case soon
        List<String> wishlistInfo = new ArrayList<>();

        for (Item item : itemManager.getAllItems()) {

            if (accountManager.getCurrAccount().getWishlist().contains(item.getItemID())) {

                wishlistInfo.add(item.toString());

            }

        }

        wishlistPresenter.displayWishlist(wishlistInfo);

        String input = wishlistPresenter.startTrade();
        if (Pattern.matches("^[0-9]+$", input)) {

            List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
            int index = Integer.parseInt(input);

            if (index < wishlistIds.size()) {
                // todo: waiting for TradeCreator
            }
        }


    }

    private void removeFromWishlist() {

        // this is placeholder for something that will be a use case soon
        List<String> wishlistInfo = new ArrayList<>();

        for (Item item : itemManager.getAllItems()) {

            if (accountManager.getCurrAccount().getWishlist().contains(item.getItemID())) {

                wishlistInfo.add(item.toString());

            }

        }

        wishlistPresenter.displayWishlist(wishlistInfo);

        String input = wishlistPresenter.removeFromWishlist();
        if (Pattern.matches("^[0-9]+$", input)) {

            List<Integer> wishlistIds = accountManager.getCurrAccount().getWishlist();
            int index = Integer.parseInt(input);

            if (index < wishlistIds.size()) accountManager.removeItemFromWishlist(wishlistIds.get(index));
        }
    }

}
