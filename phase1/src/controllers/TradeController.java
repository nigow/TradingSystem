package controllers;

import entities.Trade;
import entities.TradeStatus;
import gateways.ManualConfig;
import presenters.TradePresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.TradeManager;
import usecases.TradeUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// TODO: 1. variable and method javadoc 2. code >_<

/**
 * Controller that deals with user's past trades and making changes to them.
 * @author Maryam
 */
public class TradeController {
    private final TradePresenter tradePresenter;
    private final AuthManager authManager;
    private final AccountManager accountManager;
    private final TradeUtility tradeUtility;
    private final TradeManager tradeManager;
    private final ControllerHelper helper;

    public TradeController(ManualConfig mc, TradePresenter tradePresenter) {
        this.tradePresenter = tradePresenter;
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        tradeUtility = mc.getTradeUtility();
        tradeManager = mc.getTradeManager();
        helper = new ControllerHelper();
    }

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

            if (helper.isNum(action)) {
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
        tradePresenter.displayTrades(tradeUtility.getAllTradesAccountString()); // TODO: only show non-rejected trades
    }

    // TODO make helpers to make this less ugly
    private void selectAndChangeTrade() {
        String index = tradePresenter.selectTrade();
        if (helper.isNum(index)) {
            int ind = Integer.parseInt(index);
            List<Trade> trades = tradeUtility.getAllTradesAccount();
            if (0 <= ind && ind < trades.size()) {
                tradeManager.setTrade(trades.get(ind));
                // TODO set trade in trade manager
                if (tradeManager.isRejected()) {
                    tradePresenter.showMessage("You already rejected this trade.");
                } else if (tradeManager.isUnconfirmed()) {
                    List<String> options = new ArrayList<>();
                    options.add("Reject or cancel this trade");
                    if (tradeManager.isEditTurn(accountManager.getCurrAccount())) {
                        options.add("Confirm the time and location for this trade");
                        options.add("Edit the time and location for this trade");
                    }
                    String action = tradePresenter.displayTradeOptions(options);
                    if (helper.isNum(action)) {
                        int action_ind = Integer.parseInt(action);
                        if (0 <= action_ind && action_ind < options.size()) {
                            if (action_ind == 0) {
                                tradeManager.updateStatus(TradeStatus.REJECTED);
                            } else if (action_ind == 1) {
                                tradeManager.updateStatus(TradeStatus.CONFIRMED);
                            } else if (action_ind == 2) {
                                changeTrade();
                            }
                        } else
                            tradePresenter.invalidInput();
                    } else
                        tradePresenter.invalidInput();
                } else if (tradeManager.isConfirmed()) {
                    tradePresenter.showMessage("You have confirmed the meetup for this trade. " +
                            "Has this trade been completed?");
                    String ans = tradePresenter.yesOrNo();
                    if (ans.equals("y")) {
                        tradePresenter.showMessage("You have marked this trade as complete.");
                        // TODO call method to mark this trade as complete from this end
                    } else if (ans.equals("n"))
                        tradePresenter.showMessage("Okay.");
                    else
                        tradePresenter.invalidInput();

                } else if (tradeManager.isCompleted()) {
                    tradePresenter.showMessage("This trade has already been completed.");
                }
            } else if (ind == -1)
                return;
            else
                tradePresenter.invalidInput();
        } else
            tradePresenter.invalidInput();
    }

    private void changeTrade() {
        String[] newInfo = tradePresenter.editTradeTimePlace();
        if (helper.isDate(newInfo[1]) && helper.isTime(newInfo[2])) {
            tradeManager.editTimePlace(LocalDateTime.parse(newInfo[1] + " " + newInfo[2], DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm")),
                    newInfo[0], accountManager.getCurrAccountID());
        } else
            tradePresenter.invalidInput();
    }

    // TODO: only take completed trades into account for the next three methods
    private void recentTwoWayTrades() {
        List<String> trades = new ArrayList<>();
        for (Trade t : tradeUtility.getRecentTwoWay()) {
            trades.add(t.toString());
        }
        tradePresenter.displayRecentTwoWayTrade(trades);
    }

    private void recentOneWayTrades() {
        List<String> trades = new ArrayList<>();
        for (Trade t : tradeUtility.getRecentOneWay()) {
            trades.add(t.toString());
        }
        tradePresenter.displayRecentOneWayTrade(trades);
    }

    private void frequentPartners() {
        List<String> partners = new ArrayList<>();
        for (int id : tradeUtility.getTopThreePartnersIds()) {
            partners.add(accountManager.getAccountFromID(id).toString());
        }
        tradePresenter.displayFrequentPartners(partners);
    }
}
