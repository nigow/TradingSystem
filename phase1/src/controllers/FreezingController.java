package controllers;

import entities.Account;
import gateways.ManualConfig;
import presenters.ConsoleFreezingPresenter;
import presenters.FreezingPresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.FreezingUtility;
import usecases.TradeUtility;

import java.util.ArrayList;
import java.util.List;

public class FreezingController {

    private ManualConfig mc;

    private FreezingPresenter freezingPresenter;

    private FreezingUtility freezingUtility;

    private AccountManager accountManager;

    private AuthManager authManager;

    private TradeUtility tradeUtility;

    public FreezingController(ManualConfig mc, TradeUtility tradeUtility) {
        this.mc = mc;
        freezingPresenter = new ConsoleFreezingPresenter();
        freezingUtility = mc.getFreezingUtility();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        this.tradeUtility = tradeUtility;
    }

    public void run() {
        List<String> freezingActions = new ArrayList<>();
        freezingActions.add("Freeze users");
        freezingActions.add("Unfreeze users");
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            String action = freezingPresenter.displayFreezingOptions(freezingActions);
            if (action == "0") {
                freeze();
            }
            else if (action == "1") {
                unfreeze();
            }
            else {
                freezingPresenter.invalidInput();
                isValidInput = false;
            }
        }
    }

    public void freeze() {
        List<Account> accounts = freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility);
        List<String> usernames = freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility);
        freezingPresenter.displayPossibleFreeze(usernames);
        String chosenUsers;
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            chosenUsers = freezingPresenter.freeze();
            String[] listOfChosenUsers = chosenUsers.split(",");
            for (String i: listOfChosenUsers) {
                try{
                    freezingUtility.freezeAccount(authManager, tradeUtility, accounts.get(Integer.valueOf(i)));
                }
                catch (NumberFormatException E) {
                    isValidInput = false;
                }
                catch (IndexOutOfBoundsException F) {
                    isValidInput = false;
                }
            }
        }
    }

    public void unfreeze() {

    }
}
