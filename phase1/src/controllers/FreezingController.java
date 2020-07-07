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
 * controller for handling freezing
 *
 * @author Catherine
 */
public class FreezingController {

    private final FreezingPresenter freezingPresenter;

    private final FreezingUtility freezingUtility;

    private final AccountManager accountManager;

    private final AuthManager authManager;

    private final TradeUtility tradeUtility;

    private final ControllerInputValidator controllerInputValidator;

    public FreezingController(ManualConfig mc, FreezingPresenter freezingPresenter) {
        tradeUtility = mc.getTradeUtility();
        this.freezingPresenter = freezingPresenter;
        freezingUtility = mc.getFreezingUtility();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        controllerInputValidator = new ControllerInputValidator();
    }

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
                    return;
                case "1":
                    unfreeze();
                    return;
                case "2":
                    return;
                default:
                    freezingPresenter.invalidInput();
                    break;
            }
        }
    }

    private void freeze() {
        List<Account> accounts = freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility);
        List<String> usernames = freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility);
        freezingPresenter.displayPossibleFreeze(usernames);
        String chosenUser;
        while (true) {
            chosenUser = freezingPresenter.freeze();
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

    private void unfreeze() {
        List<Account> accounts = freezingUtility.getAccountsToUnfreeze(accountManager, authManager);
        List<String> usernames = freezingUtility.getUsernamesToUnfreeze(accountManager, authManager);
        freezingPresenter.displayPossibleUnfreeze(usernames);
        String chosenUser;
        while (true) {
            chosenUser = freezingPresenter.freeze();
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
