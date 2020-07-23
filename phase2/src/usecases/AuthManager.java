package usecases;

import entities.Account;
import entities.Permissions;
import entities.Restrictions;
import gateways.AccountGateway;
import gateways.RestrictionsGateway;

import java.util.List;

/**
 * Manager responsible for authenticating logging in and authorizing actions.
 *
 * @author Andrew
 */
public class AuthManager {

    /**
     * The account gateway dealing with storage of accounts.
     */
    private final AccountGateway accountGateway;

    /**
     * The restrictions gateway dealing with the storage of trading restrictions.
     */
    private final RestrictionsGateway restrictionsGateway;

    /**
     * Constructs an instance of AuthManager and stores accountGateway, restrictionsGateway.
     *
     * @param accountGateway      Gateway used to interact with persistent storage of accounts
     * @param restrictionsGateway Gateway used to interact with persistent storage of restrictions
     */
    public AuthManager(AccountGateway accountGateway, RestrictionsGateway restrictionsGateway) {
        this.accountGateway = accountGateway;
        this.restrictionsGateway = restrictionsGateway;
    }

    /**
     * Authenticates a login session provided username and password.
     *
     * @param username Username of account that the user entered
     * @param password Password of account that the user entered
     * @return Whether the login is successful or not
     */
    public boolean authenticateLogin(String username, String password) {
        Account storedAccount = accountGateway.findByUsername(username);
        if (storedAccount == null) {
            return false;
        }
        return storedAccount.getPassword().equals(password);
    }

    /**
     * Adds a permission to the account by permissionID.
     *
     * @param account      Account to add the permission to
     * @param permissionID Unique identifier of permission
     */

    public void addPermissionByID(Account account, Permissions permissionID) {
        account.addPermission(permissionID);
    }

    /**
     * Adds a list of permissions to the account by permissionIDs.
     *
     * @param account       Account to add the list of permissions to
     * @param permissionIDs List of unique identifiers of permissions
     */
    public void addPermissionsByIDs(Account account, List<Permissions> permissionIDs) {
        for (Permissions permissionID : permissionIDs) {
            account.addPermission(permissionID);
        }
    }

    /**
     * Remove a permission from the account by permissionID.
     *
     * @param account      Account to remove the permission from
     * @param permissionID Unique identifier of permission
     */
    public void removePermissionByID(Account account, Permissions permissionID) {
        account.removePermission(permissionID);
    }

    /**
     * Removes a list of permissions from the account by permissionsIDs.
     *
     * @param account       Account to remove the list of permissions from
     * @param permissionIDs List of unique identifiers of permissions
     */
    public void removePermissionsByIDs(Account account, List<Permissions> permissionIDs) {
        for (Permissions permissionID : permissionIDs) {
            account.removePermission(permissionID);
        }
    }

//    /**
//     * Determines whether a given account account can login.
//     *
//     * @param account Account that is checked it see if it can login
//     * @return Whether the account can login or not
//     */
//    public boolean canLogin(Account account) {
//        return account.getPermissions().contains(Permissions.LOGIN);
//    }

    /**
     * Determines whether a given account can add to wishlist.
     *
     * @param account Account that is checked it see if it can add to wishlist
     * @return Whether the account can add to wishlist or not
     */
    public boolean canAddToWishlist(Account account) {
        return account.getPermissions().contains(Permissions.ADD_TO_WISHLIST);
    }

    /**
     * Determines whether a given account can create items.
     *
     * @param account Account that is checked it see if it can create items
     * @return Whether the account can create items or not
     */
    public boolean canCreateItem(Account account) {
        return account.getPermissions().contains(Permissions.CREATE_ITEM);
    }

    /**
     * Determines if account can browse the inventory.
     *
     * @param account Account that is checked it see if it can browse the inventory
     * @return Whether the account can browse the inventory
     */
    public boolean canBrowseInventory(Account account) {
        return account.getPermissions().contains(Permissions.BROWSE_INVENTORY);
    }

    /**
     * Determines whether a given account account can borrow items.
     *
     * @param account Account that is checked if it can borrow items
     * @return Whether the account can borrow items
     */
    private boolean canBorrow(Account account) {
        return account.getPermissions().contains(Permissions.BORROW);
    }

    /**
     * Determines whether a given account account can lend items.
     *
     * @param account Account that is checked if it can lend items
     * @return Whether the account can lend items
     */
    private boolean canLend(Account account) {
        return account.getPermissions().contains(Permissions.LEND);
    }

