package usecases;

import entities.Account;
import gateways.AccountGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the manager that creates an account or takes in an account and edits the wishlist.
 * @author Andrew
 */
public class AccountManager {

    /**
     * The current account being edited
     */
    private Account currAccount;

    /**
     * The account gateway dealing with storage of accounts
     */
    private AccountGateway accountGateway;

    /**
     * Constructs an instance of AccountManager and stores accountGateway
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     */
    public AccountManager(AccountGateway accountGateway){
        this.accountGateway = accountGateway;
    }

    /**
     * Constructs an instance of AccountManager and stores accountGateway
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     * @param account Account that current account is being set to
     */
    public AccountManager(AccountGateway accountGateway, Account account){
        this.accountGateway = accountGateway;
        currAccount = account;
    }

    /**
     * Creates a new Account using username and password by determining if characters used are valid and stores it using accountGateway
     * @param username Username of the new account
     * @param password Password of the new account
     * @return true if account is successfully created and false if username is taken already or invalid characters where used
     */
    public boolean createAccount(String username, String password){
        if(!username.matches("^[a-zA-Z0-9_]*$") || !password.matches("^[ -~]*$") || password.contains(","))
            return false;
        if (accountGateway.findByUsername(username) == null){
            currAccount = new Account(username, password, new ArrayList<>(), accountGateway.generateValidId());
            this.accountGateway.updateAccount(currAccount);
            return true;
        }
        return false;
    }

    /**
     * Gets the account corresponding to the accountID provided
     * @param accountID Unique identifier of account
     * @return account corresponding to the accountID
     */
    public Account getAccountFromID(int accountID){
        return accountGateway.findById(accountID);
    }

    /**
     * Gets the account corresponding to the username provided
     * @param username Username of an account
     * @return account corresponding to the username
     */
    public Account getAccountFromUsername(String username){
        return accountGateway.findByUsername(username);
    }

    /**
     * Assigns he given account to currAccount
     * @param account Account of user being set
     * @return Whether the current account is successfully set or not
     */
    public boolean setCurrAccount(Account account){
        if (account != null){
            currAccount = account;
            return true;
        }
        return false;
    }

    /**
     * Gets the current Account being modified
     * @return the current account
     */
    public Account getCurrAccount(){
        return currAccount;
    }

    /**
     * Adds an itemID to the current account's wishlist
     * @param itemID Unique identifier of the item
     */
    public void addItemToWishlist(int itemID){
        if (currAccount != null){
            currAccount.addToWishlist(itemID);
            accountGateway.updateAccount(currAccount);
        }
    }

    /**
     * Removes an itemID from the current account's wishlist
     * @param itemID Unique identifier of the item
     */
    public void removeItemFromWishlist(int itemID){
        if (currAccount != null){
            currAccount.removeFromWishList(itemID);
            accountGateway.updateAccount(currAccount);
        }
    }

    /**
     * Updates in persistent storage the account corresponding to the account
     * @param account Account of user to update
     */
    public void updateAccount(Account account){
        accountGateway.updateAccount(account);
    }

    /**
     * Retrieves all accounts stored in the system
     * @return List of all accounts
     */
    public List<Account> getAccountsList(){
        return accountGateway.getAllAccounts();
    }

}
