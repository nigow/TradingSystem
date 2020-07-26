package controllers;

import usecases.*;

/**
 * Configures the application by integrating gateway interfaces to use cases.
 *
 * @author Michael
 */
interface UseCasePool {

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
     *
     * @return An instance of WishlistUtility use case.
     */
    WishlistManager getWishlistManager();


    /**
     * @return An instance of AccountRepository use case
     */
    AccountRepository getAccountRepository();

    /**
     * @return An instance of PermissionManager use case.
     */
    PermissionManager getPermissionManager();

    /**
     * @return An instance of LoginManager use case.
     */
    LoginManager getLoginManager();
}
