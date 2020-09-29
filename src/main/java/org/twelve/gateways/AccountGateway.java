package org.twelve.gateways;

import org.twelve.usecases.account.AccountRepository;

import java.util.List;

/**
 * A gateway for Accounts that interacts with external storage.
 */
public interface AccountGateway {

    /**
     * Method that syncs the external storage information into the in-memory account repository.
     *
     * @param accountRepository an account repository to store
     * @return whether or not the population was successful
     */
    boolean populate(AccountRepository accountRepository);

    /**
     * Method that syncs a local account's update to the external storage
     *
     * @param accountId   account id of the account
     * @param username    username of the account
     * @param password    password of the account
     * @param wishlist    wishlist of the account
     * @param permissions permissions of the account
     * @param location    location of the account
     * @param newAccount  true if it is a newly created account. false if it is meant to update an existing account
     * @return whether or not the sync was successful
     */
    boolean save(int accountId, String username, String password, List<Integer> wishlist, List<String> permissions,
                 String location, boolean newAccount);
}
