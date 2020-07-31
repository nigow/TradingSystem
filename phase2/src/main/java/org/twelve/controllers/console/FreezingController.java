package org.twelve.controllers.console;
import org.twelve.controllers.InputHandler;
import org.twelve.presenters.console.FreezingPresenter;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;


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
     * An instance of StatusManager to get qualifying accounts.
     */
    private final StatusManager statusManager;

    /**
     * An instance of InputValidator to check if input is valid.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes constructor with necessary use cases and presenter.
     *
     * @param useCasePool       An instance of ManualConfig to get use cases
     * @param freezingPresenter An instance of FreezingPresenter to display information
     */
    public FreezingController(UseCasePool useCasePool, FreezingPresenter freezingPresenter) {
        this.freezingPresenter = freezingPresenter;
        statusManager = useCasePool.getStatusManager();

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
        List<Integer> accounts = statusManager.getAccountIDsToFreeze();
        List<String> usernames = statusManager.getUsernamesToFreeze();
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
                statusManager.freezeAccount(accounts.get(Integer.parseInt(chosenUser)));
                freezingPresenter.displaySuccessfulFreeze();
                return;
            }
        }
    }

    /**
     * Unfreezes an account that has requested to be unfrozen.
     */
    private void unfreeze() {
        List<Integer> accountIDs = statusManager.getAccountsToUnfreeze();
        List<String> usernames = statusManager.getUsernamesToUnfreeze();
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
                statusManager.unfreezeAccount(accountIDs.get(Integer.parseInt(chosenUser)));
                freezingPresenter.displaySuccessfulUnfreeze();
                return;
            }
        }
    }
}
