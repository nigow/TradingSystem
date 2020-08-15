package org.twelve.usecases;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ram.InMemoryGatewayPool;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TradeManagerIntegrationTest extends TestCase {
    GatewayPool inMemoryGatewayPool;
    AccountRepository accountRepository;
    ItemManager itemManager;
    WishlistManager wishlistManager;
    TradeManager tradeManager;

    private void setTradeManager() {
        inMemoryGatewayPool = new InMemoryGatewayPool();
        SecurityUtility securityUtility = new SecurityUtility("lBhBaINFEvv7hzsI", "AES");
        accountRepository = new AccountRepository(inMemoryGatewayPool.getAccountGateway(), securityUtility);
        itemManager = new ItemManager(inMemoryGatewayPool.getItemsGateway(), accountRepository);
        wishlistManager = new WishlistManager(accountRepository, itemManager);

        tradeManager = new TradeManager(accountRepository,
                new ThresholdRepository(inMemoryGatewayPool.getThresholdsGateway()),
                wishlistManager,
                inMemoryGatewayPool.getTradeGateway(),
                new ItemManager(inMemoryGatewayPool.getItemsGateway(),accountRepository));


    }

    /**
     * Test that TradeUtility is able to return a string
     * representation of a Trade successfully.
     */
    public void testTradeAsString() {
        setTradeManager();
        accountRepository.createAccount("test", "12345", new ArrayList<>(), "placeholder");
        accountRepository.createAccount("test2", "12345", new ArrayList<>(), "placeholder");
        itemManager.createItem("chess", "A chess board", 1);

        List<Integer> tradeIds = new ArrayList<>();
        tradeIds.add(accountRepository.getAccountFromUsername("test").getAccountID());
        tradeIds.add(accountRepository.getAccountFromUsername("test2").getAccountID());

        List<List<Integer>> itemIds = new ArrayList<>();
        List<Integer> itemID = new ArrayList<>();

    }
}
