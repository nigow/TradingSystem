package org.twelve.controllers.console;


import org.twelve.controllers.InputHandler;
import org.twelve.presenters.OldTradeCreatorPresenter;
import org.twelve.usecases.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with the trade creation screen.
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
     * @param useCasePool          Repository of use cases.
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

    /**
     * Run the controller and allow it to take over current screen.
     */
    public void run() {

        List<Integer> traderOneItems = new ArrayList<>();
        List<Integer> traderTwoItems = new ArrayList<>();

        if (wishlistManager.isInWishlist(sessionManager.getCurrAccountID(), itemId)) {
            traderTwoItems.add(itemId);
        } else {
            traderOneItems.add(itemId);
        }

        String twoWayTrade = forceTwoWay ? inputHandler.getTrue() : oldTradeCreatorPresenter.getTwoWayTrade();

        while (!inputHandler.isBool(twoWayTrade)) {

            if (inputHandler.isExitStr(twoWayTrade)) return;
            oldTradeCreatorPresenter.invalidInput();
            twoWayTrade = oldTradeCreatorPresenter.getTwoWayTrade();

        }

        if (inputHandler.isTrue(twoWayTrade) && !setUpTwoWayTrade(traderOneItems, traderTwoItems)) return;

        String tradeLocation = oldTradeCreatorPresenter.getLocation();

        if (inputHandler.isExitStr(tradeLocation)) return;

        String date = oldTradeCreatorPresenter.getDate();

        while (!inputHandler.isDate(date) || LocalDate.parse(date).isBefore(LocalDate.now())) {

            if (inputHandler.isExitStr(date)) return;
            oldTradeCreatorPresenter.invalidInput();
            date = oldTradeCreatorPresenter.getDate();

        }

        String time = oldTradeCreatorPresenter.getTime();

        while (!inputHandler.isTime(time) ||
                !LocalDate.parse(date).atTime(LocalTime.parse(time)).isAfter(LocalDateTime.now())) {

            if (inputHandler.isExitStr(time)) return;
            oldTradeCreatorPresenter.invalidInput();
            time = oldTradeCreatorPresenter.getTime();

        }

        String isPerm = oldTradeCreatorPresenter.getIsPerm();

        while (!inputHandler.isBool(isPerm)) {

            if (inputHandler.isExitStr(isPerm)) return;
            oldTradeCreatorPresenter.invalidInput();
            isPerm = oldTradeCreatorPresenter.getIsPerm();

        }

        // this can be cleaner
        List<Integer> traderIDs = new ArrayList<>();
        traderIDs.add(traderOneId);
        traderIDs.add(traderTwoId);
        List<Integer> itemIDs = new ArrayList<>();
        itemIDs.addAll(traderOneItems);
        itemIDs.addAll(traderTwoItems);
        tradeManager.createTrade(LocalDateTime.parse(date + "T" + time), tradeLocation,
                inputHandler.isTrue(isPerm), traderIDs, itemIDs);
        oldTradeCreatorPresenter.successMessage();

    }

    private boolean setUpTwoWayTrade(List<Integer> traderOneItems, List<Integer> traderTwoItems) {

        List<String> inventory = traderOneItems.isEmpty() ? itemManager.getApprovedInventoryOfAccountString(traderOneId) :
                itemManager.getApprovedInventoryOfAccountString(traderTwoId);

        if (traderOneItems.isEmpty()) {
            oldTradeCreatorPresenter.showInventory(inventory);
        }
        else {
            oldTradeCreatorPresenter.showInventory(accountRepository.getUsernameFromID(traderTwoId), inventory);
        }

        String oppositeItemIndex = oldTradeCreatorPresenter.getItem();

        while (!inputHandler.isNum(oppositeItemIndex) || Integer.parseInt(oppositeItemIndex) >= inventory.size()) {

            if (inputHandler.isExitStr(oppositeItemIndex)) return false;
            oldTradeCreatorPresenter.invalidInput();
            oppositeItemIndex = oldTradeCreatorPresenter.getItem();

        }

        if (traderOneItems.isEmpty()) {
            traderOneItems.add(itemManager.getApprovedInventoryOfAccount(traderOneId).get(Integer.parseInt(oppositeItemIndex)));
        } else {
            traderTwoItems.add(itemManager.getApprovedInventoryOfAccount(traderTwoId).get(Integer.parseInt(oppositeItemIndex)));
        }

        return true;
    }

}
