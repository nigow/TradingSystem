package controllers;

import entities.Account;
import entities.Trade;
import gateways.ManualConfig;
import presenters.ConsoleTradePresenter;
import presenters.TradePresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.TradeUtility;

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
    private final ControllerHelper helper;

    public TradeController(ManualConfig mc, TradePresenter tradePresenter) {
        this.tradePresenter = tradePresenter;
        authManager = mc.getAuthManager();
        accountManager = mc.getAccountManager();
        tradeUtility = mc.getTradeUtility();
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

    private void selectAndChangeTrade() {
        String index = tradePresenter.selectTrade();
        if (helper.isNum(index)) {
            int ind = Integer.parseInt(index);
            List<Trade> trades = tradeUtility.getAllTradesAccount();
            if (0 <= ind && ind < trades.size()) {
                changeTrade(trades.get(ind));
            } else if (ind == -1)
                return;
            else
                tradePresenter.invalidInput();
        } else
            tradePresenter.invalidInput();
    }

    private void changeTrade(Trade t) {
        List<String> options = new ArrayList<>();

        // TODO

        options.add("Cancel or reject this trade"); // if the trade is not completed
        options.add("Confirm the time and location of this trade"); // if the trade is unconfirmed and it's not this user's turn
        options.add("Confirm that this trade occurred"); // if the trade is confirmed
        options.add("Offer a new time and location for this trade"); // if it's this user's turn
        options.add("Reverse this trade"); // if it is a reversible trade
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
