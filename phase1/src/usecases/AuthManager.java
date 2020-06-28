package usecases;

import entities.Account;
import entities.Permissions;
import gateways.AccountGateway;
import gateways.RestrictionsGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the manager responsible for authenticating logging in and authorizing actions
 * @author Andrew
 */
public class AuthManager {

    /**
     * The account gateway dealing with storage of accounts
     */
    private AccountGateway accountGateway;

    /**
     * The restrictions gateway dealing with the storage of trading restrictions
     */
    private RestrictionsGateway restrictionsGateway;

    /**
     * Constructs an instance of AccountManager and stores accountGateway, restrictionsGateway and roleGateway
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     * @param restrictionsGateway Gateway used to interact with persistent storage of restrictions
     */
    public AuthManager(AccountGateway accountGateway, RestrictionsGateway restrictionsGateway){
        this.accountGateway = accountGateway;
        this.restrictionsGateway = restrictionsGateway;
    }

    /**
     * Authenticates a login session provided username and password
     * @param username Username of account that the user entered
     * @param password Password of account that the user entered
     * @return Whether the login is successful or not
     */
    public boolean authenticateLogin(String username, String password){
        Account storedAccount = accountGateway.findByUsername(username);
        if(storedAccount != null && storedAccount.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    /**
     * Adds a permission to the account by permissionID
     * @param account Account to add the permission to
     * @param permissionID Unique identifier of permission
     */

    public void addPermissionByID(Account account, Permissions permissionID){
        account.addPermission(permissionID);
        accountGateway.updateAccount(account);
    }

    /**
     * Adds a list of permissions to the account by permissionIDs
     * @param account Account to add the list of permissions to
     * @param permissionIDs List of unique identifiers of permissions
     */
    public void addPermissionsByIDs(Account account, List<Permissions> permissionIDs){
        for (Permissions permissionID: permissionIDs){
            account.addPermission(permissionID);
        }
        accountGateway.updateAccount(account);
    }

    /**
     * Remove a permission from the account by permissionID
     * @param account Account to remove the permission from
     * @param permissionID Unique identifier of permission
     */
    public void removePermissionByID(Account account, Permissions permissionID){
        account.removePermission(permissionID);
        accountGateway.updateAccount(account);
    }

    /**
     * Removes a list of permissions from the account by permissionsIDs
     * @param account Account to remove the list of permissions from
     * @param permissionIDs List of unique identifiers of permissions
     */
    public void removePermissionsByIDs(Account account, List<Permissions> permissionIDs){
        for (Permissions permissionID: permissionIDs){
            account.removePermission(permissionID);
        }
        accountGateway.updateAccount(account);
    }

    /**
     * Determines whether a given account account can borrow items
     * @param account Account that is checked if it can borrow items
     * @return Whether the account can borrow items
     */
    public boolean canBorrow(Account account){
        return account.getPermissions().contains(Permissions.BORROW);
    }

    /**
     * Determines whether a given account account can lend items
     * @param account Account that is checked if it can lend items
     * @return Whether the account can lend items
     */
    public boolean canLend(Account account){
        return account.getPermissions().contains(Permissions.LEND);
    }

    /**
     * Determines whether a given account is an administer account
     * @param account Account that is checked if it has admin permissions
     * @return Whether the account is an admin or not
     */
    // Would be better to check for each individual permission, but I'm waiting to see how we want to deal with Roles
    public boolean isAdmin(Account account){
        List<Permissions> adminPerms = new ArrayList<>(Arrays.asList(
                Permissions.ADD_ADMIN, Permissions.CHANGE_RESTRICTIONS,
                Permissions.CONFIRM_ITEM, Permissions.FREEZE, Permissions.UNFREEZE));
        return account.getPermissions().containsAll(adminPerms);
    }

    /**
     * Determines whether a given account is frozen
     * @param account Account that is checked if it is frozen
     * @return Whether the account is frozen or not
     */
    public boolean isFrozen(Account account){
        return !account.getPermissions().contains(Permissions.BORROW);
    }

    /**
     * Determines whether a given account has requested to be unfrozen
     * @param account Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(Account account){
        return !account.getPermissions().contains(Permissions.BORROW) && !account.getPermissions().contains(Permissions.REQUEST_UNFREEZE);
    }

    /**
     * Determines whether a given account should be frozen
     * @param account Account that is checked if it can be frozen
     * @return Whether the account can be frozen or not
     */
    public boolean canbeFrozen(Account account){
        if (isAdmin(account)){
            //Need TradeUtility for this part
            return false;
        }
        return true;
    }

    /**
     * Determines whether a given account can request to unfreeze and requests to unfreeze if it can
     * @param account Account to request to be unfrozen
     * @return Whether the account can request to unfreeze or not
     */
    public boolean requestUnfreeze(Account account){
        if (isFrozen(account) && !isPending(account)){
            account.removePermission(Permissions.REQUEST_UNFREEZE);
            accountGateway.updateAccount(account);
            return true;
        }
        return false;
    }
}
