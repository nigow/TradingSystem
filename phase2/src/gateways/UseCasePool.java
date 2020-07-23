package gateways;

import usecases.*;

/**
 * Configures the application by integrating gateway interfaces to use cases.
 *
 * @author Michael
 */
public interface UseCasePool {

    /**
     * Gets an instance of AccountManager use case.
     *
     * @return an instance of AccountManager use case.
     */
    AccountManager getAccountManager();

    /**
     * Gets an instance of AuthManager use case.
     *
     * @return an instance of AuthManager use case.
     */
    AuthManager getAuthManager();

    /**
     * Gets an instance of ItemManager use case.
     *
     * @return an instance of ItemManager use case.
     */
    ItemManager getItemManager();

    /**
     * Gets an instance of FreezingUtility use case.
     *
     * @return an instance of FreezingUtility use case.
     */
    FreezingUtility getFreezingUtility();

    /**
     * Gets an instance of TradeManager use case.
     *
     * @return an instance of TradeManager use case.
     */
    TradeManager getTradeManager();

    /**
     * Gets an instance of WishlistUtility use case.
     *
     * @return An instance of WishlistUtility use case.
     */
    WishlistManager getWishlistManager();

    /**
     * Gets an instance of ItemUtility use case.
     *
     * @return An instance of ItemUtility use case.
     */
    ItemUtility getItemUtility();

    /**
     * Gets an instance of TradeUtility use case.
     *
     * @return An instance of TradeUtility use case.
     */
    TradeUtility getTradeUtility();

    /**

     */
    AccountRepository getAccountRepository();

}
