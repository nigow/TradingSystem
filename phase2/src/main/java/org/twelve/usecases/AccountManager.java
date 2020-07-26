package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;
import org.twelve.gateways.AccountGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO to be removed
/**
 * Manager that creates an account or takes in an account and edits the wishlist.
 *
 * @author Andrew
 */
public class AccountManager {

    /**
     * The current account being edited.
     */
    private Account currAccount;

    /**
     * The account gateway dealing with storage of accounts.
     */
    private final AccountGateway accountGateway;

    /**
     * Constructs an instance of AccountManager and stores accountGateway.
     *
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     */
    public AccountManager(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    /**
     * Creates a new standard trading account using username and password by determining if
     * characters used are valid.
     *
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Whether standard account is created successfully
     */
    public boolean createStandardAccount(String username, String password) {
        if (accountGateway.findByUsername(username) == null) {
            AccountBuilder accountBuilder = new AccountBuilder();
            accountBuilder.buildCredentials(username, password);
            List<Permissions> perms = new ArrayList<>(Arrays.asList(Permissions.LOGIN,
                    Permissions.BORROW, Permissions.LEND, Permissions.BROWSE_INVENTORY,
                    Permissions.ADD_TO_WISHLIST, Permissions.CREATE_ITEM));
            accountBuilder.buildPermissions(perms);
            accountBuilder.buildID(accountGateway.generateValidId());
            Account createdAccount = accountBuilder.buildAccount();
            if (accountGateway.updateAccount(createdAccount)) {
                currAccount = createdAccount;
                return true;
            }
            return false;
        }
        return false;
    }

    // TODO createCustomAccount or createXXXAccount?
    /**
     * Creates a new administrator account with permissions to trade using username and password by determining if characters used are valid.
     *
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Whether admin account is created successfully
     */
    public boolean createAdminAccount(String username, String password) {
        if (accountGateway.findByUsername(username) == null) {
            AccountBuilder accountBuilder = new AccountBuilder();
            accountBuilder.buildCredentials(username, password);
            // TODO this is a jumbled mess for perms. Could have helpers for presets.
            List<Permissions> perms = new ArrayList<>(Arrays.asList(Permissions.LOGIN,
                    Permissions.BORROW, Permissions.LEND, Permissions.BROWSE_INVENTORY,
                    Permissions.ADD_TO_WISHLIST, Permissions.CREATE_ITEM, Permissions.ADD_ADMIN,
                    Permissions.CHANGE_RESTRICTIONS, Permissions.FREEZE, Permissions.UNFREEZE,
                    Permissions.CONFIRM_ITEM));
            accountBuilder.buildPermissions(perms);
            accountBuilder.buildID(accountGateway.generateValidId());
            Account createdAccount = accountBuilder.buildAccount();
            if (accountGateway.updateAccount(createdAccount)) {
                currAccount = createdAccount;
                return true;
            }
            return false;
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
        return accountGateway.findById(accountID);
    }

    /**
     * Gets the account corresponding to the username provided.
     *
     * @param username Username of an account
     * @return Account corresponding to the username
     */
    private Account getAccountFromUsername(String username) {
        return accountGateway.findByUsername(username);
    }

    /**
     * Gets the username of an account corresponding to the given unique identifier.
     *
     * @param accountID Unique identifier of account
     * @return Username corresponding to the unique identifier of an account
     */
    public String getUsernameFromID(int accountID) {
        return accountGateway.findById(accountID).getUsername();
    }

    /**
     * Assigns the given username corresponding to an account to currAccount.
     *
     * @param username Username of Account being set
     * @return Whether the current account is successfully set or not
     */
    public boolean setCurrAccount(String username) {
        Account account = getAccountFromUsername(username);
        if (account != null) {
            currAccount = account;
            return true;
        }
        return false;
    }

    /**
     * Gets the current account being modified.
     *
     * @return The current account
     */
    public Account getCurrAccount() {
        return currAccount;
    }

    /**
     * Gets the current account's unique identifier.
     *
     * @return The current account ID
     */
    public int getCurrAccountID() {
        return currAccount.getAccountID();
    }

    /**
     * Gets the current account's username.
     *
     * @return The current account username
     */
    public String getCurrAccountUsername() {
        return currAccount.getUsername();
    }

    /**
     * Adds an itemID to the current account's wishlist.
     *
     * @param itemID Unique identifier of the item
     */
    public void addItemToWishlist(int itemID) {
        currAccount.addToWishlist(itemID);
    }

    /**
     * Removes an itemID from the current account's wishlist.
     *
     * @param itemID Unique identifier of the item
     */
    public void removeItemFromWishlist(int itemID) {
        currAccount.removeFromWishList(itemID);
    }

    /**
     * Gets the wishlist of item ids for the current account.
     *
     * @return Wishlist of the current account
     */
    public List<Integer> getCurrWishlist() {
        return currAccount.getWishlist();
    }

    /**
     * Determines if item corresponding to the itemID is in the current account's wishlist.
     *
     * @param itemID Unique identifier of the item
     * @return Whether the item corresponding to the itemID is in the current account's wishlist
     */
    public boolean isInWishlist(int itemID) {
        return currAccount.getWishlist().contains(itemID);
    }

    //TODO either this is used or removed(controllers can just call it from gateway)
    /**
     * Updates in persistent storage the account corresponding to the account.
     *
     * @param account Account of user to update
     * @return Whether account is successfully updated or not
     */
    public boolean updateAccount(Account account) {
        return accountGateway.updateAccount(account);
    }

    /**
     * Retrieves all accounts stored in the system.
     *
     * @return List of all accounts
     */
    public List<Account> getAccountsList() {
        return accountGateway.getAllAccounts();
    }


    /**
     * Retrieves a formatted string of an account from the given accountID.
     *
     * @param accountID Unique identifier of account
     * @return Formatted String of account
     */
    public String getAccountStringFromID(int accountID) {
        return accountGateway.findById(accountID).toString();
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

}
