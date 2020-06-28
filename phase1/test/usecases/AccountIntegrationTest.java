package usecases;

import entities.Account;
import gateways.AccountGateway;
import gateways.InMemoryAccountGateway;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An integration test to verify integration of usecases, gateways, and entities is successful.
 * @author Michael
 */
public class AccountIntegrationTest extends TestCase {

    private AccountManager accountManager;
    private AccountGateway accountGateway;

    private AccountManager setUpAccount(){

        Account initial = new Account("admin", "1234", new ArrayList<>(), 0);
        HashMap<Integer, Account> h = new HashMap<>();
        h.put(0, initial);

        accountGateway = new InMemoryAccountGateway(h);

        accountManager = new AccountManager(accountGateway);
        assertTrue(accountManager.setCurrAccount(accountGateway.findById(0)));
        return accountManager;

    }
    /**
     * Verifies that it is possible to initialize an AccountManager, given
     * a valid path provided.
     */
    public void testAccountInitialization() {
        accountManager = setUpAccount();
        assertNotNull(this.setUpAccount());
    }

    /**
     * Verifies that updating an Account actually saves the result inside a file.
     */
    public void testModifying() {
        // Note that this item does not actually exist, this should not be relevant for the test.
        accountManager = setUpAccount();
        assertNotNull(accountManager);
        // Adding both item ids
        accountManager.addItemToWishlist(1234);
        accountManager.addItemToWishlist(1236);
        assertTrue(accountManager.getCurrAccount().getWishlist().contains(1234));
        assertFalse(accountManager.getCurrAccount().getWishlist().contains(1235));
        assertTrue(accountManager.getCurrAccount().getWishlist().contains(1236));
        accountManager.removeItemFromWishlist(1234);
        assertFalse(accountManager.getCurrAccount().getWishlist().contains(1234));
        accountManager.removeItemFromWishlist(1236);
        assertFalse(accountManager.getCurrAccount().getWishlist().contains(1236));
    }

    /**
     * Verifies that all accounts can be fetched appropriately.
     */
    public void testGetAllAccounts() {
        accountManager = setUpAccount();
        assertEquals(accountManager.getAccountsList().size(), 1);
        assertEquals(accountManager.getAccountsList().get(0).getUsername(), "admin");
        assertEquals(accountManager.getAccountsList().get(0).getAccountID(), 0);
        accountManager.createAccount("bob", "12345");
        assertEquals(accountManager.getAccountsList().size(), 2);
    }

    /**
     * Verifies that both getters of AccountManager return the same Account instance.
     */
    public void testAccountGetter() {
        accountManager = setUpAccount();
        assertEquals(accountManager.getAccountFromUsername("admin"), accountManager.getAccountFromID(0));
    }

    /**
     * Verifies that changing an account actually saves the results.
     */
    public void testChangingAccount() {
        accountManager = setUpAccount();
        Account second = new Account("second", "1678", new ArrayList<>(), 1);
        accountGateway.updateAccount(second);
        accountManager.setCurrAccount(second);
        assertEquals(accountManager.getAccountFromID(1), second);
        accountManager.addItemToWishlist(23);
        assertTrue(accountManager.getAccountFromID(1).getWishlist().contains(23));
    }


}
