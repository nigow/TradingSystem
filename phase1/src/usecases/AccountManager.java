package usecases;

import entities.Account;
import gateways.AccountGateway;

import java.util.ArrayList;

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
     * Constructs an instance of a new Account using username and password and stores it using accountGateway
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     * @param username Username of the new account
     * @param password Password of the new account
     */
    public AccountManager(AccountGateway accountGateway, String username, String password){
        this.accountGateway = accountGateway;
        currAccount = new Account(username, password, new ArrayList<>(), accountGateway.generateValidId());
        this.accountGateway.updateAccount(currAccount);
    }

    /**
     * Finds an existing account by username using accountGateway
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     * @param username Username of account to find
     */
    public AccountManager(AccountGateway accountGateway, String username){
        this.accountGateway = accountGateway;
        currAccount = this.accountGateway.findByUsername(username);
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
}
