package controllers;

import gateways.ManualConfig;
import presenters.TradeCreatorPresenter;
import usecases.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the trade creation screen.
 */
public class TradeCreatorController {


    private final TradeManager tradeManager;
    private final AccountManager accountManager;
    private final ItemUtility itemUtility;

    private final TradeCreatorPresenter tradeCreatorPresenter;

    private final int traderOneId;
    private final int traderTwoId;
    private final int itemId;
    private final boolean forceTwoWay;

    private final ControllerInputValidator controllerInputValidator;

    /**
     * Create a controller for the trade creation screen.
     *
     * @param tradeCreatorPresenter A presenter for this controller.
     * @param manualConfig          Repository of use cases.
     * @param peerId                Id of account trade is being conducted with.
     * @param itemId                Id of item being offered or asked for.
     * @param forceTwoWay           Whether two way trade should be forced.
     */
    public TradeCreatorController(TradeCreatorPresenter tradeCreatorPresenter,
                                  ManualConfig manualConfig, int peerId, int itemId, boolean forceTwoWay) {

        this.tradeManager = manualConfig.getTradeManager();
        this.accountManager = manualConfig.getAccountManager();
        this.itemUtility = manualConfig.getItemUtility();
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        this.traderOneId = accountManager.getCurrAccountID();
        this.traderTwoId = peerId;
        this.itemId = itemId;
        this.forceTwoWay = forceTwoWay;

        controllerInputValidator = new ControllerInputValidator();

    }

    /**
     * Run the controller and allow it to take over current screen.
     */
    public void run() {

        List<Integer> traderOneItems = new ArrayList<>();
        List<Integer> traderTwoItems = new ArrayList<>();

        if (accountManager.isInWishlist(itemId)) {
            traderTwoItems.add(itemId);
        } else {
            traderOneItems.add(itemId);
        }

        String twoWayTrade = forceTwoWay ? controllerInputValidator.getTrue() : tradeCreatorPresenter.getTwoWayTrade();

        while (!controllerInputValidator.isBool(twoWayTrade)) {

            if (controllerInputValidator.isExitStr(twoWayTrade)) return;
            tradeCreatorPresenter.invalidInput();
            twoWayTrade = tradeCreatorPresenter.getTwoWayTrade();

        }

        if (controllerInputValidator.isTrue(twoWayTrade) && !setUpTwoWayTrade(traderOneItems, traderTwoItems)) return;

        String tradeLocation = tradeCreatorPresenter.getLocation();

        if (controllerInputValidator.isExitStr(tradeLocation)) return;

        String date = tradeCreatorPresenter.getDate();

        while (!controllerInputValidator.isDate(date) || LocalDate.parse(date).isBefore(LocalDate.now())) {

            if (controllerInputValidator.isExitStr(date)) return;
            tradeCreatorPresenter.invalidInput();
            date = tradeCreatorPresenter.getDate();

        }

        String time = tradeCreatorPresenter.getTime();

        while (!controllerInputValidator.isTime(time) ||
                !LocalDate.parse(date).atTime(LocalTime.parse(time)).isAfter(LocalDateTime.now())) {

            if (controllerInputValidator.isExitStr(time)) return;
            tradeCreatorPresenter.invalidInput();
            time = tradeCreatorPresenter.getTime();

        }

        String isPerm = tradeCreatorPresenter.getIsPerm();

        while (!controllerInputValidator.isBool(isPerm)) {

            if (controllerInputValidator.isExitStr(isPerm)) return;
            tradeCreatorPresenter.invalidInput();
            isPerm = tradeCreatorPresenter.getIsPerm();

        }

        tradeManager.createTrade(LocalDateTime.parse(date + "T" + time), tradeLocation, controllerInputValidator.isTrue(isPerm),
                traderOneId, traderTwoId, traderOneItems, traderTwoItems, accountManager);
        tradeCreatorPresenter.successMessage();

    }

    private boolean setUpTwoWayTrade(List<Integer> traderOneItems, List<Integer> traderTwoItems) {

        String oppositeAccountUsername = traderOneItems.isEmpty() ? "your" : accountManager.getUsernameFromID(traderTwoId) + "'s";

        List<String> inventory = traderOneItems.isEmpty() ? itemUtility.getApprovedInventoryOfAccountString(traderOneId) :
                itemUtility.getApprovedInventoryOfAccountString(traderTwoId);

        tradeCreatorPresenter.showInventory(oppositeAccountUsername, inventory);
        String oppositeItemIndex = tradeCreatorPresenter.getItem();

        while (!controllerInputValidator.isNum(oppositeItemIndex) || Integer.parseInt(oppositeItemIndex) >= inventory.size()) {

            if (controllerInputValidator.isExitStr(oppositeItemIndex)) return false;
            tradeCreatorPresenter.invalidInput();
            oppositeItemIndex = tradeCreatorPresenter.getItem();

        }

        if (traderOneItems.isEmpty()) {
            traderOneItems.add(itemUtility.getApprovedInventoryOfAccount(traderOneId).get(Integer.parseInt(oppositeItemIndex)).getItemID());
        } else {
            traderTwoItems.add(itemUtility.getApprovedInventoryOfAccount(traderTwoId).get(Integer.parseInt(oppositeItemIndex)).getItemID());
        }

        return true;
    }

}
