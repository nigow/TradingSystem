package usecases;

import entities.*;
import gateways.InMemoryRestrictionsGateway;
import gateways.InMemoryTradeGateway;
import gateways.RestrictionsGateway;
import gateways.TradeGateway;
import junit.framework.TestCase;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
        freezingUtility.setMaxIncompleteTrade(5);
        freezingUtility.setMaxWeeklyTrade(4);
        assertEquals(3, freezingUtility.getLendMoreThanBorrow());
        assertEquals(5, freezingUtility.getMaxIncompleteTrade());
        assertEquals(4, freezingUtility.getMaxWeeklyTrade());
    }

    public void testFreezingAccounts(){
        freezingUtility = setUpFreezingUtility();
        AuthManagerIntegrationTest authManagerIntegrationTest = new AuthManagerIntegrationTest();
        AuthManager authManager = authManagerIntegrationTest.setUpAuthentication();
        AccountManager accountManager =  authManagerIntegrationTest.accountManager;

        Account account1 = accountManager.getCurrAccount();
        Account account = accountManager.getAccountsList().get(0); // can't be frozen account

        Item item1 = new Item(0, "anime figure", "neat figure", 0);
        Item item2 = new Item(1, "japanese artwork appreciation pillow", "fluffy and cool", 1);
        Trade trade = new Trade(0, 0, true, 0, 1, new ArrayList<>(Arrays.asList(item1.getItemID())) ,new ArrayList<>(Arrays.asList(item2.getItemID())), 3);
        trade.setStatus(TradeStatus.REJECTED);
        TimePlace timePlace = new TimePlace(0, LocalDateTime.of(2020, 7, 1, 0, 0), "UTM");

        HashMap<Integer, Trade> tradeHashMap = new HashMap<>();
        tradeHashMap.put(0, trade);
        HashMap<Integer, TimePlace> timePlaceHashMap = new HashMap<>();
        timePlaceHashMap.put(0, timePlace);

        TradeGateway tradeGateway = new InMemoryTradeGateway(tradeHashMap, timePlaceHashMap);
        TradeManager tradeManager = new TradeManager(tradeGateway);
        TradeUtility tradeUtility = new TradeUtility(tradeManager);
        tradeUtility.setAccount(account1);

        //Methods are heavily interrelated so they are clumped together (the tests are very specific as well, which led to this clump of a method)

        assertEquals(freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility).size(), 0); //checks that no account should be frozen
        assertEquals(freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility).size(), 0); //checks that no username should be frozen

        Item item3 = new Item(2, "anime poster", "drawn by Mashiro Shiina", 0);
//        tradeManager.createTrade(LocalDateTime.of(2020, 7, 2, 0, 0), "Bahen",
//                false, 0, 1, new ArrayList<>(Arrays.asList(item3.getItemID())),
//                new ArrayList<>(), null);
//
//        assertEquals(freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility).get(0).getAccountID(), 1); //checks that only 1 account should be frozen
//        assertEquals(freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility).get(0), "Kento_Hinode");
//
//        tradeManager.updateStatus(TradeStatus.REJECTED);
//        account.removePermission(Permissions.UNFREEZE);
//        assertEquals(freezingUtility.getAccountsToFreeze(accountManager, authManager, tradeUtility).size(), 2); //checks that both accounts should be frozen
//        assertEquals(freezingUtility.getUsernamesToFreeze(accountManager, authManager, tradeUtility).size(), 2); //checks that both usernames should be frozen
//        assertEquals(freezingUtility.getAccountsToUnfreeze(accountManager, authManager).size(), 0); //checks that no account should be unfrozen since no accounts requested
//        assertEquals(freezingUtility.getUsernamesToUnfreeze(accountManager, authManager).size(), 0); //checks that no username should be unfrozen since no accounts requested
//
//        assertTrue(freezingUtility.freezeAccount(authManager, tradeUtility, account, null)); //checks that account can be frozen and is frozen
//        assertTrue(authManager.isFrozen(account)); //makes sure account is actually frozen
//        assertFalse(freezingUtility.freezeAccount(authManager, tradeUtility, account, null)); //checks that a frozen account isn't frozen
//
//        assertTrue(authManager.requestUnfreeze(account));
//
//        assertFalse(freezingUtility.freezeAccount(authManager, tradeUtility, account, null)); //checks that a pending account isnt frozen
//        assertEquals(freezingUtility.getAccountsToUnfreeze(accountManager, authManager).get(0).getAccountID(), 0); //checks that one account should be unfrozen
//        assertEquals(freezingUtility.getUsernamesToUnfreeze(accountManager, authManager).get(0), "Miyo_Sasaki"); //checks that one username should be unfrozen
//
//        assertTrue(freezingUtility.unfreezeAccount(authManager, account)); //checks that account is successfully unfrozen
//        assertFalse(freezingUtility.unfreezeAccount(authManager, account)); //checks that an unfrozen account can't be frozen
//        assertFalse(freezingUtility.unfreezeAccount(authManager, account1)); //checks that a non-pending account can't be frozen
//        assertFalse(authManager.isFrozen(account)); //makes sure account isn't frozen
//
//        //checks that no accounts and usernames should be unfrozen
//        assertEquals(freezingUtility.getAccountsToUnfreeze(accountManager, authManager).size(), 0);
//        assertEquals(freezingUtility.getUsernamesToUnfreeze(accountManager, authManager).size(), 0);
    }


}
