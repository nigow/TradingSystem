package controllers;

import entities.Account;
import presenters.FreezingPresenter;
import usecases.AccountManager;
import usecases.AuthManager;
import usecases.FreezingUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that lets admin freeze and unfreeze users.
 *
 * @author Catherine
 */
public class FreezingController {
    /**
     * An instance of FreezingPresenter to display options.
     */
    private final FreezingPresenter freezingPresenter;
    /**
     * An instance of FreezingUtility to get qualifying accounts.
     */
    private final FreezingUtility freezingUtility;
    /**
     * An instance of AccountManager.
     */
    private final AccountManager accountManager;
    /**
     * An instance of AuthManager.
     */
    private final AuthManager authManager;
    /**
     * An instance of oldTradeUtility.
     */
    private final OldTradeUtility oldTradeUtility;
    /**
     * An instance of ControllerInputValidator to check if input is valid.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes constructor with necessary use cases and presenter.
     *
     * @param useCasePool       An instance of ManualConfig to get use cases
     * @param freezingPresenter An instance of FreezingPresenter to display information
     */
    public FreezingController(UseCasePool useCasePool, FreezingPresenter freezingPresenter) {
        oldTradeUtility = useCasePool.getOldTradeUtility();
        this.freezingPresenter = freezingPresenter;
        freezingUtility = useCasePool.getFreezingUtility();
        accountManager = useCasePool.getAccountManager();
        authManager = useCasePool.getAuthManager();
        inputHandler = new InputHandler();
    }

    /**
     * Shows actions that can be completed and redirects admin to method of requested action.
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
                    break;
                case "1":
                    unfreeze();
                    break;
                case "2":
                    return;
                default:
                    freezingPresenter.invalidInput();
                    break;
            }
        }
    }

    /**
     * Freezes an account that should be frozen.
     */
    private void freeze() {
        // TODO: update to new TradeUtility
        List<Account> accounts = freezingUtility.getAccountIDsToFreeze(oldTradeUtility);
        List<String> usernames = freezingUtility.getUsernamesToFreeze(oldTradeUtility);
        freezingPresenter.displayPossibleFreeze(usernames);
        while (true) {
            String chosenUser = freezingPresenter.freeze();
            if (inputHandler.isExitStr(chosenUser))
                return;
            if (!inputHandler.isNum(chosenUser))
                freezingPresenter.invalidInput();
            else if (Integer.parseInt(chosenUser) >= accounts.size())
                freezingPresenter.invalidInput();
            else {
                freezingUtility.freezeAccount(oldTradeUtility, accounts.get(Integer.parseInt(chosenUser)));
                freezingPresenter.displaySuccessfulFreeze();
                return;
            }
        }
    }

    /**
     * Unfreezes an account that has requested to be unfrozen.
     */
    private void unfreeze() {
        List<Integer> accountIDs = freezingUtility.getAccountsToUnfreeze();
        List<String> usernames = freezingUtility.getUsernamesToUnfreeze();
        freezingPresenter.displayPossibleUnfreeze(usernames);
        while (true) {
            String chosenUser = freezingPresenter.unfreeze();
            if (inputHandler.isExitStr(chosenUser))
                return;
            if (!inputHandler.isNum(chosenUser))
                freezingPresenter.invalidInput();
            else if (Integer.parseInt(chosenUser) >= accountIDs.size())
                freezingPresenter.invalidInput();
            else {
                freezingUtility.unfreezeAccount(accountIDs.get(Integer.parseInt(chosenUser)));
                freezingPresenter.displaySuccessfulUnfreeze();
                return;
            }
        }
    }
}
