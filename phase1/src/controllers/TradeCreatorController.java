package controllers;

import gateways.ManualConfig;
import presenters.ConsoleTradeCreatorPresenter;
import presenters.TradeCreatorPresenter;
import usecases.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the trade creation screen.
 */
public class TradeCreatorController {


    private TradeManager tradeManager;
    private AccountManager accountManager;
    private ItemUtility itemUtility;

    private TradeCreatorPresenter tradeCreatorPresenter;

    private int traderOneId;
    private int traderTwoId;
    private int itemId;

    private ControllerHelper controllerHelper;

    /**
     * Create a controller for the trade creation screen.
     * @param manualConfig Repository of use cases.
     * @param peerId Id of account trade is being conducted with.
     * @param itemId Id of item being offered or asked for.
     */
    public TradeCreatorController(TradeCreatorPresenter tradeCreatorPresenter,
                                  ManualConfig manualConfig, int peerId, int itemId) {

        this.tradeManager = manualConfig.getTradeManager();
        this.accountManager = manualConfig.getAccountManager();
        this.itemUtility = manualConfig.getItemUtility();
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        this.traderOneId = accountManager.getCurrAccount().getAccountID();
        this.traderTwoId = peerId;
        this.itemId = itemId;

        controllerHelper = new ControllerHelper();

    }

    /**
     * Run the controller and allow it to take over current screen.
     */
    public void run() {

        List<Integer> traderOneItems = new ArrayList<>();
        List<Integer> traderTwoItems = new ArrayList<>();

        // this is a sketchy way of determining whether the trade started as a lend or borrow
        if (accountManager.getCurrAccount().getWishlist().contains(itemId)) {
            traderTwoItems.add(itemId);
        } else {
            traderOneItems.add(itemId);
        }

        String twoWayTrade = tradeCreatorPresenter.getTwoWayTrade();

        while (!controllerHelper.isBool(twoWayTrade)) {

            if (twoWayTrade.equals("-1")) return;
            tradeCreatorPresenter.invalidInput();
            twoWayTrade = tradeCreatorPresenter.getTwoWayTrade();

        }

        if (twoWayTrade.equals("y")) setUpTwoWayTrade(traderOneItems, traderTwoItems);

        String tradeLocation = tradeCreatorPresenter.getLocation();

        String date = tradeCreatorPresenter.getDate();

        while (!controllerHelper.isDate(date)) {

            if (date.equals("-1")) return;
            tradeCreatorPresenter.invalidInput();
            date = tradeCreatorPresenter.getDate();

        }

        String time = tradeCreatorPresenter.getTime();

        while (!controllerHelper.isTime(time)) {

            if (time.equals("-1")) return;
            tradeCreatorPresenter.invalidInput();
            time = tradeCreatorPresenter.getDate();

        }

        String isPerm = tradeCreatorPresenter.getIsPerm();

        while (!controllerHelper.isBool(isPerm)) {

            if (time.equals("-1")) return;
            tradeCreatorPresenter.invalidInput();
            isPerm = tradeCreatorPresenter.getIsPerm();

        }

        tradeManager.createTrade(LocalDateTime.parse(date + "T" + time), tradeLocation, isPerm.equals("y"),
                                 traderOneId, traderTwoId, traderOneItems, traderTwoItems);

    }

    private void setUpTwoWayTrade(List<Integer> traderOneItems, List<Integer> traderTwoItems) {

        String oppositeAccountUsername = traderOneItems.isEmpty() ? accountManager.getAccountFromID(traderTwoId).getUsername() :
                accountManager.getCurrAccount().getUsername();

        List<String> inventory = traderOneItems.isEmpty() ? itemUtility.getInventoryOfAccountString(traderOneId) :
                itemUtility.getInventoryOfAccountString(traderTwoId);

        tradeCreatorPresenter.showInventory(oppositeAccountUsername, inventory);
        String oppositeItemIndex = tradeCreatorPresenter.getItem();

        while (!controllerHelper.isNum(oppositeItemIndex) || Integer.parseInt(oppositeItemIndex) >= inventory.size()) {

            if (oppositeItemIndex.equals("-1")) return;
            tradeCreatorPresenter.invalidInput();
            oppositeItemIndex = tradeCreatorPresenter.getItem();

        }

        if (traderOneItems.isEmpty()) {
            traderOneItems.add(itemUtility.getInventoryOfAccount(traderOneId).get(Integer.parseInt(oppositeItemIndex)).getItemID());
        } else {
            traderTwoItems.add(itemUtility.getInventoryOfAccount(traderTwoId).get(Integer.parseInt(oppositeItemIndex)).getItemID());
        }

    }

}
