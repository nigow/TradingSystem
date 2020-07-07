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
public class TradeIntegrationTest extends TestCase {
    private TradeManager tradeManager;
    private TradeUtility tradeUtility;
    private TradeGateway tradeGateway;
    private AccountManager accountManager;

    private TradeManager setUpTradeManager() {
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(100);
        items2.add(200);
        Trade initial = new Trade(0, 0, false, 10, 11,
                items1, items2, 0);
        HashMap<Integer, Trade> h = new HashMap<>();
        h.put(0, initial);

        LocalDateTime time = LocalDateTime.now();
        TimePlace first = new TimePlace(0, time, "UTM");
        HashMap<Integer, TimePlace> hash = new HashMap<>();
        hash.put(0, first);
        tradeGateway = new InMemoryTradeGateway(h, hash);
        tradeManager = new TradeManager(tradeGateway);
        tradeManager.setTrade(initial);
        return tradeManager;
    }

    private TradeUtility setUpTradeUtility() {
        tradeManager = setUpTradeManager();
        tradeUtility = new TradeUtility(tradeManager);
        return tradeUtility;
    }

    private AccountManager setUpAccount(){

        Account initial = new Account("admin", "1234", new ArrayList<>(), 0);
        HashMap<Integer, Account> h = new HashMap<>();
        h.put(0, initial);

        AccountGateway accountGateway = new InMemoryAccountGateway(h);

        accountManager = new AccountManager(accountGateway);
        assertTrue(accountManager.setCurrAccount("admin"));
        return accountManager;

    }

    /**
     * Verifies that it is possible to initialize TradeManager and TradeUtility, given
     * a valid path provided.
     */
    public void testTradeInitialization() {
        tradeManager = setUpTradeManager();
        tradeUtility = setUpTradeUtility();
        assertNotNull(this.tradeManager);
        assertNotNull(this.tradeUtility);
    }

    /**
     * Verifies that when the trade is set the new trade being set is saved
     */
    public void testChangingTrade() {
        tradeManager = setUpTradeManager();
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        Trade trade2 = new Trade(1, 1, false, 12, 13,
                items1, items2, 0);
        LocalDateTime time = LocalDateTime.now();
        TimePlace timePlace2 = new TimePlace(1, time, "UTM");
        tradeGateway.updateTrade(trade2, timePlace2);
        tradeManager.setTrade(trade2);
        assertEquals(tradeManager.getTrade(), trade2);
        assertEquals(tradeManager.getTimePlace(), timePlace2);
    }

    /**
     * Verifies trades are properly created
     */
    public void testCreatingTrades() {
        tradeManager = setUpTradeManager();
        assertEquals(tradeManager.getAllTrades().size(), 1);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        tradeManager.createTrade(time, "UTM", false, 12, 13,
                items1, items2, accountManager);
        assertEquals(tradeManager.getAllTrades().size(), 2);
        assertEquals(tradeManager.getAllTrades().get(1).getTraderOneID(), 12);
        assertEquals(tradeManager.getAllTrades().get(1).getItemOneIDs(), items1);
        tradeManager.reverseTrade(accountManager);
        assertEquals(tradeManager.getAllTrades().size(), 3);
        assertEquals(tradeManager.getAllTrades().get(2).getTraderOneID(), 12);
        assertEquals(tradeManager.getAllTrades().get(2).getItemOneIDs(), items2);
        assertTrue(tradeManager.getAllTrades().get(2).isPermanent());
    }

    /**
     * Verifies trade's timePlace can be properly updated
     */
    public void testEditTimePlaceTrade() {
        tradeManager = setUpTradeManager();
        LocalDateTime newTime = LocalDateTime.of(2000, Month.JANUARY, 1,19, 30);
        tradeManager.editTimePlace(newTime, "Home", 11);
        assertEquals(tradeManager.getTimePlace().getTime(), newTime);
        assertEquals(tradeManager.getTimePlace().getPlace(), "Home");
        assertEquals(tradeManager.getTrade().getLastEditorID(), 11);
    }

    /**
     * Verifies trade's status can be properly updated
     */
    public void testUpdateStatus() {
        tradeManager = setUpTradeManager();
        assertTrue(tradeManager.isUnconfirmed());
        TradeStatus tradeStatus = TradeStatus.COMPLETED;
        tradeManager.updateStatus(tradeStatus);
        assertEquals(tradeManager.getTradeStatus(), tradeStatus);
        assertFalse(tradeManager.isRejected());
        assertTrue(tradeManager.isCompleted());
        assertFalse(tradeManager.isUnconfirmed());
        assertFalse(tradeManager.isRejected());
    }

