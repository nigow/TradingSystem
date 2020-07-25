package usecases;

import entities.Account;
import entities.Permissions;
import gateways.experimental.AccountGateway;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountRepository {

    private Map<Integer, Account> accounts;
    private final AccountGateway accountGateway;

    public AccountRepository(AccountGateway accountGateway){
        this.accountGateway = accountGateway;
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
    public boolean createAccount(String username, String password, List<Permissions> perms) {
        if (getAccountFromUsername(username) == null) {
            //TODO remove referencing of list of perms
            int accountID = accounts.size();
            Account newAccount = new Account(username, password, perms, accountID);
            accounts.put(accountID, newAccount);
            //TODO accountGateway.save(username, password, newAccount.getWishlist(), perms, accountID);
            return true;
        }
        return false;
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

    public List<Integer> getAccountIDs(){
        return new ArrayList<>(accounts.keySet());
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

    public void removeAccount(int accountID){
        accounts.remove(accountID);
    }
}
