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

    /**
     * Creates ManualConfig and initializes the required usecases.
     */
    public ManualConfig() {
        String filePath = System.getProperty("user.dir") + "/out/Files/";
        CSVRestrictionsGateway csvRestrictionsGateway =
                new CSVRestrictionsGateway(filePath + "restrictions.csv");
        freezingUtility = new FreezingUtility(csvRestrictionsGateway);


        try {
            // TODO: Replace csvPath with wherever we store the information.
            CSVAccountGateway csvAccountGateway =
                    new CSVAccountGateway(filePath + "accounts.csv");

            this.accountManager = new AccountManager(csvAccountGateway);
            authManager = new AuthManager(csvAccountGateway, csvRestrictionsGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

        try {
            CSVTradeGateway csvTradeGateway = new CSVTradeGateway(filePath + "items.csv");
            tradeManager = new TradeManager(csvTradeGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }


        CSVItemsGateway csvItemsGateway = new CSVItemsGateway(filePath + "items.csv");
        itemManager = new ItemManager(csvItemsGateway);


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


}
