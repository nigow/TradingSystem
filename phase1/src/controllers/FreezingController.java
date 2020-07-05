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
 * @author Catherine
 */
public class FreezingController {
    /**
     * an instance of ManualConfig
     */
    private ManualConfig mc;
    /**
     * an instance of FreezingPresenter to allow user to see options
     */
    private FreezingPresenter freezingPresenter;
    /**
     * an instance of FreezingUtility to get users that need to be frozen/unfrozen
     */
    private FreezingUtility freezingUtility;
    /**
     * an instance of AccountManager
     */
    private AccountManager accountManager;
    /**
     * an instance of authManager
     */
    private AuthManager authManager;
    /**
     * an instance of tradeUtility
     */
    private TradeUtility tradeUtility;

    /**
     * an instance of ControllerInputValidator to make sure input is valid
     */
    private ControllerInputValidator controllerInputValidator;

    /**
     * initializes a constructor for FreezingController
     * @param mc an instance of ManualConfig
     * @param freezingPresenter an instance of freezingPresenter
     */
    public FreezingController(ManualConfig mc, FreezingPresenter freezingPresenter) {
        this.mc = mc;
        tradeUtility = mc.getTradeUtility();
        this.freezingPresenter = freezingPresenter;
        freezingUtility = mc.getFreezingUtility();
        accountManager = mc.getAccountManager();
        authManager = mc.getAuthManager();
        controllerInputValidator =  new ControllerInputValidator();
    }

    /**
     * shows admin freezing related actions they can complete and directs them appropriately
     */
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
                //TODO return home
            }
            else {
                freezingPresenter.invalidInput();
                isValidInput = false;
            }
        }
    }

    /**
     * lets admin freeze appropriate users
     */
    protected void freeze() {
        List<Account> accounts = freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility);
        List<String> usernames = freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility);
        freezingPresenter.displayPossibleFreeze(usernames);
        String chosenUser;
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            chosenUser = freezingPresenter.freeze();
            if (chosenUser.equals("-1")) {
                run();
            }
            else if (!controllerInputValidator.isNum(chosenUser)) {
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

    /**
     * lets user unfreeze users that have requested to be frozen
     */
    protected void unfreeze() {
        List<Account> accounts = freezingUtility.getAccountsToUnfreeze(accountManager, authManager);
        List<String> usernames = freezingUtility.getUsernamesToUnfreeze(accountManager, authManager);
        freezingPresenter.displayPossibleUnfreeze(usernames);
        String chosenUser;
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            chosenUser = freezingPresenter.unfreeze();
            if (chosenUser.equals("-1")) {
                run();
            }
            else if (!controllerInputValidator.isNum(chosenUser)) {
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
