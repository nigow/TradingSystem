package controllers;

import entities.Trade;
import entities.TradeStatus;
import gateways.ManualConfig;
import presenters.TradePresenter;
import usecases.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with user's past trades and making changes to them.
 * @author Maryam
 */
public class TradeController {

    // TODO: javadoc, but since it's private it's not a priority

    private final TradePresenter tradePresenter;

    private final AuthManager authManager;

    private final AccountManager accountManager;

    private final TradeUtility tradeUtility;

    private final TradeManager tradeManager;

    private final ItemManager itemManager;

    private final ItemUtility itemUtility;

    private final ControllerInputValidator controllerInputValidator;

    /**
     * Initialized TradeController by setting necessary usecases and presenter.
     * @param mc An instance of ManualConfig to get necessary usecases
     * @param tradePresenter An instance of TradePresenter to display and get information from the user
     */
    public TradeController(ManualConfig mc, TradePresenter tradePresenter) {
        this.tradePresenter = tradePresenter;
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        tradeUtility = mc.getTradeUtility();
        tradeManager = mc.getTradeManager();
        itemManager = mc.getItemManager();
        itemUtility = mc.getItemUtility();
        controllerInputValidator = new ControllerInputValidator();
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

            if (controllerInputValidator.isNum(action)) {
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
            if (controllerInputValidator.isExitStr(index))
                return;
            if (controllerInputValidator.isNum(index)) {
                int ind = Integer.parseInt(index);
                List<Trade> trades = tradeUtility.getAllTradesAccount();
                if (0 <= ind && ind < trades.size()) {
                    tradeManager.setTrade(trades.get(ind));
                    tradePresenter.showMessage(tradeManager.tradeAsString(accountManager, itemManager));
                    if (tradeManager.isRejected()) {
                        tradePresenter.showMessage("This trade has been cancelled.");
                    } else if (tradeManager.isUnconfirmed()) {
                        changeUnconfirmedTrade();
                    } else if (tradeManager.isConfirmed()) {
                        changeConfirmedTrade();
                    } else if (tradeManager.isCompleted()) {
                        tradePresenter.showMessage("This trade has already been completed.");
                    }
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeConfirmedTrade() {
        tradePresenter.showMessage("You have confirmed the meetup for this trade. " +
                "Has this trade been completed?");
        String ans = tradePresenter.yesOrNo();
        if (ans.equals("y")) {
            tradePresenter.showMessage("You have marked this trade as complete.");
            tradeManager.updateCompletion(accountManager.getCurrAccountID());
        } else if (ans.equals("n"))
            tradePresenter.showMessage("Okay.");
        else
            tradePresenter.invalidInput();
    }

    private void changeUnconfirmedTrade() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Reject or cancel this trade");
            if (tradeManager.isEditTurn(accountManager.getCurrAccount())) {
                if (tradeManager.getDateTime().isAfter(LocalDateTime.now()))
                    options.add("Confirm the time and location for this trade");
                options.add("Edit the time and location for this trade");
            }
            options.add("Go back");
            String action = tradePresenter.displayTradeOptions(options);
            if (controllerInputValidator.isNum(action)) {
                int actionInd = Integer.parseInt(action);
                if (actionInd == options.size() - 1)
                    return;
                if (0 <= actionInd && actionInd < options.size()) {
                    if (actionInd == 0) {
                        tradeManager.updateStatus(TradeStatus.REJECTED);
                    } else if (actionInd == 1) {
                        tradeManager.updateStatus(TradeStatus.CONFIRMED);
                        tradeUtility.makeTrade(tradeManager.getTrade(), accountManager, itemManager, itemUtility);
                        if (!tradeManager.isPermanent()) {
                            tradeManager.reverseTrade();
                        }
                    } else if (actionInd == 2) {
                        changeTradeTimePlace();
                    }
                    return;
                }
            }
            tradePresenter.invalidInput();
        }
    }

    private void changeTradeTimePlace() {
        String location = tradePresenter.editTradeLocation();
        if (controllerInputValidator.isExitStr(location))
            return;

        while(!controllerInputValidator.isValidCSVStr(location)) {
            tradePresenter.invalidInput();
            location = tradePresenter.editTradeLocation();
            if (controllerInputValidator.isExitStr(location))
                return;
        }

        while (true) {
            String date = tradePresenter.editTradeDate();
            if (controllerInputValidator.isExitStr(date))
                return;

            while (!controllerInputValidator.isValidCSVStr(date) || !controllerInputValidator.isDate(date)) {
                tradePresenter.invalidInput();
                date = tradePresenter.editTradeDate();
                if (controllerInputValidator.isExitStr(date))
                    return;
            }

            String time = tradePresenter.editTradeTime();
            if (controllerInputValidator.isExitStr(time))
                return;

            while (!controllerInputValidator.isValidCSVStr(time) || !controllerInputValidator.isTime(time)) {
                tradePresenter.invalidInput();
                time = tradePresenter.editTradeTime();
                if (controllerInputValidator.isExitStr(time))
                    return;
            }

            LocalDateTime dateTime =
                    LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (dateTime.isAfter(LocalDateTime.now())) {
                tradeManager.editTimePlace(dateTime, location, accountManager.getCurrAccountID());
                tradePresenter.showMessage("You have suggested a new meetup");
                return;
            }
            tradePresenter.showMessage("You need to choose a date and time in the future.");
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
