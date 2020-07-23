package usecases;

import entities.Account;
import entities.Permissions;

import java.util.List;

public class AccountRepository {

    private List<Account> accounts;

    // TODO references but we'd need to override clone() method in Account to prevent and I don't think this will affect anything
    public AccountRepository(List<Account> accounts){
        this.accounts = accounts;
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
            Account newAccount = new Account(username, password, perms, accounts.size());
            accounts.add(newAccount);
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
    public Account getAccountFromID(int accountID) {
        for(Account account: accounts){
            if (account.getAccountID() == accountID){
                return account;
            }
        }
        return null;
    }

    /**
     * Gets the account corresponding to the username provided.
     *
     * @param username Username of an account
     * @return Account corresponding to the username
     */
    public Account getAccountFromUsername(String username) {
        for(Account account: accounts){
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
        for(Account account: accounts){
            if (account.getAccountID() == accountID){
                return account.getUsername();
            }
        }
        return null;
    }

    /**
     * Retrieves all accounts stored in the system.
     *
     * @return List of all accounts
     */
    public List<Account> getAccounts() {
        return accounts;
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

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void removeAccount(Account account){
        accounts.remove(account);
    }
}
