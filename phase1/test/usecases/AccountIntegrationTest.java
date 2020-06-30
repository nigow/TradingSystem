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
        assertTrue(accountManager.setCurrAccount("admin"));
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
        accountManager.createStandardAccount("bob", "12345");
        assertEquals(accountManager.getAccountsList().size(), 2);
    }


    /**
     * Verifies that invalid accounts aren't created and valid ones are
     */
    public void testCreatingValidAccount(){
        accountManager = setUpAccount();
        assertEquals(accountManager.getAccountsList().size(), 1);
        assertEquals(accountManager.getAccountsList().get(0).getUsername(), "admin");
        assertEquals(accountManager.getAccountsList().get(0).getAccountID(), 0);
        accountManager.createStandardAccount("みお", "12345");
        assertEquals(accountManager.getAccountsList().size(), 1);
        accountManager.createAdminAccount("😊", "12345");
        assertEquals(accountManager.getAccountsList().size(), 1);
        accountManager.createStandardAccount("BobtheCoder", "123,456");
        assertEquals(accountManager.getAccountsList().size(), 1);
        StringBuilder password = new StringBuilder();
        StringBuilder username = new StringBuilder();
        for(char c = 0; c < 128; c++) {
            Character ascii = c;
            if (ascii.toString().matches("^[a-zA-Z0-9_]*$")) {
                username.append(ascii);
            }
            if (ascii.toString().matches("^[ -~]*$") && !ascii.equals(',')){
                password.append(ascii);
            }
        }
        System.out.println(password.toString());
        accountManager.createAdminAccount(username.toString(), password.toString());
        assertEquals(accountManager.getAccountsList().size(), 2);

    }

    /**
     * Verifies that both getters of AccountManager return the same Account instance.
     */
    public void testAccountGetter() {
        accountManager = setUpAccount();
        assertEquals(accountManager.getCurrAccount(), accountManager.getAccountFromID(0));
    }

    /**
     * Verifies that changing an account actually saves the results.
     */
    public void testChangingAccount() {
        accountManager = setUpAccount();
        Account second = new Account("second", "1678", new ArrayList<>(), 1);
        accountGateway.updateAccount(second);
        accountManager.setCurrAccount("second");
        assertEquals(accountManager.getAccountFromID(1), second);
        accountManager.addItemToWishlist(23);
        assertTrue(accountManager.getAccountFromID(1).getWishlist().contains(23));
    }

}
