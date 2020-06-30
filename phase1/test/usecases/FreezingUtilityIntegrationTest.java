package usecases;

import entities.Account;
import entities.Restrictions;
import gateways.InMemoryRestrictionsGateway;
import gateways.RestrictionsGateway;
import junit.framework.TestCase;


import java.util.List;

/**
 * An integration test to verify integration of FreezingUtility, RestrictionsGateway, and entities are successful.
 */
public class FreezingUtilityIntegrationTest extends TestCase{

    private FreezingUtility freezingUtility;
    private RestrictionsGateway restrictionsGateway;


    private FreezingUtility setUpFreezingUtility(){
        Restrictions currRestrictions = new Restrictions(1, 3, 5);
        restrictionsGateway = new InMemoryRestrictionsGateway(currRestrictions);
        freezingUtility = new FreezingUtility(restrictionsGateway);


        assertEquals(1, currRestrictions.getLendMoreThanBorrow());
        assertEquals(3, currRestrictions.getMaxIncompleteTrade());
        assertEquals(5, currRestrictions.getMaxWeeklyTrade());
        return freezingUtility;
    }


    public void testAccountInitialization() {
        freezingUtility = setUpFreezingUtility();
        assertNotNull(this.setUpFreezingUtility());
    }

    public void testChangingRestrictions(){
        freezingUtility = setUpFreezingUtility();

        freezingUtility.setLendMoreThanBorrow(3);
        try {
            freezingUtility.setMaxIncompleteTrade(-1);

        } catch (IllegalArgumentException e){
            try {
                freezingUtility.setMaxWeeklyTrade(-2);
            } catch (IllegalArgumentException ex){
                freezingUtility.setMaxIncompleteTrade(5);
                freezingUtility.setMaxWeeklyTrade(4);
                assertEquals(3, freezingUtility.getLendMoreThanBorrow());
                assertEquals(5, freezingUtility.getMaxIncompleteTrade());
                assertEquals(4, freezingUtility.getMaxWeeklyTrade());
            }
        }
    }

    public void testGetAccountsToUnfreeze(){
        freezingUtility = setUpFreezingUtility();
        AuthManagerIntegrationTest authManagerIntegrationTest = new AuthManagerIntegrationTest();
        AuthManager authManager = authManagerIntegrationTest.setUpAuthentication();
        AccountManager accountManager =  authManagerIntegrationTest.accountManager;
        List<Account> accountsToUnFreeze = freezingUtility.getAccountsToUnfreeze(accountManager, authManager);

        //WIP

    }


}
