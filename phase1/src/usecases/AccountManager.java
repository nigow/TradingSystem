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
     * The account being edited
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
     * Creates a new Account using username and password and stores it using accountGateway
     * @param username Username of account to find
     * @param password Password of the new account
     * @return true if account is successfully created and false if username is taken already
     */
    public boolean createAccount(String username, String password){
        if (this.accountGateway.findByUsername(username).getUsername() == null){
            currAccount = new Account(username, password, new ArrayList<>(), accountGateway.generateValidId());
            this.accountGateway.updateAccount(currAccount);
            return true;
        }
        return false;
    }

    /**
     * Finds the account with the username and assigns it to currAccount
     * @param username Unique identifier of the account
     * @return true if currAccount is successfully set and false if no account is found with username
     */
    public boolean setCurrAccount(String username){
        Account account = this.accountGateway.findByUsername(username);
        if (account != null){
            currAccount = account;
            return true;
        }
        return false;
    }

    /**
     * Finds the account with the accountID and assigns it to currAccount
     * @param accountID Unique identifier of the account
     * @return true if currAccount is successfully set and false if no account is found with accountID
     */
    public boolean setCurrAccount(int accountID){
        Account account = this.accountGateway.findById(accountID);
        if (account != null){
            currAccount = account;
            return true;
        }
        return false;
    }

    /**
     * Adds an itemID to the current account's wishlist
     * @param itemID Unique identifier of the item
     */
    public void addItemtoWishlist(int itemID){
        currAccount.addToWishlist(itemID);
        accountGateway.updateAccount(currAccount);
    }

    /**
     * Removes an itemID from the current account's wishlist
     * @param itemID Unique identifier of the item
     */
    public void removeItemfromWishlist(int itemID){
        currAccount.removeFromWishList(itemID);
        accountGateway.updateAccount(currAccount);
    }

    /** Gets the current Account being modified
     * @return the current account
     */
    public Account getCurrAccount(){
        return currAccount;
    }

    /**
     * Updates in persistent storage the account corresponding to the username
     * @param username Username of the account to update
     */
    public void updateAccount(String username){
        accountGateway.updateAccount(accountGateway.findByUsername(username));
    }

    /**
     * Updates in persistent storage the account corresponding to the accountID
     * @param accountID Unique identifier of the account
     */
    public void updateAccount(int accountID){
        accountGateway.updateAccount(accountGateway.findById(accountID));
    }

    /**
     * Retrieves all accounts stored in the system
     * @return List of all accounts
     */
    public List<Account> getAccountsList(){
        return accountGateway.getAllAccounts();
    }
}