    /**
     * Verifies trades an account has made can be properly retrieved
     */
    public void testGetTradesAccount() {
        tradeManager = setUpTradeManager();
        tradeUtility = setUpTradeUtility();
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        tradeManager.createTrade(time, "UTM", false, 11, 12,
                items1, items2, accountManager);
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 11);
        tradeUtility.setAccount(account);
        assertEquals(tradeUtility.getAllTradesAccount().size(), 2);
        List<Integer> items3 = new ArrayList<>();
        List<Integer> items4 = new ArrayList<>();
        items3.add(102);
        items4.add(202);
        tradeManager.createTrade(time, "UTM", false, 10, 11,
                items3, items4, accountManager);
        assertEquals(tradeUtility.getAllTradesAccount().size(), 3);
        Account account2 = new Account("Username", "Password", permissionsList, 14);
        tradeUtility.setAccount(account2);
        assertEquals(tradeUtility.getAllTradesAccount().size(), 0);
        Account account3 = new Account("Username", "Password", permissionsList, 10);
        tradeUtility.setAccount(account3);
        assertEquals(tradeUtility.getAllTradesAccount().size(), 2);
    }

    public void testGetThreeRecent() {
        tradeManager = setUpTradeManager();
        tradeUtility = setUpTradeUtility();
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 21);
        tradeUtility.setAccount(account);
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
        tradeManager.createTrade(time.minusDays(1), "UTM", false, 20, 21,
                items1, items2, accountManager);
        tradeManager.createTrade(time.minusDays(1), "UTM", false, 21, 22,
                items3, items, accountManager);
        List<Integer> ans = new ArrayList<>();
        assertEquals(tradeUtility.getTopThreePartnersIds(), ans);
        assertEquals(tradeUtility.getRecentOneWay(), ans);
        assertEquals(tradeUtility.getRecentTwoWay(), ans);
        for (Trade trade: tradeUtility.getAllTradesAccount()) {
            tradeManager.setTrade(trade);
            tradeManager.updateStatus(TradeStatus.CONFIRMED);
        }
        assertTrue(tradeUtility.getTopThreePartnersIds().contains(22));
        assertTrue(tradeUtility.getTopThreePartnersIds().contains(20));
        assertTrue(tradeUtility.getRecentOneWay().contains(301));
        assertTrue(tradeUtility.getRecentTwoWay().contains(101));
        assertTrue(tradeUtility.getRecentTwoWay().contains(201));
        accountManager = setUpAccount();
        tradeManager.createTrade(time, "UTM", false, 21, 23,
                items1, items2, accountManager);
        tradeManager.createTrade(time, "UTM", false, 25, 21,
                items3, items1, accountManager);
        tradeManager.createTrade(time, "UTM", false, 22, 25,
                items3, items4, accountManager);
        tradeManager.createTrade(time, "UTM", false, 23, 21,
                items, items2, accountManager);
        tradeManager.createTrade(time, "UTM", false, 21, 25,
                items4, items, accountManager);
        tradeManager.createTrade(time, "UTM", false, 22, 21,
                items4, items, accountManager);
        tradeManager.createTrade(time, "UTM", false, 21, 23,
                items3, items, accountManager);
        for (Trade trade: tradeUtility.getAllTradesAccount()) {
            tradeManager.setTrade(trade);
            tradeManager.updateStatus(TradeStatus.CONFIRMED);
        }
        assertTrue(tradeUtility.getTopThreePartnersIds().contains(22));
        assertTrue(tradeUtility.getTopThreePartnersIds().contains(25));
        assertTrue(tradeUtility.getTopThreePartnersIds().contains(23));
        assertFalse(tradeUtility.getTopThreePartnersIds().contains(21));
        assertTrue(tradeUtility.getRecentOneWay().contains(201));
        assertTrue(tradeUtility.getRecentOneWay().contains(301));
        assertTrue(tradeUtility.getRecentOneWay().contains(401));
        assertTrue(tradeUtility.getRecentTwoWay().contains(201));
        assertTrue(tradeUtility.getRecentTwoWay().contains(301));
        assertTrue(tradeUtility.getRecentTwoWay().contains(101));
    }

    /**
     * Verifies times lent and borrowed are properly retrieved
     */
    public void testGetLentBorrowed() {
        tradeManager = setUpTradeManager();
        tradeUtility = setUpTradeUtility();
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 41);
        tradeUtility.setAccount(account);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        List<Integer> items3 = new ArrayList<>();
        List<Integer> items = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        items3.add(301);
        LocalDateTime time = LocalDateTime.now();
        accountManager = setUpAccount();
        tradeManager.createTrade(time, "UTM", false, 40, 41,
                items1, items, accountManager);
        tradeManager.createTrade(time, "UTM", false, 41, 40,
                items2, items, accountManager);
        tradeManager.createTrade(time, "UTM", false, 40, 41,
                items, items3, accountManager);
        tradeManager.createTrade(time, "UTM", false, 40, 41,
                items2, items3, accountManager);
        Integer ans = 2;
        Integer ans2 = 1;
        assertEquals(tradeUtility.getTimesLent(), ans);
        assertEquals(tradeUtility.getTimesBorrowed(), ans2);
    }

    /**
     * Verifies methods for get trades related to time work
     * Note: May sometimes be off by one if pc is slow
     */
    public void testGetTradeTime() {
        tradeManager = setUpTradeManager();
        tradeUtility = setUpTradeUtility();
        Permissions permissions = Permissions.LOGIN;
        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        Account account = new Account("Username", "Password", permissionsList, 50);
        tradeUtility.setAccount(account);
        List<Integer> items1 = new ArrayList<>();
        List<Integer> items2 = new ArrayList<>();
        items1.add(101);
        items2.add(201);
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time3days = LocalDateTime.now().minusDays(3);
        LocalDateTime time7days = LocalDateTime.now().minusDays(6);
        LocalDateTime time12days = LocalDateTime.now().minusDays(12);
        accountManager = setUpAccount();
        tradeManager.createTrade(time, "UTM", false, 50, 51,
                items1, items2, accountManager);
        tradeManager.createTrade(time3days, "UTM", false, 50, 51,
                items1, items2, accountManager);
        tradeManager.createTrade(time7days, "UTM", false, 50, 51,
                items1, items2, accountManager);
        tradeManager.createTrade(time12days, "UTM", false, 51, 50,
                items2, items1, accountManager);
        tradeManager.createTrade(time12days, "UTM", false, 50, 51,
                items1, items2, accountManager);
        tradeManager.updateStatus(TradeStatus.CONFIRMED);
        Integer ans = 3;
        assertEquals(tradeUtility.getNumWeeklyTrades(), ans);
        Integer ans2 = 1;
        assertEquals(tradeUtility.getTimesIncomplete(), ans2);
    }
}

