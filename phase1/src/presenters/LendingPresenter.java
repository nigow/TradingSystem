package presenters;

import entities.Account;
import entities.Item;

import java.util.List;

/**
 * Presenter for lending services
 * @author Tairi
 */
public interface LendingPresenter {
    /**
     * Display all accounts available to trade with
     */
    void displayAccounts(List<Account> allAccounts);

    /**
     * Display all tradable items that the current user possesses
     */
    void displayInventory(List<Item> inventory);

    /**
     * Prompt user to select an account to trade with
     * @return index of account listed in displayAccounts
     */
    String selectAccount();

    /**
     * Prompt user to select an item to lend
     * @return index of item listed in displayInventory
     */
    String selectItem();

    /**
     * Let user know that the input is invalid
     */
    void invalidInput();

    /**
     * Let user know that the lending is cancelled
     */
    void abort();
}
