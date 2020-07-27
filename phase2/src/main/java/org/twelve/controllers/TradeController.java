package org.twelve.controllers;

import org.twelve.entities.TradeStatus;
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

    private final PermissionManager permissionManager;

    private final AccountRepository accountRepository;

    private final TradeManager tradeManager;

    private final ItemManager itemManager;

    private final InputHandler inputHandler;

    private final SessionManager sessionManager;

    private final FreezingUtility freezingUtility;

    /**
     * Initialized TradeController by setting necessary use cases and presenter.
     *
     * @param useCasePool             An instance of ManualConfig to get necessary use cases
     * @param tradePresenter An instance of TradePresenter to display and get information from the user
     */
    public TradeController(UseCasePool useCasePool, TradePresenter tradePresenter) {
        this.tradePresenter = tradePresenter;
        permissionManager = useCasePool.getPermissionManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        tradeManager = useCasePool.getTradeManager();
        itemManager = useCasePool.getItemManager();
        freezingUtility = useCasePool.getFreezingUtility();
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

            // TODO how do we check if someone is frozen?
            if (!freezingUtility.isFrozen(sessionManager.getCurrAccountID())) {
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
        tradePresenter.displayTrades(tradeManager.getAllTradesAccountString(sessionManager.getCurrAccountID()));
    }

    private void selectAndChangeTrade() {
        while (true) {
            showTrades();
            String index = tradePresenter.selectTrade();
            if (inputHandler.isExitStr(index))
                return;
            if (inputHandler.isNum(index)) {
                int ind = Integer.parseInt(index);
                List<Integer> tradesID = tradeManager.getAllTradesAccountID(sessionManager.getCurrAccountID());
                List<String> tradesString = tradeManager.getAllTradesAccountString(sessionManager.getCurrAccountID());
                if (0 <= ind && ind < tradesID.size()) {
                    tradePresenter.displayTrade(tradesString.get(ind));
                    if (tradeManager.isRejected(tradesID.get(ind))) {
                        tradePresenter.displayCancelled();
                    } else if (tradeManager.isUnconfirmed(tradesID.get(ind))) {
                        changeUnconfirmedTrade(tradesID.get(ind));
                    } else if (tradeManager.isConfirmed(tradesID.get(ind))) {
                        changeConfirmedTrade(tradesID.get(ind));
                    } else if (tradeManager.isCompleted(tradesID.get(ind))) {
                        tradePresenter.displayCompleted();
                    }
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeConfirmedTrade(int tradeID) {
        tradePresenter.displayConfirmed();
        if (tradeManager.getDateTime(tradeID).isBefore(LocalDateTime.now())) {
            while (true) {
                String ans = tradePresenter.isTradeCompleted();
                if (inputHandler.isTrue(ans)) {
                    tradePresenter.displayCompleted();
                    tradeManager.updateCompletion(sessionManager.getCurrAccountID(), tradeID);
                    return;
                } else if (inputHandler.isFalse(ans)) {
                    tradePresenter.displayIncomplete();
                    return;
                } else
                    tradePresenter.invalidInput();
            }
        }
    }

    private void changeUnconfirmedTrade(int tradeID) {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Reject or cancel this trade");
            if (tradeManager.isEditTurn(sessionManager.getCurrAccountID(), tradeID)) {
                if (tradeManager.getDateTime(tradeID).isAfter(LocalDateTime.now())) {
                    options.add("Confirm the time and location for this trade");
                }
                if (tradeManager.canBeEdited(tradeID)) {
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
                    changeUnconfirmedTradeActions(tradeID, actionInd);
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeUnconfirmedTradeActions(int tradeID, int actionInd) {
        if (actionInd == 0) {
            tradeManager.updateStatus(tradeID, TradeStatus.REJECTED);
            tradePresenter.displayRejected();
        } else if (actionInd == 1 && tradeManager.getDateTime(tradeID).isAfter(LocalDateTime.now())) {
            tradeManager.updateStatus(tradeID, TradeStatus.CONFIRMED);
            tradeManager.makeTrade(tradeID);
            if (!tradeManager.isPermanent(tradeID)) {
                tradeManager.reverseTrade(tradeID);
                tradeManager.updateStatus(tradeID, TradeStatus.CONFIRMED);
                tradeManager.makeTrade(tradeID);
            }
            tradePresenter.displayConfirmed();
        } else {
            changeTradeTimePlace(tradeID);
            if (!tradeManager.canBeEdited(tradeID)) {
                tradeManager.updateStatus(tradeID, TradeStatus.REJECTED);
                tradePresenter.displayLimitReached();
                tradePresenter.displayCancelled();
            }
        }
    }

    private void changeTradeTimePlace(int tradeID) {
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
                tradeManager.editTimePlace(tradeID, dateTime, location);
                tradePresenter.displaySuggestion();
                return;
            }
            tradePresenter.displayFuture();
        }
    }

    private void recentTwoWayTrades() {
        List<String> items = new ArrayList<>();
        for (int itemID : tradeManager.getRecentTwoWay(sessionManager.getCurrAccountID())) {
            items.add(itemManager.getItemStringById(itemID));
        }
        tradePresenter.displayRecentTwoWayTrade(items);
    }

    private void recentOneWayTrades() {
        List<String> items = new ArrayList<>();
        for (int itemID : tradeManager.getRecentOneWay(sessionManager.getCurrAccountID())) {
            items.add(itemManager.getItemStringById(itemID));
        }
        tradePresenter.displayRecentOneWayTrade(items);
    }

    private void frequentPartners() {
        List<String> partners = new ArrayList<>();
        for (int id : tradeManager.getTopThreePartnersIds(sessionManager.getCurrAccountID())) {
            partners.add(accountRepository.getAccountStringFromID(id));
        }
        tradePresenter.displayFrequentPartners(partners);
    }
}
