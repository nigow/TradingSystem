package org.twelve.controllers;
//import org.twelve.entities.Account;
import org.twelve.presenters.FreezingPresenter;
import org.twelve.usecases.FreezingUtility;
//import org.twelve.usecases.AccountRepository;
//import org.twelve.usecases.PermissionManager;
//import org.twelve.usecases.TradeManager;

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
//    /**
//     * An instance of AccountRepository.
//     */
//    private final AccountRepository accountRepository;
//    /**
//     * An instance of AuthManager.
//     */
//    private final PermissionManager permissionManager;
//    /**
//     * An instance of TradeManager.
//     */
//    private final TradeManager tradeManager;
    /**
     * An instance of ControllerInputValidator to check if input is valid.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes constructor with necessary use cases and presenter. #TODO: make sure all commented code can be removed, and if so, remove it.
     *
     * @param useCasePool       An instance of ManualConfig to get use cases
     * @param freezingPresenter An instance of FreezingPresenter to display information
     */
    public FreezingController(UseCasePool useCasePool, FreezingPresenter freezingPresenter) {
//        tradeManager = useCasePool.getTradeManager();
        this.freezingPresenter = freezingPresenter;
        freezingUtility = useCasePool.getFreezingUtility();
//        accountRepository = useCasePool.getAccountRepository();
//        permissionManager = useCasePool.getPermissionManager();
        inputHandler = new InputHandler();
    }

    /**
     * Shows actions that can be completed and redirects admin to method of requested action.
     */
    public void run() {
        List<String> freezingActions = new ArrayList<>();
        freezingActions.add(freezingPresenter.freezeUser());
        freezingActions.add(freezingPresenter.unfreezeUser());
        freezingActions.add(freezingPresenter.returnToHome());
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
        List<Integer> accounts = freezingUtility.getAccountIDsToFreeze();
        List<String> usernames = freezingUtility.getUsernamesToFreeze();
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
                freezingUtility.freezeAccount(accounts.get(Integer.parseInt(chosenUser)));
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