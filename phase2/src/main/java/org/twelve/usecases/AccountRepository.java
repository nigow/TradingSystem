package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;
import org.twelve.gateways.AccountGateway;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for storing all accounts in the system.
 */
public class AccountRepository {

    private Map<Integer, Account> accounts;
    private AccountGateway accountGateway;

    public AccountRepository(AccountGateway accountGateway){
        this.accountGateway = accountGateway;
        accounts = new HashMap<>();
        accountGateway.populate(this);
    }

    public void switchToDemoMode(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
        for (Account account : accounts.values()) {
            updateCreateAccount(account);
        }
    }

    public void switchToNormalMode(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
        accounts.clear();
        accountGateway.populate(this);
    }

    /**
     * Creates a new account using username and password if username is not already taken.
     *
     * @param username Username of the new account
     * @param password Password of the new account
     * @param perms List of permissions for the new account
     * @return Whether the new account is created successfully
     */
    public boolean createAccount(String username, String password, List<Permissions> perms, String location) {
        if (getAccountFromUsername(username) == null) {
            List<Permissions> permsToAdd = new ArrayList<>(perms);
            int accountID = accounts.size();
            Account newAccount = new Account(username, password, permsToAdd, accountID, location);
            accounts.put(accountID, newAccount);
            updateCreateAccount(newAccount);
            return true;
        }
        return false;
    }

    /**
     * Create an account instance for an account that already exists in the database.
     * @param accountId The account's id
     * @param username The account's username
     * @param password The account's password
     * @param perms The account's permissions
     * @param wishlist The account's wishlist
     * @param location The account's location.
     */
    public void createAccount(int accountId, String username, String password, List<String> perms,
                              List<Integer> wishlist, String location) {

        List<Permissions> permsToAdd = new ArrayList<>();
        for(String perm: perms) permsToAdd.add(Permissions.valueOf(perm));
        Account newAccount = new Account(username, password, wishlist, permsToAdd, accountId, location);
        accounts.put(newAccount.getAccountID(), newAccount);
    }
    //used by inMemoryAccountGateway
    // TODO: This needs to be removed.
    public void addAccount(Account account) {
        accounts.put(account.getAccountID(), account);
        updateCreateAccount(account);
    }

    /**
     * Gets the account corresponding to the accountID provided.
     *
     * @param accountID Unique identifier of account
     * @return Account corresponding to the accountID
     */
    Account getAccountFromID(int accountID) {
        return accounts.get(accountID);
    }

    /**
     * Gets the account corresponding to the username provided.
     *
     * @param username Username of an account
     * @return Account corresponding to the username
     */
    Account getAccountFromUsername(String username) {
        for(Account account: accounts.values()){
            if (account.getUsername().equals(username)){
                return account;
            }
        }
        return null;
    }

    /**
     * Gets the username of an account corresponding to the given unique identifier.
     *
     * @param accountID Unique identifier of account
     * @return Username corresponding to the unique identifier of an account
     */
    public String getUsernameFromID(int accountID) {
        return accounts.get(accountID).getUsername();
    }

    /**
     * Retrieves all accounts stored in the system.
     *
     * @return List of all accounts
     */
    public List<Account> getAccounts() {
        return (List<Account>) accounts.values();
    }

    /**
     * Retrieves the ids of all accounts in the system
     *
     * @return A list of account ids in the system.
     */
    public List<Integer> getAccountIDs(){
        return new ArrayList<>(accounts.keySet());
    }

    /**
     * @return List of all accounts as strings
     */
    public List<String> getAccountStrings(){
        List<String> accountStrins = new ArrayList<>();
        for (Account account : accounts.values()) {
            accountStrins.add(account.toString());
        }
        return accountStrins;
    }

    /**
     * Retrieves a formatted string of an account from the given accountID.
     *
     * @param accountID Unique identifier of account
     * @return Formatted String of account
     */
    public String getAccountStringFromID(int accountID) {
        return getAccountFromID(accountID).toString();
    }

    /**
     * Retrieves the ID of a given account.
     *
     * @param account Account to get from ID
     * @return ID associated with the account
     */
    public int getAccountID(Account account) {
        return account.getAccountID();
    }

    // TODO merge these two methods  --maryam

    /**
     * Save program changes with an account instance to an account gateway.
     * @param account An account instance that is being changed.
     */
    void updateToAccountGateway(Account account){
        List<String> permsAsStrings = new ArrayList<>();
        for (Permissions perms: account.getPermissions()){
            permsAsStrings.add(perms.name());
        }
        accountGateway.save(account.getAccountID(), account.getUsername(),
                account.getPassword(), account.getWishlist(), permsAsStrings, account.getLocation(), false);
    }

    /**
     * Update an account in accountGateway. Used for both new accounts/updated accounts.
     * @param account An account to update.
     */
    void updateCreateAccount(Account account){
        List<String> permsAsStrings = new ArrayList<>();
        for (Permissions perms: account.getPermissions()){
            permsAsStrings.add(perms.name());
        }
        accountGateway.save(account.getAccountID(), account.getUsername(),
                account.getPassword(), account.getWishlist(), permsAsStrings, account.getLocation(), true);
    }

    /**
     * Given an account username, return the account ids.
     * @param username The username of the account
     * @return An integer representing the account id.
     */
    public int getIDFromUsername(String username) {
        int userID = -1;
        for (Account account: accounts.values()) {
            if (username.equals(account.getUsername())) {
                userID = account.getAccountID();
            }
        }
        return userID;
    }
}
