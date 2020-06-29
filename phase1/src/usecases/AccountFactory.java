package usecases;

import entities.Account;
import entities.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A factory for creating different types of accounts
 * @author Andrew
 */
public class AccountFactory {

    /**
     * Creates a standard account with trading permissions
     * @param accountManager Manager for permissions and authorizing actions
     * @param authManager Manager for permissions and authorizing actions
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Account if successfully created or null if unsuccessful
     */
    public Account createStandardAccount(AccountManager accountManager, AuthManager authManager, String username, String password) {
        if (accountManager.createAccount(username, password)) {
            Account currAccount = accountManager.getCurrAccount();
            List<Permissions> permsToAdd = new ArrayList<>(Arrays.asList(
                    Permissions.LOGIN, Permissions.BORROW, Permissions.LEND,
                    Permissions.ADD_TO_WISHLIST, Permissions.CREATE_ITEM));
            authManager.addPermissionsByIDs(currAccount, permsToAdd);
            return currAccount;
        }
        return null;
    }

    /**
     * Creates an administrator account with trading permissions
     * @param accountManager Manager for permissions and authorizing actions
     * @param authManager Manager for permissions and authorizing actions
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Account if successfully created or null if unsuccessful
     */
    public Account createAdminAccount(AccountManager accountManager, AuthManager authManager, String username, String password){
        Account currAccount = createStandardAccount(accountManager, authManager, username, password);
        if (currAccount != null){
            List<Permissions> permsToAdd = new ArrayList<>(Arrays.asList(
                    Permissions.ADD_ADMIN, Permissions.CHANGE_RESTRICTIONS, Permissions.FREEZE,
                    Permissions.UNFREEZE, Permissions.CONFIRM_ITEM));
            authManager.addPermissionsByIDs(currAccount, permsToAdd);
        }
        return currAccount;
    }
}
