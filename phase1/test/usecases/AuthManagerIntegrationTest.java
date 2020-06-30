package usecases;

import entities.Account;
import entities.Permissions;
import entities.Restrictions;
import gateways.AccountGateway;
import gateways.InMemoryAccountGateway;
import gateways.InMemoryRestrictionsGateway;
import gateways.RestrictionsGateway;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AuthManagerIntegrationTest extends TestCase {

    private AccountGateway accountGateway;
    protected AccountManager accountManager;
    private RestrictionsGateway restrictionsGateway;
    protected AuthManager authManager;

    protected AuthManager setUpAuthentication(){
        Account initial = new Account("Miyo_Sasaki", "I love you Hinode", new ArrayList<>(), 0);
        HashMap<Integer, Account> h = new HashMap<>();
        h.put(0, initial);

        accountGateway = new InMemoryAccountGateway(h);

        accountManager = new AccountManager(accountGateway);

        accountManager.createAccount("Kento_Hinode", "Sorry but I love Emilia");
        accountManager.updateAccount(accountManager.getCurrAccount());
        assertEquals(accountManager.getAccountsList().size(), 2);

        Restrictions currRestrictions = new Restrictions(1, 3, 5);
        restrictionsGateway = new InMemoryRestrictionsGateway(currRestrictions);

        authManager = new AuthManager(accountGateway, restrictionsGateway);
        return authManager;
    }

    public void testAccountInitialization() {
        authManager = setUpAuthentication();
        assertNotNull(this.setUpAuthentication());
    }

    public void testAuthenticateLogin(){
        authManager = setUpAuthentication();
        assertFalse(authManager.authenticateLogin("Miyo_Sasaki", "I love you Hinode jk"));
        assertTrue(authManager.authenticateLogin("Miyo_Sasaki", "I love you Hinode"));
        assertFalse(authManager.authenticateLogin("Miyo_Sasak", "I love you Hinode"));
    }

    public void testChangingPermissions(){
        authManager = setUpAuthentication();
        Account account1 = accountManager.getCurrAccount();
        authManager.addPermissionByID(account1, Permissions.LOGIN);
        assertEquals(Arrays.asList(Permissions.LOGIN), authManager.getPermissions(account1));

        authManager.addPermissionsByIDs(account1, Arrays.asList(
                Permissions.BORROW, Permissions.LEND, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST));
        assertEquals(account1.getPermissions(), authManager.getPermissions(account1));

        authManager.removePermissionByID(account1, Permissions.BORROW);
        assertEquals(account1.getPermissions(), authManager.getPermissions(account1));

        authManager.removePermissionsByIDs(account1, (Arrays.asList(
                Permissions.LEND, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST)));
        assertEquals(Arrays.asList(Permissions.LOGIN), authManager.getPermissions(account1));
    }



}
