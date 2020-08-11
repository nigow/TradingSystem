package org.twelve.usecases;

import org.twelve.gateways.GatewayPool;

/**
 * Initializes use cases and provides a pool for accessing them.
 *
 * @author Michael
 */

public class UseCasePool {
    private ItemManager itemManager;
    private CityManager cityManager;
    private StatusManager statusManager;
    private TradeManager tradeManager;
    private WishlistManager wishlistManager;
    private AccountRepository accountRepository;
    private ThresholdRepository thresholdRepository;
    private LoginManager loginManager;
    private SessionManager sessionManager;
    private GatewayPool gatewayPool;

    /**
     * Creates all the required use cases for the program.
     */
    public UseCasePool(GatewayPool gatewayPool) {
        this.gatewayPool = gatewayPool;
        initializeUseCases();
    }


    private void initializeUseCases() {
        accountRepository = new AccountRepository(gatewayPool.getAccountGateway());
        thresholdRepository = new ThresholdRepository(gatewayPool.getThresholdsGateway());
        itemManager = new ItemManager(gatewayPool.getItemsGateway(), accountRepository);
        wishlistManager = new WishlistManager(accountRepository, itemManager);
        tradeManager = new TradeManager(gatewayPool.getTradeGateway(), thresholdRepository,
                accountRepository, itemManager, wishlistManager);
        statusManager = new StatusManager(accountRepository, tradeManager, thresholdRepository);
        loginManager = new LoginManager(accountRepository);
        sessionManager = new SessionManager(accountRepository);
        cityManager = new CityManager(gatewayPool.getCitiesGateway(), accountRepository);
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

    /**
     * @return An instance of CityManager use case.
     */
    public CityManager getCityManager() {
        return cityManager;
    }

    /**
     * @param gatewayPool An instance of gateway pool storing all gateways
     * @param demoMode Whether we should be able to switch to demo mode
     */
    // TODO: Look into strategy pattern for this.
    public void switchMode(GatewayPool gatewayPool, boolean demoMode) {
        this.gatewayPool = gatewayPool;
        if (demoMode) {
            switchToDemoMode();
        }
        else {
            switchToNormalMode();
        }
    }

    private void switchToDemoMode() {
        itemManager.switchToDemoMode(gatewayPool.getItemsGateway());
        cityManager.switchToDemoMode(gatewayPool.getCitiesGateway());
        accountRepository.switchToDemoMode(gatewayPool.getAccountGateway());
        thresholdRepository.switchToDemoMode(gatewayPool.getThresholdsGateway());
        tradeManager.switchToDemoMode(gatewayPool.getTradeGateway());
    }

    private void switchToNormalMode() {
        itemManager.switchToNormalMode(gatewayPool.getItemsGateway());
        cityManager.switchToNormalMode(gatewayPool.getCitiesGateway());
        accountRepository.switchToNormalMode(gatewayPool.getAccountGateway());
        thresholdRepository.switchToNormalMode(gatewayPool.getThresholdsGateway());
        tradeManager.switchToNormalMode(gatewayPool.getTradeGateway());
    }

}
