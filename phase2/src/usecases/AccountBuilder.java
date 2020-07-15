package usecases;

import entities.Account;
import entities.Permissions;
import gateways.AccountGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Factory for creating different types of accounts.
 *
 * @author Andrew
 */
public class AccountBuilder {

    /**
     * The account gateway dealing with storage of accounts.
     */
    private final AccountGateway accountGateway;

    /**
     * Creates an instance of AccountFactory and stores accountGateway.
     *
     * @param accountGateway Gateway used to interact with persistent storage of accounts
     */
    public AccountBuilder(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    /**
     * Creates a standard account with trading permissions.
     *
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Account that is created with trading permissions
     */
    public Account createStandardAccount(String username, String password) {
        List<Permissions> permsToAdd = new ArrayList<>(Arrays.asList(
                Permissions.LOGIN, Permissions.BORROW, Permissions.LEND,
                Permissions.ADD_TO_WISHLIST, Permissions.CREATE_ITEM, Permissions.BROWSE_INVENTORY));
        return new Account(username, password, permsToAdd, accountGateway.generateValidId());
    }

    /**
     * Creates an administrator account with trading permissions.
     *
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Account that is created with administrator permissions
     */
    public Account createAdminAccount(String username, String password) {
        Account account = createStandardAccount(username, password);
        account.addPermission(Permissions.ADD_ADMIN);
        account.addPermission(Permissions.CHANGE_RESTRICTIONS);
        account.addPermission(Permissions.FREEZE);
        account.addPermission(Permissions.UNFREEZE);
        account.addPermission(Permissions.CONFIRM_ITEM);
        return account;
    }

}
