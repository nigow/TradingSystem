package usecases;

import entities.Account;
import entities.Restrictions;
import entities.Role;
import entities.Roles;
import gateways.AccountGateway;
import gateways.RestrictionsGateway;
import gateways.RoleGateway;

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
     * The role gateway dealing with the storage of roles
     */
    private RoleGateway roleGateway;

    /**
     * Constructs an instance of AccountManager and stores accountGateway, restrictionsGateway and roleGateway
     * @param restrictionsGateway Gateway used to interact with persistent storage of restrictions
     * @param roleGateway Gateway used to interact with persistent storage of roles
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     */
    public AuthManager(AccountGateway accountGateway, RestrictionsGateway restrictionsGateway, RoleGateway roleGateway){
        this.accountGateway = accountGateway;
        this.restrictionsGateway = restrictionsGateway;
        this.roleGateway = roleGateway;
    }

    /**
     * Authenticates a login session provided username and password
     * @param username Username of account that the user entered
     * @param password Password of account that the user entered
     * @return Whether the login is successful or not
     */
    public boolean authenticateLogin(String username, String password){
        // WIP
        return true;
    }

    /**
     * Adds a role to the account by roleID
     * @param account Account to add the role to
     * @param roleID Unique identifier of role
     */
    // Note to controller people: Default rolesIDs are BASIC and TRADER
    // Admin roleIDs are ADMIN, BASIC, TRADER
    public void addRolebyID(Account account, Roles roleID){
        account.addRole(roleID);
        accountGateway.updateAccount(account);
    }

    /**
     * Adds a list of roles to the account by roleIDs
     * @param account Account to add the list of roles to
     * @param roleIDs List of unique identifiers of roles
     */
    public void addRolesbyIDs(Account account, List<Roles> roleIDs){
        for (Roles roleID: roleIDs){
            account.addRole(roleID);
        }
        accountGateway.updateAccount(account);
    }

    /**
     * Remove a role from the account by roleID
     * @param account Account to remove the role from
     * @param roleID Unique identifier of role
     */
    public void removeRolebyID(Account account, Roles roleID){
        account.removeRole(roleID);
        accountGateway.updateAccount(account);
    }

    /**
     * Removes a list of roles from the account by roleIDs
     * @param account Account to remove the list of roles from
     * @param roleIDs List of unique identifiers of roles
     */
    public void removeRolesbyIDs(Account account, List<Roles> roleIDs){
        for (Roles roleID: roleIDs){
            account.removeRole(roleID);
        }
        accountGateway.updateAccount(account);
    }

    /**
     * Determines whether a given account account can borrow items
     * @param account Account that is checked if it can borrow items
     * @return Whether the account can borrow items
     */
    public boolean canBorrow(Account account){
        //WIP
        return true;
    }

    /**
     * Determines whether a given account account can lend items
     * @param account Account that is checked if it can lend items
     * @return Whether the account can lend items
     */
    public boolean canLend(Account account){
        // WIP
        return true;
    }

    /**
     * Determines whether a given account is an administer account
     * @param account Account that is checked if it has admin permissions
     * @return Whether the account is an admin or not
     */
    public boolean isAdmin(Account account){
        return true;
    }

    /**
     * Determines whether a given account is frozen
     * @param account Account that is checked if it is frozen
     * @return Whether the account is frozen or not
     */
    public boolean isFrozen(Account account){
        return true;
    }

    /**
     * Determines whether a given account has requested to be unfrozen
     * @param account Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(Account account){
        return true;
    }

    /**
     * Determines whether a given account should be frozen
     * @param account Account that is checked if it can be frozen
     * @return Whether the account can be frozen or not
     */
    public boolean canbeFrozen(Account account){
        return true;
    }

    /**
     * Checks if a given account has already requested to unfreeze or is not frozen
     * @param account Account that is checked if it can request to be unfrozen
     * @return Whether the account can request to unfreeze their account
     */
    public boolean canRequestUnfreeze(Account account){
        // Might remove this method/make it private and have it be used in requestUnfreeze()
        return true;
    }

    /**
     * Determines whether the current account can request to unfreeze and changes the role of the account from FROZEN to PENDING if it can
     * @param account Account to request unfreeze
     */
    public void requestUnfreeze(Account account){
        //if (canRequestUnfreeze()) do something
            // WIP (gives PENDING Role)
    }
}
