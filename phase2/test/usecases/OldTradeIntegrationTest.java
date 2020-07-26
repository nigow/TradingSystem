package usecases;

import entities.*;
import gateways.AccountGateway;
import gateways.InMemoryAccountGateway;
import gateways.InMemoryTradeGateway;
import gateways.TradeGateway;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An integration test to verify integration of usecases, gateways, and entities is successful.
 * @author Isaac
 */
public class OldTradeIntegrationTest extends TestCase {
    private OldTradeManager oldTradeManager;
    private OldTradeUtility oldTradeUtility;
    private TradeGateway tradeGateway;
    private AccountManager accountManager;

    private OldTradeManager setUpTradeManager() {
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(100);
        items2.add(200);
        OldTrade initial = new OldTrade(0, 0, false, 10, 11,
                items1, items2, 0);
        HashMap<Integer, OldTrade> h = new HashMap<>();
        h.put(0, initial);

        LocalDateTime time = LocalDateTime.now();
        TimePlace first = new TimePlace(0, time, "UTM");
        HashMap<Integer, TimePlace> hash = new HashMap<>();
        hash.put(0, first);
        tradeGateway = new InMemoryTradeGateway(h, hash);
        oldTradeManager = new OldTradeManager(tradeGateway);
        oldTradeManager.setOldTrade(initial);
        return oldTradeManager;
    }

    private OldTradeUtility setUpTradeUtility() {
        oldTradeManager = setUpTradeManager();
        oldTradeUtility = new OldTradeUtility(oldTradeManager);
        return oldTradeUtility;
    }

    private AccountManager setUpAccount(){

        Account initial = new Account("admin", "1234", new ArrayList<>(), 0);
        HashMap<Integer, Account> h = new HashMap<>();
        h.put(0, initial);


        AccountGateway accountGateway = new InMemoryAccountGateway(h);

        accountManager = new AccountManager(accountGateway);
        for (int i = 0; i < 100; i++) {
            accountManager.createStandardAccount("User" + i, "Password");
        }
        assertTrue(accountManager.setCurrAccount("admin"));
        return accountManager;

    }

    /**
     * Verifies that it is possible to initialize OldTradeManager and OldTradeUtility, given
     * a valid path provided.
     */
    public void testTradeInitialization() {
        oldTradeManager = setUpTradeManager();
        oldTradeUtility = setUpTradeUtility();
        assertNotNull(this.oldTradeManager);
        assertNotNull(this.oldTradeUtility);
    }

    /**
     * Verifies that when the trade is set the new trade being set is saved
     */
    public void testChangingTrade() {
        oldTradeManager = setUpTradeManager();
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        OldTrade oldTrade2 = new OldTrade(1, 1, false, 12, 13,
                items1, items2, 0);
        LocalDateTime time = LocalDateTime.now();
        TimePlace timePlace2 = new TimePlace(1, time, "UTM");
        tradeGateway.updateTrade(oldTrade2, timePlace2);
        oldTradeManager.setOldTrade(oldTrade2);
        assertEquals(oldTradeManager.getOldTrade(), oldTrade2);
        assertEquals(oldTradeManager.getTimePlace(), timePlace2);
    }

    /**
     * Verifies trades are properly created
     */
    public void testCreatingTrades() {
        oldTradeManager = setUpTradeManager();
        assertEquals(oldTradeManager.getAllTrades().size(), 1);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        oldTradeManager.createTrade(time, "UTM", false, 12, 13,
                items1, items2, accountManager);
        assertEquals(oldTradeManager.getAllTrades().size(), 2);
        assertEquals(oldTradeManager.getAllTrades().get(1).getTraderOneID(), 12);
        assertEquals(oldTradeManager.getAllTrades().get(1).getItemOneIDs(), items1);
        oldTradeManager.reverseTrade(accountManager);
        assertEquals(oldTradeManager.getAllTrades().size(), 3);
        assertEquals(oldTradeManager.getAllTrades().get(2).getTraderOneID(), 12);
        assertEquals(oldTradeManager.getAllTrades().get(2).getItemOneIDs(), items2);
        assertTrue(oldTradeManager.getAllTrades().get(2).isPermanent());
    }

