package org.twelve.controllers;

import org.twelve.gateways.CSVGatewayPool;
import org.twelve.gateways.GatewayPool;
import org.twelve.usecases.*;

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
    private SessionManager sessionManager;
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
        //TODO: not really a todo but note that these will stop being an error when gatewayPool and usecases are updated
        accountRepository = new AccountRepository(gatewayPool.getAccountGateway());
        tradeManager = new TradeManager();
        freezingUtility = new FreezingUtility(accountRepository, tradeManager,
                gatewayPool.getRestrictionsGateway().getRestrictions());
        itemManager = new ItemManager(gatewayPool.getItemsGateway());
        wishlistManager = new WishlistManager(accountRepository, itemManager);
        permissionManager = new PermissionManager(accountRepository);
        loginManager = new LoginManager(accountRepository);
        sessionManager = new SessionManager(accountRepository);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
