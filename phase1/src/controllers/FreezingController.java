package controllers;

import entities.Account;
import gateways.ManualConfig;
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

    private ControllerHelper controllerHelper;

    public FreezingController(ManualConfig mc, FreezingPresenter freezingPresenter) {
        this.mc = mc;
        tradeUtility = mc.getTradeUtility();
        this.freezingPresenter = freezingPresenter;
        freezingUtility = mc.getFreezingUtility();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        controllerHelper =  new ControllerHelper();
    }

    public void run() {
        List<String> freezingActions = new ArrayList<>();
        freezingActions.add("Freeze users");
        freezingActions.add("Unfreeze users");
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            String action = freezingPresenter.displayFreezingOptions(freezingActions);
            if (action.equals("0")) {
                freeze();
            }
            else if (action.equals("1")) {
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
                if (!controllerHelper.isNum(i)) {
                    isValidInput = false;
                }
                else if (Integer.parseInt(i) >= accounts.size()) {
                    isValidInput = false;
                }
                else {
                    freezingUtility.freezeAccount(authManager, tradeUtility, accounts.get(Integer.parseInt(i)));
                }
            }
            if (!isValidInput) {
                freezingPresenter.invalidInput();
            }
        }
    }

    public void unfreeze() {
        List<Account> accounts = freezingUtility.getAccountsToUnfreeze(accountManager, authManager);
        List<String> usernames = freezingUtility.getUsernamesToUnfreeze(accountManager, authManager);
        freezingPresenter.displayPossibleUnfreeze(usernames);
        String chosenUsers;
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            chosenUsers = freezingPresenter.unfreeze();
            String[] listOfChosenUsers = chosenUsers.split(",");
            for (String i: listOfChosenUsers) {
                if (!controllerHelper.isNum(i)) {
                    isValidInput = false;
                }
                else if (Integer.parseInt(i) >= accounts.size()) {
                    isValidInput = false;
                }
                else {
                    freezingUtility.unfreezeAccount(authManager, accounts.get(Integer.parseInt(i)));
                }
            }
            if (!isValidInput) {
                freezingPresenter.invalidInput();
            }
        }
    }
}
