package gateways;

import entities.Account;
import entities.Permissions;
import usecases.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


        AccountGateway csvAccountGateway = setUpAccountGateway(filePath + "accounts.csv");
        accountManager = new AccountManager(csvAccountGateway);
        if (accountManager.getAccountsList().size() == 0)  {
            accountManager.createAdminAccount("admin", "12345");
        }
        authManager = new AuthManager(csvAccountGateway, csvRestrictionsGateway);
        wishlistUtility = new WishlistUtility(csvAccountGateway, csvItemsGateway);


        TradeGateway csvTradeGateway = setUpTradeGateway(filePath + "trades.csv");
        tradeManager = new TradeManager(csvTradeGateway);


    }

    private AccountGateway setUpAccountGateway(String filePath) {
        try {
            return new CSVAccountGateway(filePath);

        }
        catch (IOException e) {
            System.out.println("File not found");
            return null;
        }
    }

    private TradeGateway setUpTradeGateway(String filePath) {
        try {
            return new CSVTradeGateway(filePath);

        }
        catch (IOException e) {
            System.out.println("File not found");
            return null;
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

    /**
     * @return An instance of WishlistUtility use case.
     */
    public WishlistUtility getWishlistUtility() {
        return wishlistUtility;
    }

    /**
     * @return An instance of ItemUtility use case
     */
    public ItemUtility getItemUtility() {
        return itemUtility;
    }

}
