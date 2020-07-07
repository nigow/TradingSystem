package presenters;

import entities.Account;
import entities.Item;

import java.util.List;

/**
 * Interface for lending services.
 *
 * @author Tairi
 */
public interface LendingPresenter {
    /**
     * Display all accounts available to trade with.
     */
    void displayAccounts(List<Account> allAccounts);

    /**
     * Display all tradable items that the current user possesses.
     */
    void displayInventory(List<Item> inventory);

    /**
     * Prompt user to select an account to trade with.
     *
     * @return index of account listed in displayAccounts
     */
    String selectAccount();

    /**
     * Prompt user to select an item to lend.
     *
     * @return index of item listed in displayInventory
     */
    String selectItem();

    /**
     * Send a message to the user.
     */
    void customMessage(String message);
}
