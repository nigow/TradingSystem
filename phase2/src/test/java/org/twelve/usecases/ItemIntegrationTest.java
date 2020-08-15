package org.twelve.usecases;

import org.twelve.entities.Item;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ram.InMemoryAccountGateway;
import org.twelve.gateways.ram.InMemoryItemGateway;
import org.twelve.gateways.ItemsGateway;
import junit.framework.TestCase;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.ItemManager;

import java.util.HashMap;

/**
 * An integration test to verify integration of usecases, gateways, and entities is successful.
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



    /**
     * Verifies Items can be properly retrieved from a users inventory
     */
    /**
    public void testInventory() {
        Account initial = new Account("admin", "1234", new ArrayList<>(), 10, "M");
        Account initial1 = new Account("admin2", "1234", new ArrayList<>(), 11, "M");
        Account initial2 = new Account("admin3", "1234", new ArrayList<>(), 12, "M");
        Account initial3 = new Account("admin4", "1234", new ArrayList<>(), 14, "M");
        HashMap<Integer, Account> h = new HashMap<>();
        AccountGateway accountGateway = new InMemoryAccountGateway(h);
        h.put(10, initial);
        h.put(11, initial1);
        h.put(12, initial2);
        h.put(14, initial3);
        AccountRepository accountRepository = new AccountRepository(accountGateway);
        WishlistManager wishlistManager = new WishlistManager(accountRepository, itemManager);
        setUpItemManager();
        itemManager.createItem("Tomato", "A Fruit", 11);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(11).size(), 2);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(12).size(), 1);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(10).size(), 1);

        assertEquals(itemManager.getAllItemsString().size(), 4);
        itemManager.createItem("Jacket", "A Cool Jacket", 12);
        itemManager.createItem("CS Hoodie", "A Cool Hoodie", 12);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(12).size(), 3);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(14).size(), 0);
        itemManager.updateOwner(itemManager.getAllItems().get(5).getItemID(), 11);
        itemManager.updateOwner(itemManager.getAllItems().get(4).getItemID(), 11);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(12).size(), 1);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(11).size(), 4);
        assertEquals(itemManager.getDisprovedInventoryOfAccount(10).size(), 1);
        for (Item item: itemManager.getAllItems()) {
            itemManager.updateApproval(item.getItemID(), true);
        }
        assertEquals(itemManager.getApprovedInventoryOfAccount(12).size(), 1);
        assertEquals(itemManager.getApprovedInventoryOfAccount(11).size(), 4);
        assertEquals(itemManager.getApprovedInventoryOfAccount(10).size(), 1);
        assertEquals(itemManager.getNotInAccount(10).size()
                , 5);
        assertEquals(itemManager.getNotInAccount(11).size()
                , 2);
        assertEquals(itemManager.getNotInAccount(12).size()
                , 5);
        assertEquals(itemManager.getNotInAccount(14).size()
                , 6);
        assertEquals(itemManager.getNotInAccount(10).size(), 3);
        assertEquals(itemManager.getNotInAccount(11).size(), 1);
        assertEquals(itemManager.getNotInAccount(12).size(), 4);
        assertEquals(itemManager.getNotInAccount(14).size(), 4);
    }
     **/
}

