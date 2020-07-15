package gateways;

import entities.*;
import usecases.*;

import java.util.HashMap;

public class InMemoryUseCasePool implements UseCasePool {
    private AccountManager accountManager;
    private AuthManager authManager;
    private ItemManager itemManager;
    private FreezingUtility freezingUtility;
    private TradeManager tradeManager;
    private WishlistUtility wishlistUtility;
    private ItemUtility itemUtility;
    private TradeUtility tradeUtility;


    public InMemoryUseCasePool(){
        InMemoryAccountGateway accountGateway = new InMemoryAccountGateway(new HashMap<Integer, Account>());
        InMemoryItemGateway itemGateway = new InMemoryItemGateway(new HashMap<Integer, Item>());
        InMemoryTradeGateway tradeGateway = new InMemoryTradeGateway(new HashMap<Integer, Trade>(), new HashMap<Integer, TimePlace>());
        InMemoryRestrictionsGateway restrictionsGateway = new InMemoryRestrictionsGateway(new Restrictions(1,1,1));
        // Restrictions are tentative

        this.accountManager = new AccountManager(accountGateway);
        this.authManager = new AuthManager(accountGateway, restrictionsGateway);
        this.itemManager = new ItemManager(itemGateway);
        this.freezingUtility = new FreezingUtility(restrictionsGateway);
        this.tradeManager = new TradeManager(tradeGateway);
        this.wishlistUtility = new WishlistUtility(accountGateway, itemGateway);
        this.itemUtility = new ItemUtility(itemManager);
        this.tradeUtility = new TradeUtility(tradeManager);

        if (accountManager.getAccountsList().size() == 0)  {
            accountManager.createAdminAccount("admin", "12345");
        }

    }


    @Override
    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    @Override
    public AuthManager getAuthManager() {
        return this.authManager;
    }

    @Override
    public ItemManager getItemManager() {
        return this.itemManager;
    }

    @Override
    public FreezingUtility getFreezingUtility() {
        return this.freezingUtility;
    }

    @Override
    public TradeManager getTradeManager() {
        return this.tradeManager;
    }

    @Override
    public WishlistUtility getWishlistUtility() {
        return this.wishlistUtility;
    }

    @Override
    public ItemUtility getItemUtility() {
        return this.itemUtility;
    }

    @Override
    public TradeUtility getTradeUtility() {
        return this.tradeUtility;
    }

}
