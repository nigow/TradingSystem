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

/**
 * controller that lets admin freeze and unfreeze users
 * @author Catherine
 */
public class FreezingController {
    /**
     * an instance of FreezingPresenter to display options
     */
    private final FreezingPresenter freezingPresenter;
    /**
     * an instance of FreezingUtility to get qualifying accounts
     */
    private final FreezingUtility freezingUtility;
    /**
     * an instance of AccountManager
     */
    private final AccountManager accountManager;
    /**
     * an instance of AuthManager
     */
    private final AuthManager authManager;
    /**
     * an instance of tradeUtility
     */
    private final TradeUtility tradeUtility;
    /**
     * an instance of ControllerInputValidator to check if input is valid
     */
    private final ControllerInputValidator controllerInputValidator;

    /**
     * initializes constructor with necessary use cases and presenter
     * @param mc an instance of ManualConfig to get use cases
     * @param freezingPresenter an instance of FreezingPresenter to display information
     */
    public FreezingController(ManualConfig mc, FreezingPresenter freezingPresenter) {
        tradeUtility = mc.getTradeUtility();
        this.freezingPresenter = freezingPresenter;
        freezingUtility = mc.getFreezingUtility();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        controllerInputValidator = new ControllerInputValidator();
    }

    /**
     * shows actions that can be completed and redirects admin to method of requested action
     */
    public void run() {
        List<String> freezingActions = new ArrayList<>();
        freezingActions.add("Freeze users");
        freezingActions.add("Unfreeze users");
        freezingActions.add("Return to home");
        while (true) {
            String action = freezingPresenter.displayFreezingOptions(freezingActions);
            switch (action) {
                case "0":
                    freeze();
                case "1":
                    unfreeze();
                case "2":
                    return;
                default:
                    freezingPresenter.invalidInput();
                    break;
            }
        }
    }

    /**
     * freezes an account that should be frozen
     */
    private void freeze() {
        List<Account> accounts = freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility);
        List<String> usernames = freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility);
        freezingPresenter.displayPossibleFreeze(usernames);
        while (true) {
            String chosenUser = freezingPresenter.freeze();
            if (controllerInputValidator.isExitStr(chosenUser))
                return;
            if (!controllerInputValidator.isNum(chosenUser))
                freezingPresenter.invalidInput();
            else if (Integer.parseInt(chosenUser) >= accounts.size())
                freezingPresenter.invalidInput();
            else {
                freezingUtility.freezeAccount(authManager, tradeUtility, accounts.get(Integer.parseInt(chosenUser)), accountManager.getCurrAccount());
                freezingPresenter.showMessage("You have frozen this account.");
                return;
            }
        }
    }

    /**
     * unfreezes an account that has requested to be unfrozen
     */
    private void unfreeze() {
        List<Account> accounts = freezingUtility.getAccountsToUnfreeze(accountManager, authManager);
        List<String> usernames = freezingUtility.getUsernamesToUnfreeze(accountManager, authManager);
        freezingPresenter.displayPossibleUnfreeze(usernames);
        while (true) {
            String chosenUser = freezingPresenter.unfreeze();
            if (controllerInputValidator.isExitStr(chosenUser))
                return;
            if (!controllerInputValidator.isNum(chosenUser))
                freezingPresenter.invalidInput();
            else if (Integer.parseInt(chosenUser) >= accounts.size())
                freezingPresenter.invalidInput();
            else {
                freezingUtility.unfreezeAccount(authManager, accounts.get(Integer.parseInt(chosenUser)));
                freezingPresenter.showMessage("You have unfrozen this account.");
                return;
            }
        }
    }
}
