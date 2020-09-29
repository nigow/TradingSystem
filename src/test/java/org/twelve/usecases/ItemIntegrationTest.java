package org.twelve.usecases;

import org.twelve.entities.Item;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ram.InMemoryAccountGateway;
import org.twelve.gateways.ram.InMemoryItemGateway;
import org.twelve.gateways.ItemsGateway;
import junit.framework.TestCase;
import org.twelve.usecases.account.AccountRepository;
import org.twelve.usecases.item.ItemManager;
import org.twelve.usecases.system.SecurityUtility;

import java.util.HashMap;

/**
 * An integration test to verify integration of usecases, gateways, and entities is successful.
 *
 * @author Isaac
 */
public class ItemIntegrationTest extends TestCase {
    private ItemManager itemManager;
    private ItemsGateway itemsGateway;

    private void setUpItemManager() {
        AccountGateway accountGateway = new InMemoryAccountGateway(new HashMap<>());
        SecurityUtility securityUtility = new SecurityUtility("lBhBaINFEvv7hzsI", "AES");
        AccountRepository accountRepository = new AccountRepository(accountGateway, securityUtility);
        String[] initialString = new String[4];
        initialString[0] = "Potato";
        initialString[1] = "A Vegetable";
        initialString[2] = "false";
        initialString[3] = "10";
        HashMap<Integer, String[]> h = new HashMap<>();
        h.put(0, initialString);

        itemsGateway = new InMemoryItemGateway(h);
        itemManager = new ItemManager(itemsGateway, accountRepository);
    }

    /**
     * Verifies that it is possible to initialize an ItemManager and ItemUtility given
     * a valid path provided.
     */
    public void testItemInitialization() {
        setUpItemManager();
        assertNotNull(this.itemManager);
    }

    public void testSavingItems() {
        setUpItemManager();
        Item item = new Item(1, "Jacket", "A Cool Jacket", 11);
        Item item2 = new Item(2, "CS Hoodie", "A Cool Hoodie", 12);
        itemsGateway.save(item.getItemID(), item.getName(), item.getDescription(),
                item.isApproved(), item.getOwnerID(), true);
        itemsGateway.populate(itemManager);
        assertEquals(itemManager.findItemById(1).getName(), item.getName());
        assertNull(itemManager.findItemById(2));
    }
}