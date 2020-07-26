package controllers;

import gateways.CSVGatewayPool;
import gateways.GatewayPool;
import usecases.*;

import java.io.IOException;

/**
 * Initializes use cases and provides a pool for accessing them.
 *
 * @author Michael
 */

class CSVUseCasePool implements UseCasePool {
    private ItemManager itemManager;
    private FreezingUtility freezingUtility;
    private TradeManager tradeManager;
    private WishlistManager wishlistManager;
    private AccountRepository accountRepository;
    private PermissionManager permissionManager;
    private LoginManager loginManager;
    private final GatewayPool gatewayPool;

    /**
     * Creates all the required use cases for the program.
     *
     * @throws IOException If the given csv file cannot be accessed
     */
    CSVUseCasePool() throws IOException {
        gatewayPool = new CSVGatewayPool();
        initializeUseCases();
    }


    private void initializeUseCases() {

        accountRepository = new AccountRepository(gatewayPool.getAccountGateway());
        freezingUtility = new FreezingUtility(accountRepository,
                gatewayPool.getRestrictionsGateway().getRestrictions());
        itemManager = new ItemManager(gatewayPool.getItemsGateway());
        tradeManager = new TradeManager();
        wishlistManager = new WishlistManager(accountRepository, tradeManager);
        permissionManager = new PermissionManager(accountRepository);
        loginManager = new LoginManager(accountRepository);

    }

    /**
     * @return an instance of ItemManager use case.
     */
    @Override
    public ItemManager getItemManager() {
        return itemManager;
    }

    /**
     * @return an instance of FreezingUtility use case.
     */
    @Override
    public FreezingUtility getFreezingUtility() {
        return freezingUtility;
    }

    /**
     * @return an instance of TradeManager use case.
     */
    @Override
    public TradeManager getTradeManager() {
        return tradeManager;
    }

    /**
     * @return An instance of WishlistUtility use case.
     */
    @Override
    public WishlistManager getWishlistManager() {
        return wishlistManager;
    }


    /**
     * @return An instance of AccountRepository use case
     */
    @Override
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    /**
     * @return An instance of PermissionManager use case.
     */
    @Override
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    /**
     * @return An instance of LoginManager use case.
     */
    @Override
    public LoginManager getLoginManager() {
        return loginManager;
    }
}
