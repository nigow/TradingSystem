package gateways;

import usecases.*;

import java.io.File;
import java.io.IOException;

/**
 * Configures the application by initializing CSV gateways and use cases.
 *
 * @author Michael
 */

// TODO: I will add a gateway pool soon to divide responsibilities better.
public class CSVUseCasePool implements UseCasePool {
    private AccountManager accountManager;
    private AuthManager authManager;
    private ItemManager itemManager;
    private FreezingUtility freezingUtility;
    private OldTradeManager oldTradeManager;
    private WishlistManager wishlistManager;
    private ItemUtility itemUtility;
    private OldTradeUtility oldTradeUtility;
    private AccountRepository accountRepository;
    private PermissionManager permissionManager;
    private LoginManager loginManager;

    /**
     * Creates ManualConfig and initializes the required use cases
     * using existing CSV file.
     *
     * @throws IOException If the given csv file cannot be accessed
     */
    public CSVUseCasePool() throws IOException {
        String filePath = System.getProperty("user.dir") + "/out/files/";
        initializeUseCases(filePath);
    }

    /**
     * Creates ManualConfig and initializes the required use cases
     * using a csv file that can be set manually.
     *
     * @param filePath file path to the folder containing csv files
     * @throws IOException If the given csv file cannot be accessed
     */
    public CSVUseCasePool(String filePath) throws IOException {
        initializeUseCases(filePath);
    }

    private void initializeUseCases(String filePath) throws IOException {

        File dir = new File(filePath);
        if (!dir.isDirectory() && !dir.mkdirs()) throw new IOException();

        AccountGateway csvAccountGateway =
                new CSVAccountGateway(filePath + "accounts.csv");

        RestrictionsGateway csvRestrictionsGateway =
                new CSVRestrictionsGateway(filePath + "restrictions.csv");
        // TODO: Comment out second one & remove first one.
        accountRepository = new AccountRepository(null);
//        accountRepository = new AccountRepository(csvAccountGateway);

        freezingUtility = new FreezingUtility(accountRepository , csvRestrictionsGateway.getRestrictions());
        ItemsGateway csvItemsGateway = new CSVItemsGateway(filePath + "items.csv");
        itemManager = new ItemManager(csvItemsGateway);


        wishlistManager = new WishlistManager(accountRepository, itemUtility);


        TradeGateway csvTradeGateway =
                new CSVTradeGateway(filePath + "trades.csv");
        oldTradeManager = new OldTradeManager(csvTradeGateway);
        oldTradeUtility = new OldTradeUtility(oldTradeManager);
    }

    /**
     * @return an instance of AccountManager use case.
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * @return an instance of AuthManager use case.
     */
    public AuthManager getAuthManager() {
        return authManager;
    }

    /**
     * @return an instance of ItemManager use case.
     */
    public ItemManager getItemManager() {
        return itemManager;
    }

    /**
     * @return an instance of FreezingUtility use case.
     */
    public FreezingUtility getFreezingUtility() {
        return freezingUtility;
    }

    /**
     * @return an instance of OldTradeManager use case.
     */
    public OldTradeManager getOldTradeManager() {
        return oldTradeManager;
    }

    /**
     * @return An instance of WishlistUtility use case.
     */
    public WishlistManager getWishlistManager() {
        return wishlistManager;
    }

    /**
     * @return An instance of ItemUtility use case.
     */
    public ItemUtility getItemUtility() {
        return itemUtility;
    }

    /**
     * @return An instance of OldTradeUtility use case.
     */
    public OldTradeUtility getOldTradeUtility() {
        return oldTradeUtility;
    }

    @Override
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    @Override
    public LoginManager getLoginManager() {
        return loginManager;
    }
}
