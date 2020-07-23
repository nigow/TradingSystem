package controllers;

import gateways.UseCasePool;
import presenters.TradeCreatorPresenter;
import presenters.WishlistPresenter;
import usecases.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that deals with the wishlist screen.
 */
public class WishlistController {

    private final WishlistPresenter wishlistPresenter;
    private final TradeCreatorPresenter tradeCreatorPresenter;

    private final UseCasePool useCasePool;
    private final AccountManager accountManager;
    private final WishlistManager wishlistManager;
    private final ItemManager itemManager;
    private final AuthManager authManager;
    private final OldTradeUtility oldTradeUtility;

    private final InputHandler inputHandler;

    /**
     * Create a controller for the wishlist screen.
     *
     * @param wishlistPresenter     A presenter for this controller.
     * @param tradeCreatorPresenter A presenter for {@link controllers.TradeCreatorController}.
     * @param useCasePool          Repository of use cases.
     */
    public WishlistController(WishlistPresenter wishlistPresenter, TradeCreatorPresenter tradeCreatorPresenter,
                              UseCasePool useCasePool) {

        this.useCasePool = useCasePool;

        this.oldTradeUtility = useCasePool.getOldTradeUtility();
        this.accountManager = useCasePool.getAccountManager();
        this.wishlistManager = useCasePool.getWishlistManager();
        this.itemManager = useCasePool.getItemManager();
        this.authManager = useCasePool.getAuthManager();

        this.wishlistPresenter = wishlistPresenter;
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        inputHandler = new InputHandler();

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

            if (inputHandler.isNum(input) && Integer.parseInt(input) < actions.size()) {

                actions.values().toArray(new Runnable[0])[Integer.parseInt(input)].run();

            } else {

                wishlistPresenter.invalidInput();

            }

        } while (!input.equals(String.valueOf(actions.size() - 1)));

    }

    private Map<String, Runnable> generateActions() {

        Map<String, Runnable> actions = new LinkedHashMap<>();

        actions.put("View wishlist.", this::displayWishlist);
        actions.put("Remove item from wishlist.", this::removeFromWishlist);

        // tradecreatorcontroller will handle if initiator has to give item in return
        if (authManager.canTrade(accountManager.getCurrAccount()))

            actions.put("Start trade.", this::startTrade);

        actions.put("Back.", () -> {
        });
        return actions;
    }

    private void displayWishlist() {
        List<String> wishlistInfo = wishlistManager.wishlistToString(accountManager.getCurrAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);
    }

    private void startTrade() {

        displayWishlist();

        String itemIndex = wishlistPresenter.startTrade();
        List<Integer> wishlistIds = accountManager.getCurrWishlist();

        while (!inputHandler.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistIds.size()) {

            if (inputHandler.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.startTrade();

        }

        int itemId = wishlistIds.get(Integer.parseInt(itemIndex));

        new TradeCreatorController(tradeCreatorPresenter, useCasePool, itemManager.getOwnerId(itemId), itemId,
                !authManager.lentMoreThanBorrowed(oldTradeUtility)).run();

    }

    private void removeFromWishlist() {

        displayWishlist();

        String itemIndex = wishlistPresenter.removeFromWishlist();
        List<Integer> wishlistIds = accountManager.getCurrWishlist();

        while (!inputHandler.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistIds.size()) {

            if (inputHandler.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.removeFromWishlist();

        }

        accountManager.removeItemFromWishlist(wishlistIds.get(Integer.parseInt(itemIndex)));

    }

}
