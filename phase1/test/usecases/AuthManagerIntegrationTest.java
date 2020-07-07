package usecases;

import entities.*;
import gateways.*;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AuthManagerIntegrationTest extends TestCase {

    private AccountGateway accountGateway;
    protected AccountManager accountManager;
    private RestrictionsGateway restrictionsGateway;
    protected AuthManager authManager;

    protected AuthManager setUpAuthentication(){
        Account initial = new Account("Miyo_Sasaki", "I love you Hinode", new ArrayList<>(Arrays.asList(Permissions.BORROW, Permissions.LEND, Permissions.UNFREEZE)), 0);
        HashMap<Integer, Account> h = new HashMap<>();
        h.put(0, initial);

        accountGateway = new InMemoryAccountGateway(h);

        accountManager = new AccountManager(accountGateway);

        accountManager.createStandardAccount("Kento_Hinode", "Sorry but I love Emilia");
        accountManager.updateAccount(accountManager.getCurrAccount());
        assertEquals(accountManager.getAccountsList().size(), 2);

        Restrictions currRestrictions = new Restrictions(0, 1, 3);
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
        assertFalse(account1.getPermissions().contains(Permissions.CONFIRM_ITEM));
        authManager.addPermissionByID(account1, Permissions.CONFIRM_ITEM);
        assertTrue(account1.getPermissions().contains(Permissions.CONFIRM_ITEM));

        authManager.addPermissionsByIDs(account1, Arrays.asList(
                Permissions.ADD_ADMIN, Permissions.CHANGE_RESTRICTIONS));
        assertTrue(account1.getPermissions().containsAll(Arrays.asList(Permissions.CONFIRM_ITEM, Permissions.CHANGE_RESTRICTIONS)));


        authManager.removePermissionByID(account1, Permissions.BORROW);
        assertFalse(account1.getPermissions().contains(Permissions.BORROW));

        authManager.removePermissionsByIDs(account1, (Arrays.asList(
                Permissions.LEND, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST)));
        assertFalse(account1.getPermissions().containsAll(Arrays.asList(Permissions.LEND, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST)));
    }

    public void testCanPerformActions(){
        authManager = setUpAuthentication();
        accountManager.createAdminAccount("Yorushika", "n-buna");
        Account account2 = accountManager.getCurrAccount();

        assertTrue(authManager.canBrowseInventory(account2));
        account2.removePermission(Permissions.BROWSE_INVENTORY);
        assertFalse(authManager.canBrowseInventory(account2));

        assertTrue(authManager.canCreateItem(account2));
        account2.removePermission(Permissions.CREATE_ITEM);
        assertFalse(authManager.canCreateItem(account2));

        assertTrue(authManager.canAddToWishlist(account2));
        account2.removePermission(Permissions.ADD_TO_WISHLIST);
        assertFalse(authManager.canAddToWishlist(account2));

        assertTrue(authManager.canConfirmItem(account2));
        account2.removePermission(Permissions.CONFIRM_ITEM);
        assertFalse(authManager.canConfirmItem(account2));

        assertTrue(authManager.canChangeRestrictions(account2));
        account2.removePermission(Permissions.CHANGE_RESTRICTIONS);
        assertFalse(authManager.canChangeRestrictions(account2));

        assertTrue(authManager.canAddAdmin(account2));
        account2.removePermission(Permissions.ADD_ADMIN);
        assertFalse(authManager.canAddAdmin(account2));

        assertTrue(authManager.canFreeze(account2));
        account2.removePermission(Permissions.FREEZE);
        assertFalse(authManager.canFreeze(account2));

        assertTrue(authManager.canUnfreeze(account2));
        account2.removePermission(Permissions.UNFREEZE);
        assertFalse(authManager.canUnfreeze(account2));

        assertTrue(authManager.canLogin(account2));
        account2.removePermission(Permissions.LOGIN);
        assertFalse(authManager.canLogin(account2));
    }

    public void testFrozen(){
        authManager = setUpAuthentication();
        Account account1 = accountManager.getCurrAccount();

        assertFalse(authManager.isFrozen(account1));
        assertFalse(authManager.isPending(account1));

        account1.removePermission(Permissions.BORROW);
        assertFalse(authManager.isFrozen(account1));
        assertFalse(authManager.isPending(account1));

        account1.removePermission(Permissions.LEND);
        assertTrue(authManager.isFrozen(account1));

        account1.addPermission(Permissions.REQUEST_UNFREEZE);
        assertFalse(authManager.isPending(account1));

        assertTrue(authManager.requestUnfreeze(account1));
        assertFalse(authManager.requestUnfreeze(account1));
        assertTrue(authManager.isPending(account1));
    }

    public void testBrokenRestrictions(){
        authManager = setUpAuthentication();
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
        //canBeFrozen() Tests

        assertFalse(authManager.canBeFrozen(tradeUtility, account, null)); //makes sure account can't be frozen when it has the unfreeze permission
        assertFalse(authManager.canBeFrozen(tradeUtility, account1, null)); //checks that account1 cant be frozen as lend = borrows


        Item item3 = new Item(2, "anime poster", "drawn by Mashiro Shiina", 0);
        //account borrows from account1
        tradeManager.createTrade(LocalDateTime.of(2020, 7, 2, 0, 0), "Bahen",
                false, 0, 1,
                new ArrayList<>(Arrays.asList(item3.getItemID())), new ArrayList<>(), null);
        assertFalse(authManager.canBeFrozen(tradeUtility, account, null)); //checks that account cant be frozen as it has done nothing wrong yet
        assertTrue(authManager.canBeFrozen(tradeUtility, account1, null)); //checks that account1 can be frozen since it borrowed 1 more than it has lent

        tradeManager.updateStatus(TradeStatus.REJECTED);
        account.removePermission(Permissions.UNFREEZE); //basically account is no longer admin and can be frozen
        assertTrue(authManager.canBeFrozen(tradeUtility, account, null)); //Checks if account can be frozen given that it has too many incomplete trades
        assertTrue(authManager.canBeFrozen(tradeUtility, account1, null));

        account.removePermission(Permissions.BORROW);
        account.removePermission(Permissions.LEND);
        assertFalse(authManager.canBeFrozen(tradeUtility, account, null)); //makes sure a frozen account can't be frozen

        account1.addPermission(Permissions.UNFREEZE);
        assertFalse(authManager.canBeFrozen(tradeUtility, account1, null)); //makes sure an account that can unfreeze can't be frozen

        //canTrade() tests (only reason the tests are together is the massive amount of code needed to set up tradeUtility and trades)

        assertFalse(authManager.canTrade(account)); //frozen accounts cant trade
        assertTrue(authManager.canTrade(account1)); //at weekly limit so can't trade

        tradeManager.createTrade(LocalDateTime.of(2020, 7, 2, 20, 3),
                "Bahen", false, 0, 1,
                new ArrayList<>(), new ArrayList<>(Arrays.asList(item1.getItemID())), null);
        assertFalse(authManager.canTrade(account)); //frozen accounts cant trade
        assertFalse(authManager.canTrade(account1)); //at weekly limit so can't trade
    }







}
