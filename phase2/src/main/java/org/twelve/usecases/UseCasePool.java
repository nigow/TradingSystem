package org.twelve.usecases;

import org.twelve.gateways.GatewayPool;

/**
 * Initializes use cases and provides a pool for accessing them.
 *
 * @author Michael
 */

public class UseCasePool {
    private ItemManager itemManager;
    private StatusManager statusManager;
    private TradeManager tradeManager;
    private WishlistManager wishlistManager;
    private AccountRepository accountRepository;
    private ThresholdRepository thresholdRepository;
    private LoginManager loginManager;
    private SessionManager sessionManager;
    private final GatewayPool gatewayPool;

    /**
     * Creates all the required use cases for the program.
     */
    public UseCasePool(GatewayPool gatewayPool) {
        this.gatewayPool = gatewayPool;
        initializeUseCases();
    }


    private void initializeUseCases() {
        //TODO: not really a todo but note that these will stop being an error when gatewayPool and usecases are updated
        accountRepository = new AccountRepository(gatewayPool.getAccountGateway());
        thresholdRepository = new ThresholdRepository(gatewayPool.getThresholdsGateway());
        itemManager = new ItemManager(gatewayPool.getItemsGateway(), accountRepository);
        wishlistManager = new WishlistManager(accountRepository, itemManager);
        tradeManager = new TradeManager(gatewayPool.getTradeGateway(), thresholdRepository,
                accountRepository, itemManager, wishlistManager);
        statusManager = new StatusManager(accountRepository, tradeManager, thresholdRepository);
        loginManager = new LoginManager(accountRepository);
        sessionManager = new SessionManager(accountRepository);
    }

    /**
     * @return an instance of ItemManager use case.
     */
    public ItemManager getItemManager() {
        return itemManager;
    }

    /**
     * @return an instance of StatusManager use case.
     */
    public StatusManager getStatusManager() {
        return statusManager;
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
    public WishlistManager getWishlistManager() {
        return wishlistManager;
    }


    /**
     * @return An instance of AccountRepository use case
     */
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    /**
     * @return An instance of LoginManager use case.
     */
    public LoginManager getLoginManager() {
        return loginManager;
    }

    /**
     * {@return} An instance of SessionManager use case.
     */
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * {@return} An instance of ThresholdRepository use case.
     */
    public ThresholdRepository getThresholdRepository() {
        return thresholdRepository;
    }
}
