package org.twelve.controllers;


import org.twelve.presenters.OldTradeCreatorPresenter;
import org.twelve.usecases.*;

/**
 * Controller that deals with the trade creation view.
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class TradeCreatorController {
    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final ItemManager itemManager;
    private final WishlistManager wishlistManager;
    private final SessionManager sessionManager;

    private final OldTradeCreatorPresenter oldTradeCreatorPresenter;

    private final int traderOneId;
    private final int traderTwoId;
    private final int itemId;
    private final boolean forceTwoWay;

    private final InputHandler inputHandler;

    /**
     * Create a controller for the trade creation screen.
     *
     * @param oldTradeCreatorPresenter A presenter for this controller.
     * @param useCasePool           Repository of use cases.
     * @param peerId                Id of account trade is being conducted with.
     * @param itemId                Id of item being offered or asked for.
     * @param forceTwoWay           Whether two way trade should be forced.
     */
    public TradeCreatorController(OldTradeCreatorPresenter oldTradeCreatorPresenter,
                                  UseCasePool useCasePool, int peerId, int itemId, boolean forceTwoWay) {

        this.tradeManager = useCasePool.getTradeManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.wishlistManager = useCasePool.getWishlistManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.oldTradeCreatorPresenter = oldTradeCreatorPresenter;

        this.traderOneId = sessionManager.getCurrAccountID();
        this.traderTwoId = peerId;
        this.itemId = itemId;
        this.forceTwoWay = forceTwoWay;

        inputHandler = new InputHandler();

    }


}
