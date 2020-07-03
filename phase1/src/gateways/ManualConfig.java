package gateways;

import usecases.*;

/**
 * Configures the application by integrating gateway interfaces to useCases.
 * @author Michael
 */
public interface ManualConfig {

    /**
     * @return an instance of AccountManager use case.
     */
    AccountManager getAccountManager();

    /**
     * @return an instance of AuthManager use case.
     */
    AuthManager getAuthManager();

    /**
     * @return an instance of ItemManager use case.
     */
    ItemManager getItemManager();

    /**
     * @return an instance of FreezingUtility use case.
     */
    FreezingUtility getFreezingUtility();

    /**
     * @return an instance of TradeManager use case.
     */
    TradeManager getTradeManager();

    /**
     * @return An instance of WishlistUtility use case.
     */
    WishlistUtility getWishlistUtility();

    /**
     * @return An instance of ItemUtility use case.
     */
    ItemUtility getItemUtility();

    /**
     * @return An instance of TradeUtility use case.
     */
    TradeUtility getTradeUtility();

}
