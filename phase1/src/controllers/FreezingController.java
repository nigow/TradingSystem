package controllers;

import com.sun.xml.internal.bind.v2.TODO;
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
        freezingActions.add("Return to home");
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
            else if (action.equals("2")) {
                //TODO return to home
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
        String chosenUser;
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            chosenUser = freezingPresenter.freeze();
            if (!controllerHelper.isNum(chosenUser)) {
                isValidInput = false;
                freezingPresenter.invalidInput();
            }
            else if (Integer.parseInt(chosenUser) >= accounts.size()) {
                isValidInput = false;
                freezingPresenter.invalidInput();
            }
            else {
                freezingUtility.freezeAccount(authManager, tradeUtility, accounts.get(Integer.parseInt(chosenUser)));
            }
        }
    }

    public void unfreeze() {
        List<Account> accounts = freezingUtility.getAccountsToUnfreeze(accountManager, authManager);
        List<String> usernames = freezingUtility.getUsernamesToUnfreeze(accountManager, authManager);
        freezingPresenter.displayPossibleUnfreeze(usernames);
        String chosenUser;
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            chosenUser = freezingPresenter.unfreeze();
            if (!controllerHelper.isNum(chosenUser)) {
                isValidInput = false;
                freezingPresenter.invalidInput();
            }
            else if (Integer.parseInt(chosenUser) >= accounts.size()) {
                isValidInput = false;
                freezingPresenter.invalidInput();
            }
            else {
                freezingUtility.unfreezeAccount(authManager, accounts.get(Integer.parseInt(chosenUser)));
            }
        }
    }
}
