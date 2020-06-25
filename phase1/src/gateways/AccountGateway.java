package gateways;

import entities.Account;

import java.util.List;

/**
 * A gateway for interacting with the persistent storage of accounts.
 */
public interface AccountGateway {

    /**
     * Given an account's ID, return the corresponding Account object.
     * @param id ID of desired account.
     * @return Account possessing the given ID (null if an invalid ID was given).
     */
    Account findById(int id);

    /**
     * Given an account's username, return the corresponding Account object.
     * @param username Username of desired account.
     * @return Account possessing the given username (null if an invalid username was given).
     */
    Account findByUsername(String username);

    /**
     * Given an account, save its information to persistent storage.
     * @param account Account being saved.
     */
    void updateAccount(Account account);

    /**
     * Retrieve every account in the system.
     * @return List of every account in the system.
     */
    List<Account> getAllAccounts();

    /**
     * Return an ID that does not belong to any account at the time the method is called.
     * @return An unused ID.
     */
    int generateValidId();

}