    /**
     * Verifies trade's timePlace can be properly updated
     */
    public void testEditTimePlaceTrade() {
        oldTradeManager = setUpTradeManager();
        LocalDateTime newTime = LocalDateTime.of(2000, Month.JANUARY, 1,19, 30);
        oldTradeManager.editTimePlace(newTime, "Home", 11);
        assertEquals(oldTradeManager.getTimePlace().getTime(), newTime);
        assertEquals(oldTradeManager.getTimePlace().getPlace(), "Home");
        assertEquals(oldTradeManager.getOldTrade().getLastEditorID(), 11);
    }

    /**
     * Verifies trade's status can be properly updated
     */
    public void testUpdateStatus() {
        oldTradeManager = setUpTradeManager();
        assertTrue(oldTradeManager.isUnconfirmed());
        TradeStatus tradeStatus = TradeStatus.COMPLETED;
        oldTradeManager.updateStatus(tradeStatus);
        assertEquals(oldTradeManager.getTradeStatus(), tradeStatus);
        assertFalse(oldTradeManager.isRejected());
        assertTrue(oldTradeManager.isCompleted());
        assertFalse(oldTradeManager.isUnconfirmed());
        assertFalse(oldTradeManager.isRejected());
    }

    /**
     * Verifies trades an account has made can be properly retrieved
     */
    public void testGetTradesAccount() {
        oldTradeManager = setUpTradeManager();
        oldTradeUtility = setUpTradeUtility();
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        oldTradeManager.createTrade(time, "UTM", false, 11, 12,
                items1, items2, accountManager);
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 11);
        oldTradeUtility.setAccount(account);
        assertEquals(oldTradeUtility.getAllTradesAccount().size(), 2);
        List<Integer> items3 = new ArrayList<>();
        List<Integer> items4 = new ArrayList<>();
        items3.add(102);
        items4.add(202);
        oldTradeManager.createTrade(time, "UTM", false, 10, 11,
                items3, items4, accountManager);
        assertEquals(oldTradeUtility.getAllTradesAccount().size(), 3);
        Account account2 = new Account("Username", "Password", permissionsList, 14);
        oldTradeUtility.setAccount(account2);
        assertEquals(oldTradeUtility.getAllTradesAccount().size(), 0);
        Account account3 = new Account("Username", "Password", permissionsList, 10);
        oldTradeUtility.setAccount(account3);
        assertEquals(oldTradeUtility.getAllTradesAccount().size(), 2);
    }

    public void testGetThreeRecent() {
        oldTradeManager = setUpTradeManager();
        oldTradeUtility = setUpTradeUtility();
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 21);
        oldTradeUtility.setAccount(account);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        List<Integer> items3 = new ArrayList<>();
        List<Integer> items4 = new ArrayList<>();
        List<Integer> items = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        items3.add(301);
        items4.add(401);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        oldTradeManager.createTrade(time.minusDays(1), "UTM", false, 20, 21,
                items1, items2, accountManager);
        oldTradeManager.createTrade(time.minusDays(1), "UTM", false, 21, 22,
                items3, items, accountManager);
        List<Integer> ans = new ArrayList<>();
        assertEquals(oldTradeUtility.getTopThreePartnersIds(), ans);
        assertEquals(oldTradeUtility.getRecentOneWay(), ans);
        assertEquals(oldTradeUtility.getRecentTwoWay(), ans);
        for (OldTrade oldTrade : oldTradeUtility.getAllTradesAccount()) {
            oldTradeManager.setOldTrade(oldTrade);
            oldTradeManager.updateStatus(TradeStatus.CONFIRMED);
        }
        assertTrue(oldTradeUtility.getTopThreePartnersIds().contains(22));
        assertTrue(oldTradeUtility.getTopThreePartnersIds().contains(20));
        assertTrue(oldTradeUtility.getRecentOneWay().contains(301));
        assertTrue(oldTradeUtility.getRecentTwoWay().contains(201));
        accountManager = setUpAccount();
        oldTradeManager.createTrade(time, "UTM", false, 21, 23,
                items1, items2, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 25, 21,
                items3, items1, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 22, 25,
                items3, items4, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 23, 21,
                items, items2, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 21, 25,
                items4, items, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 22, 21,
                items4, items, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 21, 23,
                items3, items, accountManager);
        for (OldTrade oldTrade : oldTradeUtility.getAllTradesAccount()) {
            oldTradeManager.setOldTrade(oldTrade);
            oldTradeManager.updateStatus(TradeStatus.CONFIRMED);
        }
        assertTrue(oldTradeUtility.getTopThreePartnersIds().contains(22));
        assertTrue(oldTradeUtility.getTopThreePartnersIds().contains(25));
        assertTrue(oldTradeUtility.getTopThreePartnersIds().contains(23));
        assertFalse(oldTradeUtility.getTopThreePartnersIds().contains(21));
        assertTrue(oldTradeUtility.getRecentOneWay().contains(201));
        assertTrue(oldTradeUtility.getRecentOneWay().contains(301));
        assertTrue(oldTradeUtility.getRecentOneWay().contains(401));
        assertTrue(oldTradeUtility.getRecentTwoWay().contains(201));
        assertTrue(oldTradeUtility.getRecentTwoWay().contains(101));
    }

    /**
     * Verifies times lent and borrowed are properly retrieved
     */
    public void testGetLentBorrowed() {
        oldTradeManager = setUpTradeManager();
        oldTradeUtility = setUpTradeUtility();
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 41);
        oldTradeUtility.setAccount(account);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        List<Integer> items3 = new ArrayList<>();
        List<Integer> items = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        items3.add(301);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        oldTradeManager.createTrade(time, "UTM", false, 40, 41,
                items1, items, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 41, 40,
                items2, items, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 40, 41,
                items, items3, accountManager);
        oldTradeManager.createTrade(time, "UTM", false, 40, 41,
                items2, items3, accountManager);
        Integer ans = 2;
        Integer ans2 = 1;
        assertEquals(oldTradeUtility.getTimesLent(), ans);
        assertEquals(oldTradeUtility.getTimesBorrowed(), ans2);
    }

    /**
     * Verifies methods for get trades related to time work
     * Note: May sometimes be off by one if pc is slow
     */
    public void testGetTradeTime() {
        oldTradeManager = setUpTradeManager();
        oldTradeUtility = setUpTradeUtility();
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 50);
        oldTradeUtility.setAccount(account);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time3days = LocalDateTime.now().minusDays(3);
        LocalDateTime time7days = LocalDateTime.now().minusDays(6);
        LocalDateTime time12days = LocalDateTime.now().minusDays(12);
        accountManager = setUpAccount();
        oldTradeManager.createTrade(time, "UTM", false, 50, 51,
                items1, items2, accountManager);
        oldTradeManager.createTrade(time3days, "UTM", false, 50, 51,
                items1, items2, accountManager);
        oldTradeManager.createTrade(time7days, "UTM", false, 50, 51,
                items1, items2, accountManager);
        oldTradeManager.createTrade(time12days, "UTM", false, 51, 50,
                items2, items1, accountManager);
        oldTradeManager.createTrade(time12days, "UTM", false, 50, 51,
                items1, items2, accountManager);
        oldTradeManager.updateStatus(TradeStatus.CONFIRMED);
        Integer ans = 3;
        assertEquals(oldTradeUtility.getNumWeeklyTrades(), ans);
        Integer ans2 = 1;
        assertEquals(oldTradeUtility.getTimesIncomplete(), ans2);
    }
}

