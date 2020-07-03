package gateways;

import usecases.*;

import java.io.File;
import java.io.IOException;

/**
 * Configures the application by initializing CSV gateways and use cases.
 * @author Michael
 */
public class ManualConfigCSV implements ManualConfig {
    private AccountManager accountManager;
    private AuthManager authManager;
    private ItemManager itemManager;
    private FreezingUtility freezingUtility;
    private TradeManager tradeManager;
    private WishlistUtility wishlistUtility;
    private ItemUtility itemUtility;
    private TradeUtility tradeUtility;

    /**
     * Creates ManualConfig and initializes the required usecases
     * using existing CSV file.
     */
    public ManualConfigCSV() throws IOException {
        String filePath = System.getProperty("user.dir") + "/out/Files/";
        initializeUseCases(filePath);
    }

    /**
     * Creates ManualConfig and initializes the required usecases
     * using a csv file that can be set mannually.
     * @param filePath file path to the folder containing csv files
     */
    public ManualConfigCSV(String filePath) throws IOException {
        initializeUseCases(filePath);
    }

    private void initializeUseCases(String filePath) throws IOException{

        File dir = new File(filePath);
        if (!dir.mkdirs()) throw new IOException();

        RestrictionsGateway csvRestrictionsGateway =
                new CSVRestrictionsGateway(filePath + "restrictions.csv");
        freezingUtility = new FreezingUtility(csvRestrictionsGateway);
        ItemsGateway csvItemsGateway = new CSVItemsGateway(filePath + "items.csv");
        itemManager = new ItemManager(csvItemsGateway);
        itemUtility = new ItemUtility(itemManager);


        AccountGateway csvAccountGateway =
                new CSVAccountGateway(filePath + "accounts.csv");
        accountManager = new AccountManager(csvAccountGateway);
        if (accountManager.getAccountsList().size() == 0)  {
            accountManager.createAdminAccount("admin", "12345");
        }
        authManager = new AuthManager(csvAccountGateway, csvRestrictionsGateway);
        wishlistUtility = new WishlistUtility(csvAccountGateway, csvItemsGateway);


        TradeGateway csvTradeGateway =
                new CSVTradeGateway(filePath + "trades.csv");
        tradeManager = new TradeManager(csvTradeGateway);
        tradeUtility = new TradeUtility(tradeManager);
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
     * @return an instance of TradeManager use case.
     */
    public TradeManager getTradeManager() {
        return tradeManager;
    }

    /**
     * @return An instance of WishlistUtility use case.
     */
    public WishlistUtility getWishlistUtility() {
        return wishlistUtility;
    }

    /**
     * @return An instance of ItemUtility use case.
     */
    public ItemUtility getItemUtility() {
        return itemUtility;
    }

    /**
     * @return An instance of TradeUtility use case.
     */
    public TradeUtility getTradeUtility() {
        return tradeUtility;
    }
}
