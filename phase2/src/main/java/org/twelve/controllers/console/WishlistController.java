package org.twelve.controllers.console;

import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.presenters.WishlistPresenter;
import org.twelve.usecases.*;

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
    private final WishlistManager wishlistManager;
    private final ItemManager itemManager;
    private final PermissionManager permissionManager;
    private final SessionManager sessionManager;
    private final FreezingUtility freezingUtility;

    private final InputHandler inputHandler;

    /**
     * Create a controller for the wishlist screen.
     *
     * @param wishlistPresenter     A presenter for this controller.
     * @param tradeCreatorPresenter A presenter for {@link TradeCreatorController}.
     * @param useCasePool          Repository of use cases.
     */
    public WishlistController(WishlistPresenter wishlistPresenter, TradeCreatorPresenter tradeCreatorPresenter,
                              UseCasePool useCasePool) {

        this.useCasePool = useCasePool;

        this.wishlistManager = useCasePool.getWishlistManager();
        this.itemManager = useCasePool.getItemManager();
        this.permissionManager = useCasePool.getPermissionManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.freezingUtility = useCasePool.getFreezingUtility();

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

        actions.put(wishlistPresenter.viewWishlist(), this::displayWishlist);
        actions.put(wishlistPresenter.removeItem(), this::removeFromWishlist);

        // TODO how to check canTrade?
        // tradecreatorcontroller will handle if initiator has to give item in return
        if (permissionManager.canTrade(sessionManager.getCurrAccountID()))

            actions.put(wishlistPresenter.startNewTrade(), this::startTrade);

        actions.put(wishlistPresenter.back(), () -> {
        });
        return actions;
    }

    private void displayWishlist() {
        List<String> wishlistInfo = wishlistManager.getWishlistToString(sessionManager.getCurrAccountID());
        wishlistPresenter.displayWishlist(wishlistInfo);
    }

    private void startTrade() {

        displayWishlist();

        String itemIndex = wishlistPresenter.startTrade();
        List<Integer> wishlistIds = wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID());

        while (!inputHandler.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistIds.size()) {

            if (inputHandler.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.startTrade();

        }

        int itemId = wishlistIds.get(Integer.parseInt(itemIndex));

        // TODO how to check lend more than borrowed?
        new TradeCreatorController(tradeCreatorPresenter, useCasePool, itemManager.getOwnerId(itemId), itemId,
                !freezingUtility.lentMoreThanBorrowed(sessionManager.getCurrAccountID())).run();

    }

    private void removeFromWishlist() {

        displayWishlist();

        String itemIndex = wishlistPresenter.removeFromWishlist();
        List<Integer> wishlistIds = wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID());

        while (!inputHandler.isNum(itemIndex) || Integer.parseInt(itemIndex) >= wishlistIds.size()) {

            if (inputHandler.isExitStr(itemIndex)) return;
            wishlistPresenter.invalidInput();
            itemIndex = wishlistPresenter.removeFromWishlist();

        }

        wishlistManager.removeItemFromWishlist(sessionManager.getCurrAccountID(),
                wishlistIds.get(Integer.parseInt(itemIndex)));

    }

}
