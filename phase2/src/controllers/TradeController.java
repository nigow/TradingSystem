package controllers;

import entities.OldTrade;
import entities.TradeStatus;
import gateways.UseCasePool;
import presenters.TradePresenter;
import usecases.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with user's past trades and making changes to them.
 *
 * @author Maryam
 */
public class TradeController {

    private final TradePresenter tradePresenter;

    private final AuthManager authManager;

    private final AccountManager accountManager;

    private final OldTradeUtility oldTradeUtility;

    private final OldTradeManager oldTradeManager;

    private final ItemManager itemManager;

    private final ItemUtility itemUtility;

    private final InputHandler inputHandler;

    private final int MAX_ALLOWED_EDITS = 6;

    /**
     * Initialized TradeController by setting necessary use cases and presenter.
     *
     * @param mc             An instance of ManualConfig to get necessary use cases
     * @param tradePresenter An instance of TradePresenter to display and get information from the user
     */
    public TradeController(UseCasePool mc, TradePresenter tradePresenter) {
        this.tradePresenter = tradePresenter;
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        oldTradeUtility = mc.getOldTradeUtility();
        oldTradeManager = mc.getOldTradeManager();
        itemManager = mc.getItemManager();
        itemUtility = mc.getItemUtility();
        inputHandler = new InputHandler();
    }

    /**
     * Displays trade-managing options to the user and interacts with them to update their information.
     */
    public void run() {
        while (true) {
            List<String> options = new ArrayList<>();
            List<Runnable> methods = new ArrayList<>();

            options.add("View your trades");
            methods.add(this::showTrades);

            if (!authManager.isFrozen(accountManager.getCurrAccount())) {
                options.add("Select a trade to edit");
                methods.add(this::selectAndChangeTrade);
            }

            options.add("View items given away in recent two-way trades");
            methods.add(this::recentTwoWayTrades);

            options.add("View items given away in recent one-way trades");
            methods.add(this::recentOneWayTrades);

            options.add("View your most frequent trading partners");
            methods.add(this::frequentPartners);

            options.add("Return to previous menu");

            String action = tradePresenter.displayTradeOptions(options);

            if (inputHandler.isNum(action)) {
                int i = Integer.parseInt(action);
                if (0 <= i && i < methods.size())
                    methods.get(i).run();
                else if (i == methods.size())
                    return;
                else
                    tradePresenter.invalidInput();
            } else
                tradePresenter.invalidInput();
        }
    }

    private void showTrades() {
        List<String> trades = new ArrayList<>();
        for (OldTrade t : oldTradeUtility.getAllTradesAccount()) {
            oldTradeManager.setOldTrade(t);
            trades.add(oldTradeManager.tradeAsString(accountManager, itemManager));
        }
        tradePresenter.displayTrades(trades);
    }

