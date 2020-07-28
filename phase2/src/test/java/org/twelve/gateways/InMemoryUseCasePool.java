package gateways;

import controllers.UseCasePool;
import entities.*;
import usecases.*;

import java.util.HashMap;

public class InMemoryUseCasePool implements UseCasePool {
    private AccountManager accountManager;
    private AuthManager authManager;
    private ItemManager itemManager;
    private FreezingUtility freezingUtility;
    private OldTradeManager oldTradeManager;
    private WishlistManager wishlistManager;
    private ItemUtility itemUtility;
    private OldTradeUtility oldTradeUtility;


    public InMemoryUseCasePool(){
        InMemoryAccountGateway accountGateway = new InMemoryAccountGateway(new HashMap<Integer, Account>());
        InMemoryItemGateway itemGateway = new InMemoryItemGateway(new HashMap<Integer, Item>());
        InMemoryTradeGateway tradeGateway = new InMemoryTradeGateway(new HashMap<Integer, OldTrade>(), new HashMap<Integer, TimePlace>());
        InMemoryRestrictionsGateway restrictionsGateway = new InMemoryRestrictionsGateway(new Restrictions(1,1,1));
        // Restrictions are tentative

        this.accountManager = new AccountManager(accountGateway);
        this.authManager = new AuthManager(accountGateway, restrictionsGateway);
        this.itemManager = new ItemManager(itemGateway);
        // TODO fix this for tests
        this.freezingUtility = null;
//      this.freezingUtility = new StatusManager(restrictionsGateway);
        this.oldTradeManager = new OldTradeManager(tradeGateway);
        this.wishlistManager = null;
//      this.wishlistManager = new WishlistManager(accountGateway, itemGateway);
        this.itemUtility = new ItemUtility();
        this.oldTradeUtility = new OldTradeUtility(oldTradeManager);

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

    public FreezingUtility getStatusManager() {
        return this.freezingUtility;
    }

    @Override
    public OldTradeManager getOldTradeManager() {
        return this.oldTradeManager;
    }

    @Override
    public WishlistManager getWishlistManager() {
        return this.wishlistManager;
    }

    @Override
    public ItemUtility getItemUtility() {
        return this.itemUtility;
    }

    @Override
    public OldTradeUtility getOldTradeUtility() {
        return this.oldTradeUtility;
    }

    @Override
    public AccountRepository getAccountRepository() {
        return null;
    }

}
