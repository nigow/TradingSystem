package org.twelve.usecases;

import junit.framework.TestCase;
import org.twelve.entities.Account;
import org.twelve.entities.Item;
import org.twelve.entities.Trade;
import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.TradeGateway;
import org.twelve.gateways.ram.InMemoryTradeGateway;
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
        accountRepository = new AccountRepository(inMemoryGatewayPool.getAccountGateway());
        itemManager = new ItemManager(inMemoryGatewayPool.getItemsGateway(), accountRepository);
        wishlistManager = new WishlistManager(accountRepository, itemManager);

        tradeManager = new TradeManager(
                inMemoryGatewayPool.getTradeGateway(),
                new ThresholdRepository(inMemoryGatewayPool.getThresholdsGateway()),
                accountRepository,
                new ItemManager(inMemoryGatewayPool.getItemsGateway(),accountRepository),
                wishlistManager
        );


    }

    /**
     * Test that TradeUtility is able to return a string
     * representation of a Trade successfully.
     */
    public void testTradeAsString() {
        setTradeManager();
        accountRepository.createAccount("test", "12345", new ArrayList<>());
        accountRepository.createAccount("test2", "12345", new ArrayList<>());
        itemManager.createItem("chess", "A chess board", 1);

        ArrayList<Integer> tradeIds = new ArrayList<>();
        tradeIds.add(accountRepository.getAccountFromUsername("test").getAccountID());
        tradeIds.add(accountRepository.getAccountFromUsername("test2").getAccountID());

        ArrayList<Integer> itemIds = new ArrayList<>();
        itemIds.add(itemManager.getAllItems().get(0).getItemID());
        tradeManager.createTrade(LocalDateTime.now(), "UTM", true, tradeIds, itemIds);


    }
}
