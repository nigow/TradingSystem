package org.twelve.presenters;

import org.twelve.entities.Account;
import org.twelve.entities.Item;

import java.util.List;

/**
 * Interface for lending services.
 *
 * @author Tairi
 */
public interface LendingPresenter {
    /**
     * Display all accounts available to trade with.
     *
     * @param allAccounts List of accounts to display
     */
    void displayAccounts(List<String> allAccounts);

    /**
     * Display all tradable items that the current user possesses.
     *
     * @param inventory List of items to display
     */
    void displayInventory(List<Item> inventory);

    /**
     * Prompt user to select an account to trade with.
     *
     * @return Index of account listed in displayAccounts
     */
    String selectAccount();

    /**
     * Prompt user to select an item to lend.
     *
     * @return Index of item listed in displayInventory
     */
    String selectItem();

    /**
     * Tells user that their input was invalid.
     */
    void displayInvalidInput();

    /**
     * Tells user that it has been cancelled.
     */
    void displaySuccessfulInput();
}
