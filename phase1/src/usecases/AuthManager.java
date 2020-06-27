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
     * The account currently logged in where its permissions are also managed
     */
    private Account currAccount;

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
        // WIP (Just showing that this method sets currAccount and needs to be called before other methods)

        currAccount = accountGateway.findByUsername(username);
        return true;
    }

    /**
     * Adds a role to current account by roleID
     * @param roleID Unique identifier of role
     */
    // Note to controller people: Default rolesIDs are BASIC and TRADER
    // Admin roleIDs are ADMIN, BASIC, TRADER
    public void addRolebyID(Roles roleID){
        currAccount.addRole(roleID);
        accountGateway.updateAccount(currAccount);
    }

    /**
     * Adds a list of roles to current account by roleIDs
     * @param roleIDs List of unique identifiers of roles
     */
    public void addRolesbyIDs(List<Roles> roleIDs){
        for (Roles roleID: roleIDs){
            currAccount.addRole(roleID);
        }
        accountGateway.updateAccount(currAccount);
    }

    /**
     * Remove a role from current account by roleID
     * @param roleID Unique identifier of role
     */
    public void removeRolebyID(Roles roleID){
        currAccount.removeRole(roleID);
        accountGateway.updateAccount(currAccount);
    }

    /**
     * Removes a list of roles from current account by roleIDs
     * @param roleIDs List of unique identifiers of roles
     */
    public void removeRolesbyIDs(List<Roles> roleIDs){
        for (Roles roleID: roleIDs){
            currAccount.removeRole(roleID);
        }
        accountGateway.updateAccount(currAccount);
    }

    /**
     * Determines whether the current account can borrow items
     * @return Whether the current account can borrow items
     */
    public boolean canBorrow(){
        //WIP
        return true;
    }

    /**
     * Determines whether the current account can lend items
     * @return Whether the current account can lend items
     */
    public boolean canLend(){
        // WIP
        return true;
    }

    /**
     * Determines whether the current account can perform administer actions
     * @return Whether the current account and perform administer actions
     */
    public boolean canAdminister(){
        //LET ME KNOW IF YOU THINK WE SHOULD LET EVERY INDIVIDUAL PERMISSION HAVE A canDoSomething() METHOD
        //CURRENTLY IT IS JUST FOR KEY ACTIONS THAT MAY INCLUDE 1 OR MORE PERMISSIONS
        return true;
    }

    /**
     * Checks if the current account has already requested to unfreeze or is not frozen
     * @return Whether an current account can request to unfreeze their account
     */
    public boolean canRequestUnfreeze(){
        // WIP
        return true;
    }

    /**
     * Determines whether the current account can request to unfreeze and changes the role of the account from FROZEN to PENDING if it can
     */
    public void requestUnfreeze(){
        //if (canRequestUnfreeze()) do something
            // WIP (gives PENDING Role)
    }
}
