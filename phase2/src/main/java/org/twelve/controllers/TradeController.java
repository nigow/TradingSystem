package org.twelve.controllers;

import org.twelve.presenters.TradePresenter;
import org.twelve.usecases.*;

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

    private final TradeUtility tradeUtility;

    private final TradeManager tradeManager;

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
        tradeUtility = mc.getTradeUtility();
        tradeManager = mc.getTradeManager();
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

            options.add(tradePresenter.viewTrades());
            methods.add(this::showTrades);

            if (!authManager.isFrozen(accountManager.getCurrAccount())) {
                options.add(tradePresenter.editTrade());
                methods.add(this::selectAndChangeTrade);
            }

            options.add(tradePresenter.twoWayRecent());
            methods.add(this::recentTwoWayTrades);

            options.add(tradePresenter.oneWayRecent());
            methods.add(this::recentOneWayTrades);

            options.add(tradePresenter.frequentPartners());
            methods.add(this::frequentPartners);

            options.add(tradePresenter.returnToPrevious());

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
        for (Trade t : tradeUtility.getAllTradesAccount()) {
            tradeManager.setTrade(t);
            trades.add(tradeManager.tradeAsString(accountManager, itemManager));
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
                List<Trade> trades = tradeUtility.getAllTradesAccount();
                if (0 <= ind && ind < trades.size()) {
                    tradeManager.setTrade(trades.get(ind));
                    tradePresenter.displayTrade(tradeManager.tradeAsString(accountManager, itemManager));
                    if (tradeManager.isRejected()) {
                        tradePresenter.displayCancelled();
                    } else if (tradeManager.isUnconfirmed()) {
                        changeUnconfirmedTrade();
                    } else if (tradeManager.isConfirmed()) {
                        changeConfirmedTrade();
                    } else if (tradeManager.isCompleted()) {
                        tradePresenter.displayCompleted();
                    }
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeConfirmedTrade() {
        tradePresenter.displayConfirmed();
        if (tradeManager.getDateTime().isBefore(LocalDateTime.now())) {
            while (true) {
                String ans = tradePresenter.isTradeCompleted();
                if (inputHandler.isTrue(ans)) {
                    tradePresenter.displayCompleted();
                    tradeManager.updateCompletion(accountManager.getCurrAccountID());
                    return;
                } else if (inputHandler.isFalse(ans)) {
                    tradePresenter.displayIncomplete();
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
            if (tradeManager.isEditTurn(accountManager.getCurrAccount())) {
                if (tradeManager.getDateTime().isAfter(LocalDateTime.now())) {
                    options.add("Confirm the time and location for this trade");
                }
                if (tradeManager.getEditedCounter() < MAX_ALLOWED_EDITS) {
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
            tradeManager.updateStatus(TradeStatus.REJECTED);
            tradePresenter.displayRejected();
        } else if (actionInd == 1 && tradeManager.getDateTime().isAfter(LocalDateTime.now())) {
            tradeManager.updateStatus(TradeStatus.CONFIRMED);
            tradeUtility.makeTrade(tradeManager.getTrade(), accountManager, itemManager, itemUtility);
            if (!tradeManager.isPermanent()) {
                tradeManager.reverseTrade(accountManager);
                tradeManager.updateStatus(TradeStatus.CONFIRMED);
                tradeUtility.makeTrade(tradeManager.getTrade(), accountManager, itemManager, itemUtility);
            }
            tradePresenter.displayConfirmed();
        } else {
            changeTradeTimePlace();
            if (tradeManager.getEditedCounter() == MAX_ALLOWED_EDITS) {
                tradeManager.updateStatus(TradeStatus.REJECTED);
                tradePresenter.displayLimitReached();
                tradePresenter.displayCancelled();
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
                tradeManager.editTimePlace(dateTime, location, accountManager.getCurrAccountID());
                tradePresenter.displaySuggestion();
                return;
            }
            tradePresenter.displayFuture();
        }
    }

    private void recentTwoWayTrades() {
        List<String> items = new ArrayList<>();
        for (int itemID : tradeUtility.getRecentTwoWay()) {
            items.add(itemManager.getItemStringById(itemID));
        }
        tradePresenter.displayRecentTwoWayTrade(items);
    }

    private void recentOneWayTrades() {
        List<String> items = new ArrayList<>();
        for (int itemID : tradeUtility.getRecentOneWay()) {
            items.add(itemManager.getItemStringById(itemID));
        }
        tradePresenter.displayRecentOneWayTrade(items);
    }

    private void frequentPartners() {
        List<String> partners = new ArrayList<>();
        for (int id : tradeUtility.getTopThreePartnersIds()) {
            partners.add(accountManager.getAccountStringFromID(id));
        }
        tradePresenter.displayFrequentPartners(partners);
    }
}