    private void selectAndChangeTrade() {
        while (true) {
            showTrades();
            String index = tradePresenter.selectTrade();
            if (inputHandler.isExitStr(index))
                return;
            if (inputHandler.isNum(index)) {
                int ind = Integer.parseInt(index);
                List<OldTrade> oldTrades = oldTradeUtility.getAllTradesAccount();
                if (0 <= ind && ind < oldTrades.size()) {
                    oldTradeManager.setOldTrade(oldTrades.get(ind));
                    tradePresenter.showMessage(oldTradeManager.tradeAsString(accountManager, itemManager));
                    if (oldTradeManager.isRejected()) {
                        tradePresenter.showMessage("This trade has been cancelled.");
                    } else if (oldTradeManager.isUnconfirmed()) {
                        changeUnconfirmedTrade();
                    } else if (oldTradeManager.isConfirmed()) {
                        changeConfirmedTrade();
                    } else if (oldTradeManager.isCompleted()) {
                        tradePresenter.showMessage("This trade has already been completed.");
                    }
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeConfirmedTrade() {
        tradePresenter.showMessage("You have confirmed the time and location for this trade.");
        if (oldTradeManager.getDateTime().isBefore(LocalDateTime.now())) {
            while (true) {
                tradePresenter.showMessage("Has this trade been completed?");
                String ans = tradePresenter.yesOrNo();
                if (inputHandler.isTrue(ans)) {
                    tradePresenter.showMessage("You have marked this trade as complete.");
                    oldTradeManager.updateCompletion(accountManager.getCurrAccountID());
                    return;
                } else if (inputHandler.isFalse(ans)) {
                    tradePresenter.showMessage("Okay.");
                    return;
                } else
                    tradePresenter.invalidInput();
            }
        }
    }

    private void changeUnconfirmedTrade() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Reject or cancel this trade");
            if (oldTradeManager.isEditTurn(accountManager.getCurrAccount())) {
                if (oldTradeManager.getDateTime().isAfter(LocalDateTime.now())) {
                    options.add("Confirm the time and location for this trade");
                }
                if (oldTradeManager.getEditedCounter() < MAX_ALLOWED_EDITS) {
                    options.add("Edit the time and location for this trade");
                }
            }
            options.add("Go back");
            String action = tradePresenter.displayTradeOptions(options);
            if (inputHandler.isNum(action)) {
                int actionInd = Integer.parseInt(action);
                if (actionInd == options.size() - 1)
                    return;
                if (0 <= actionInd && actionInd < options.size()) {
                    changeUnconfirmedTradeActions(actionInd);
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeUnconfirmedTradeActions(int actionInd) {
        if (actionInd == 0) {
            oldTradeManager.updateStatus(TradeStatus.REJECTED);
            tradePresenter.showMessage("You rejected this trade.");
        } else if (actionInd == 1 && oldTradeManager.getDateTime().isAfter(LocalDateTime.now())) {
            oldTradeManager.updateStatus(TradeStatus.CONFIRMED);
            oldTradeUtility.makeTrade(oldTradeManager.getOldTrade(), accountManager, itemManager, itemUtility);
            if (!oldTradeManager.isPermanent()) {
                oldTradeManager.reverseTrade(accountManager);
                oldTradeManager.updateStatus(TradeStatus.CONFIRMED);
                oldTradeUtility.makeTrade(oldTradeManager.getOldTrade(), accountManager, itemManager, itemUtility);
            }
            tradePresenter.showMessage("You confirmed the time and location for this trade.");
        } else {
            changeTradeTimePlace();
            if (oldTradeManager.getEditedCounter() == MAX_ALLOWED_EDITS) {
                oldTradeManager.updateStatus(TradeStatus.REJECTED);
                tradePresenter.showMessage("You have edited " +
                        "the time and location for this trade too many times, and it has been cancelled.");
            }
        }
    }

    private void changeTradeTimePlace() {
        String location = tradePresenter.editTradeLocation();
        if (inputHandler.isExitStr(location))
            return;

        while (!inputHandler.isValidCSVStr(location)) {
            tradePresenter.invalidInput();
            location = tradePresenter.editTradeLocation();
            if (inputHandler.isExitStr(location))
                return;
        }

        while (true) {
            String date = tradePresenter.editTradeDate();
            if (inputHandler.isExitStr(date))
                return;

            while (!inputHandler.isDate(date)) {
                tradePresenter.invalidInput();
                date = tradePresenter.editTradeDate();
                if (inputHandler.isExitStr(date))
                    return;
            }

            String time = tradePresenter.editTradeTime();
            if (inputHandler.isExitStr(time))
                return;

            while (!inputHandler.isTime(time)) {
                tradePresenter.invalidInput();
                time = tradePresenter.editTradeTime();
                if (inputHandler.isExitStr(time))
                    return;
            }

            LocalDateTime dateTime =
                    LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (dateTime.isAfter(LocalDateTime.now())) {
                oldTradeManager.editTimePlace(dateTime, location, accountManager.getCurrAccountID());
                tradePresenter.showMessage("You have suggested a new time and location for this trade.");
                return;
            }
            tradePresenter.showMessage("You need to choose a date and time in the future.");
        }
    }

    private void recentTwoWayTrades() {
        List<String> items = new ArrayList<>();
        for (int itemID : oldTradeUtility.getRecentTwoWay()) {
            items.add(itemManager.getItemStringById(itemID));
        }
        tradePresenter.displayRecentTwoWayTrade(items);
    }

    private void recentOneWayTrades() {
        List<String> items = new ArrayList<>();
        for (int itemID : oldTradeUtility.getRecentOneWay()) {
            items.add(itemManager.getItemStringById(itemID));
        }
        tradePresenter.displayRecentOneWayTrade(items);
    }

    private void frequentPartners() {
        List<String> partners = new ArrayList<>();
        for (int id : oldTradeUtility.getTopThreePartnersIds()) {
            partners.add(accountManager.getAccountStringFromID(id));
        }
        tradePresenter.displayFrequentPartners(partners);
    }
}
