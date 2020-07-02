package controllers;

import entities.Trade;
import gateways.ManualConfig;
import presenters.ConsoleTradeCreatorPresenter;
import presenters.TradeCreatorPresenter;
import usecases.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TradeCreatorController {


    private TradeManager tradeManager;
    private AccountManager accountManager;
    private ItemUtility itemUtility;

    private TradeCreatorPresenter tradeCreatorPresenter;

    private int peerId;
    private int itemId;

    private ControllerHelper controllerHelper;

    public TradeCreatorController(ManualConfig manualConfig, int peerId, int itemId) {
        this.tradeManager = manualConfig.getTradeManager();
        this.accountManager = manualConfig.getAccountManager();
        this.itemUtility = manualConfig.getItemUtility();
        this.tradeCreatorPresenter = new ConsoleTradeCreatorPresenter();


        this.peerId = peerId;
        this.itemId = itemId;

        controllerHelper = new ControllerHelper();

    }

    public void run() {

        int traderOneId = accountManager.getCurrAccount().getAccountID();
        int traderTwoId = peerId;
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

        if (twoWayTrade.equals("y")) {

            String oppositeAccountUsername = traderOneItems.isEmpty() ? accountManager.getAccountFromID(peerId).getUsername() :
                    accountManager.getCurrAccount().getUsername();

            List<String> inventory = traderOneItems.isEmpty() ? itemUtility.getInventoryOfAccountString(traderOneId) :
                    itemUtility.getInventoryOfAccountString(traderTwoId);

            tradeCreatorPresenter.showInventory(oppositeAccountUsername, inventory);
            String oppositeItemIndex = tradeCreatorPresenter.getItem();

            while (!controllerHelper.isNum(oppositeItemIndex) && Integer.parseInt(oppositeItemIndex) < inventory.size()) {

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

}
