package org.twelve.controllers;

import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;


import java.util.List;

/**
 * Controller that allows user to freeze/unfreeze users
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 * @author Catherine
 */
public class FreezingController {
    /**
     * An instance of StatusManager to get qualifying accounts.
     */
    private final StatusManager statusManager;

    /**
     * An instance of InputValidator to check if input is valid.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes constructor with necessary use cases
     *
     * @param useCasePool       An instance of ManualConfig to get use cases
     */
    public FreezingController(UseCasePool useCasePool) {
        statusManager = useCasePool.getStatusManager();
        inputHandler = new InputHandler();
    }

    /**
     * Freezes an account that should be frozen. #TODO: when presenter is figured out, add presenter functionality
     */
    public void freeze(int chosenUser) { //Assume that the list we're given of accountIDs is the same given to the view/presenter
        List<Integer> accounts = statusManager.getAccountIDsToFreeze();
        statusManager.freezeAccount(accounts.get(chosenUser));
    }

    /**
     * Unfreeze an account that has requested to be unfrozen
     */
    public void unfreeze(int chosenUser) {
        List<Integer> accounts = statusManager.getAccountsToUnfreeze();
        statusManager.unfreezeAccount(accounts.get(chosenUser));
    }
}
