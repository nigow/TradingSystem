package gateways;

import usecases.*;

import java.io.IOException;

/**
 * Configures the application.
 * @author Michael
 */
public class ManualConfig {
    private AccountManager accountManager;
    private AuthManager authManager;
    private final ItemManager itemManager;
    private final FreezingUtility freezingUtility;
    private TradeManager tradeManager;
    private WishlistUtility wishlistUtility;
    private ItemUtility itemUtility;

    /**
     * Creates ManualConfig and initializes the required usecases.
     */
    public ManualConfig() {
        String filePath = System.getProperty("user.dir") + "/out/Files/";
        RestrictionsGateway csvRestrictionsGateway =
                new CSVRestrictionsGateway(filePath + "restrictions.csv");
        freezingUtility = new FreezingUtility(csvRestrictionsGateway);
        ItemsGateway csvItemsGateway = new CSVItemsGateway(filePath + "items.csv");
        itemManager = new ItemManager(csvItemsGateway);
        itemUtility = new ItemUtility(itemManager);


        try {
            // TODO: Replace csvPath with wherever we store the information.
            AccountGateway csvAccountGateway =
                    new CSVAccountGateway(filePath + "accounts.csv");

            accountManager = new AccountManager(csvAccountGateway);
            authManager = new AuthManager(csvAccountGateway, csvRestrictionsGateway);
            wishlistUtility = new WishlistUtility(csvAccountGateway, csvItemsGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

        try {
            TradeGateway csvTradeGateway = new CSVTradeGateway(filePath + "items.csv");
            tradeManager = new TradeManager(csvTradeGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

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

    public WishlistUtility getWishlistUtility() {
        return wishlistUtility;
    }

    public ItemUtility getItemUtility() {
        return itemUtility;
    }

}
