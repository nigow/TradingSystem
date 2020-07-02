package usecases;

import entities.Account;
import gateways.AccountGateway;

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
     * Creates a new standard trading account using username and password by determining if characters used are valid
     * @param username Username of the new account
     * @param password Password of the new account
     * @return true if account is successfully created and false if username is taken already or invalid characters where used
     */
    public boolean createStandardAccount(String username, String password){
        if (validateAccountRegister(username, password)){
            AccountFactory accountFactory = new AccountFactory(accountGateway);
            Account createdAccount = accountFactory.createStandardAccount(username, password);
            if (accountGateway.updateAccount(createdAccount)){
                currAccount = createdAccount;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Creates a new administrator account with permissions to trade using username and password by determining if characters used are valid
     * @param username Username of the new account
     * @param password Password of the new account
     * @return true if account is successfully created and false if username is taken already or invalid characters where used
     */
    public boolean createAdminAccount(String username, String password){
        if (validateAccountRegister(username, password)){
            AccountFactory accountFactory = new AccountFactory(accountGateway);
            Account createdAccount = accountFactory.createAdminAccount(username, password);
            if (accountGateway.updateAccount(createdAccount)){
                currAccount = createdAccount;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Determines if an account can be created with the given username and password
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Whether an account can be created
     */
    private boolean validateAccountRegister(String username, String password) {
        if(username.matches("^[a-zA-Z0-9_]*$") && password.matches("^[ -~]*$")
                && !password.contains(",") && accountGateway.findByUsername(username) == null){
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
    private Account getAccountFromUsername(String username){
        return accountGateway.findByUsername(username);
    }

    /**
     * Assigns the given username corresponding to an account to currAccount
     * @param username Username of Account being set
     * @return Whether the current account is successfully set or not
     */
    public boolean setCurrAccount(String username){
        Account account = getAccountFromUsername(username);
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
     * @return Whether an item is successfully added to wishlist or not
     */
    public boolean addItemToWishlist(int itemID){
        if (currAccount != null){
            currAccount.addToWishlist(itemID);
            return accountGateway.updateAccount(currAccount);
        }
        return false;
    }

    /**
     * Removes an itemID from the current account's wishlist
     * @param itemID Unique identifier of the item
     * @return Whether an item is successfully removed from wishlist or not
     */
    public boolean removeItemFromWishlist(int itemID){
        if (currAccount != null){
            currAccount.removeFromWishList(itemID);
            return accountGateway.updateAccount(currAccount);
        }
        return false;
    }

    /**
     * Updates in persistent storage the account corresponding to the account
     * @param account Account of user to update
     * @return Whether account is successfully updated or not
     */
    public boolean updateAccount(Account account){
        return accountGateway.updateAccount(account);
    }

    /**
     * Retrieves all accounts stored in the system
     * @return List of all accounts
     */
    public List<Account> getAccountsList(){
        return accountGateway.getAllAccounts();
    }

}
