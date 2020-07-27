package org.twelve.controllers;


import org.twelve.presenters.TradeCreatorPresenter;
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

    private final TradeCreatorPresenter tradeCreatorPresenter;

    private final int traderOneId;
    private final int traderTwoId;
    private final int itemId;
    private final boolean forceTwoWay;

    private final InputHandler inputHandler;

    /**
     * Create a controller for the trade creation screen.
     *
     * @param tradeCreatorPresenter A presenter for this controller.
     * @param useCasePool          Repository of use cases.
     * @param peerId                Id of account trade is being conducted with.
     * @param itemId                Id of item being offered or asked for.
     * @param forceTwoWay           Whether two way trade should be forced.
     */
    public TradeCreatorController(TradeCreatorPresenter tradeCreatorPresenter,
                                  UseCasePool useCasePool, int peerId, int itemId, boolean forceTwoWay) {

        this.tradeManager = useCasePool.getTradeManager();
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.wishlistManager = useCasePool.getWishlistManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.tradeCreatorPresenter = tradeCreatorPresenter;

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

        String twoWayTrade = forceTwoWay ? inputHandler.getTrue() : tradeCreatorPresenter.getTwoWayTrade();

        while (!inputHandler.isBool(twoWayTrade)) {

            if (inputHandler.isExitStr(twoWayTrade)) return;
            tradeCreatorPresenter.invalidInput();
            twoWayTrade = tradeCreatorPresenter.getTwoWayTrade();

        }

        if (inputHandler.isTrue(twoWayTrade) && !setUpTwoWayTrade(traderOneItems, traderTwoItems)) return;

        String tradeLocation = tradeCreatorPresenter.getLocation();

        if (inputHandler.isExitStr(tradeLocation)) return;

        String date = tradeCreatorPresenter.getDate();

        while (!inputHandler.isDate(date) || LocalDate.parse(date).isBefore(LocalDate.now())) {

            if (inputHandler.isExitStr(date)) return;
            tradeCreatorPresenter.invalidInput();
            date = tradeCreatorPresenter.getDate();

        }

        String time = tradeCreatorPresenter.getTime();

        while (!inputHandler.isTime(time) ||
                !LocalDate.parse(date).atTime(LocalTime.parse(time)).isAfter(LocalDateTime.now())) {

            if (inputHandler.isExitStr(time)) return;
            tradeCreatorPresenter.invalidInput();
            time = tradeCreatorPresenter.getTime();

        }

        String isPerm = tradeCreatorPresenter.getIsPerm();

        while (!inputHandler.isBool(isPerm)) {

            if (inputHandler.isExitStr(isPerm)) return;
            tradeCreatorPresenter.invalidInput();
            isPerm = tradeCreatorPresenter.getIsPerm();

        }

        // TODO this should be refactored according to the new Trade design  --maryam
        tradeManager.createTrade(LocalDateTime.parse(date + "T" + time), tradeLocation, inputHandler.isTrue(isPerm),
                traderOneId, traderTwoId, traderOneItems, traderTwoItems, accountRepository);
        tradeCreatorPresenter.successMessage();

    }

    private boolean setUpTwoWayTrade(List<Integer> traderOneItems, List<Integer> traderTwoItems) {

        List<String> inventory = traderOneItems.isEmpty() ? itemManager.getApprovedInventoryOfAccountString(traderOneId) :
                itemManager.getApprovedInventoryOfAccountString(traderTwoId);

        if (traderOneItems.isEmpty()) {
            tradeCreatorPresenter.showInventory(inventory);
        }
        else {
            tradeCreatorPresenter.showInventory(accountRepository.getUsernameFromID(traderTwoId), inventory);
        }

        String oppositeItemIndex = tradeCreatorPresenter.getItem();

        while (!inputHandler.isNum(oppositeItemIndex) || Integer.parseInt(oppositeItemIndex) >= inventory.size()) {

            if (inputHandler.isExitStr(oppositeItemIndex)) return false;
            tradeCreatorPresenter.invalidInput();
            oppositeItemIndex = tradeCreatorPresenter.getItem();

        }

        if (traderOneItems.isEmpty()) {
            traderOneItems.add(itemManager.getApprovedInventoryOfAccount(traderOneId).get(Integer.parseInt(oppositeItemIndex)));
        } else {
            traderTwoItems.add(itemManager.getApprovedInventoryOfAccount(traderTwoId).get(Integer.parseInt(oppositeItemIndex)));
        }

        return true;
    }

}