    /**
     * Determines whether a given account account can change trading restrictions.
     *
     * @param account Account that is checked it see if it can change restrictions
     * @return Whether the account can change restrictions
     */
    public boolean canChangeRestrictions(Account account) {
        return account.getPermissions().contains(Permissions.CHANGE_RESTRICTIONS);
    }

    /**
     * Determines whether a given account can add administrator accounts.
     *
     * @param account Account that is checked it see if it can add administrator accounts
     * @return Whether the account can add administrator accounts
     */
    public boolean canAddAdmin(Account account) {
        return account.getPermissions().contains(Permissions.ADD_ADMIN);
    }

    /**
     * Determines whether a given account account can confirm that an item can be added.
     *
     * @param account Account that is checked it see if it can confirm items
     * @return Whether the account can confirm items
     */
    public boolean canConfirmItem(Account account) {
        return account.getPermissions().contains(Permissions.CONFIRM_ITEM);
    }

    /**
     * Determines whether a given account account can freeze other accounts.
     *
     * @param account Account that is checked it see if it can freeze other accounts
     * @return Whether the account can freeze other accounts
     */
    public boolean canFreeze(Account account) {
        return account.getPermissions().contains(Permissions.FREEZE);
    }

    /**
     * Determines whether a given account can unfreeze other accounts.
     *
     * @param account Account that is checked it see if it can unfreeze other accounts
     * @return Whether the account can unfreeze other accounts
     */
    public boolean canUnfreeze(Account account) {
        return account.getPermissions().contains(Permissions.UNFREEZE);
    }

    /**
     * Determines whether a given account can request to be unfrozen.
     *
     * @param account Account that is checked it see if it can request to be unfrozen
     * @return Whether the account can request to be unfrozen
     */
    public boolean canRequestUnfreeze(Account account) {
        return account.getPermissions().contains(Permissions.REQUEST_UNFREEZE);
    }

    /**
     * Determines whether a given account is frozen.
     *
     * @param account Account that is checked if it is frozen
     * @return Whether the account is frozen or not
     */
    public boolean isFrozen(Account account) {
        return !canLend(account) && !canBorrow(account);
    }

    /**
     * Determines whether a given account has requested to be unfrozen.
     *
     * @param account Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(Account account) {
        return isFrozen(account) && !account.getPermissions().contains(Permissions.REQUEST_UNFREEZE);
    }

    /**
     * Determines whether a given account should be frozen.
     *
     * @param oldTradeUtility Utility for getting trade information
     * @param account      Account that is checked if it can be frozen
     * @param adminAccount The admin account that is freezing this account
     * @return Whether the account can be frozen or not
     */
    public boolean canBeFrozen(OldTradeUtility oldTradeUtility, Account account, Account adminAccount) {
        Restrictions restrictions = restrictionsGateway.getRestrictions();
        oldTradeUtility.setAccount(account);

        //TODO each boolean should be method within OldTradeUtility so AuthManager doesn't depend on restrictionsGateway
        boolean withinMaxIncompleteTrades = oldTradeUtility.getTimesIncomplete() <= restrictions.getMaxIncompleteTrade();
        boolean withinWeeklyLimit = oldTradeUtility.getNumWeeklyTrades() < restrictionsGateway.getRestrictions().getMaxWeeklyTrade();
        oldTradeUtility.setAccount(adminAccount);
        return !canUnfreeze(account) && !isFrozen(account) && (!withinMaxIncompleteTrades || !withinWeeklyLimit);
    }
    //TODO should be in OldTradeUtility
    /**
     * Determines whether the current account has lent more than borrowed.
     *
     * @param oldTradeUtility Utility for getting trade information
     * @return Whether the current account has lent more than borrowed
     */
    public boolean lentMoreThanBorrowed(OldTradeUtility oldTradeUtility) {
        return oldTradeUtility.getTimesLent() - oldTradeUtility.getTimesBorrowed() >=
                restrictionsGateway.getRestrictions().getLendMoreThanBorrow();
    }

    //TODO Negation of isFrozen() so useless method
    /**
     * Determines whether a given account can trade.
     *
     * @param account Account that is checked if it can be frozen
     * @return Whether account can trade or not
     */
    public boolean canTrade(Account account) {
        return !isFrozen(account);
    }

    /**
     * Determines whether a given account can request to unfreeze and requests to unfreeze if it can.
     *
     * @param account Account to request to be unfrozen
     * @return Whether the account can request to unfreeze or not
     */
    public boolean requestUnfreeze(Account account) {
        account.removePermission(Permissions.REQUEST_UNFREEZE);
        return accountGateway.updateAccount(account);
    }
}
