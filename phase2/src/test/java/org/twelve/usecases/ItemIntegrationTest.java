package org.twelve.usecases;

import org.junit.Test;
import org.twelve.entities.Account;
import org.twelve.entities.Item;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ram.InMemoryAccountGateway;
import org.twelve.gateways.ram.InMemoryItemGateway;
import org.twelve.gateways.ItemsGateway;
import junit.framework.TestCase;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.ItemUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An integration test to verify integration of usecases, gateways, and entities is successful.
 * @author Isaac
 */
public class ItemIntegrationTest extends TestCase {
    private ItemManager itemManager;
    private ItemsGateway itemsGateway;
    private AccountRepository accountRepository;

    private void setUpItemManager() {
        AccountGateway accountGateway = new InMemoryAccountGateway(new HashMap<>());
        accountRepository = new AccountRepository(accountGateway);
        Item initial = new Item(0, "Potato", "A Vegetable", 10);
        HashMap<Integer, Item> h = new HashMap<>();
        h.put(0, initial);

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
     * Verifies Items can be properly added and removed from the system
     */
    public void testRemoveItems() {
        setUpItemManager();
        itemManager.createItem("Tomato", "A Fruit", 11);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        itemManager.createItem("Jacket", "A Cool Jacket", 12);
        itemManager.createItem("CS Hoodie", "A Cool Hoodie", 12);
        assertEquals(itemManager.getAllItemsString().size(), 6);
        Item item = new Item(10, "Shirt", "A small shirt", 11);
        assertTrue(itemManager.removeItem(itemManager.getAllItems().get(0).getItemID()));
        assertTrue(itemManager.removeItem(itemManager.getAllItems().get(1).getItemID()));
        assertFalse(itemManager.removeItem(item.getItemID()));
        assertEquals(itemManager.getAllItemsString().size(), 4);
        itemsGateway.save(item.getItemID(), item.getName(), item.getDescription(),
                item.isApproved(), item.getOwnerID(), true);
        itemsGateway.populate(itemManager);
        assertEquals(itemManager.getAllItemsString().size(), 5);
        assertTrue(itemManager.removeItem(item.getItemID()));
        assertEquals(itemManager.getAllItemsString().size(), 4);
    }

    /**
     * Verifies Items can be properly be retrieved from the system
     */
    public void testGetAllItems() {
        setUpItemManager();
        assertEquals(itemManager.getAllItemsString().size(), 1);
        assertEquals(itemManager.getAllItems().get(0).getItemID(), 0);
        assertEquals(itemManager.getAllItems().get(0).getOwnerID(), 10);
        assertEquals(itemManager.getAllItems().get(0).getName(), "Potato");
        assertEquals(itemManager.getAllItems().get(0).getDescription(), "A Vegetable");
        assertFalse(itemManager.getAllItems().get(0).isApproved());
        itemManager.createItem("Tomato", "A Fruit", 11);
        assertEquals(itemManager.getAllItemsString().size(), 2);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        assertEquals(itemManager.getAllItemsString().size(), 4);
        assertEquals(itemManager.getAllItems().get(3).getName(), "Test");
    }

    /**
     * Verifies Items can be approved and disproved and they can be retrieved
     * based on approval
     */
    public void testApproveItems() {
        setUpItemManager();
        itemManager.createItem("Tomato", "A Fruit", 11);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        assertEquals(itemManager.getDisapproved().size(), 4);
        assertEquals(itemManager.getApproved().size(), 0);
        itemManager.updateApproval(itemManager.getAllItems().get(0).getItemID(),true);
        itemManager.updateApproval(itemManager.getAllItems().get(2).getItemID(),true);
        assertEquals(itemManager.getDisapproved().size(), 2);
        assertEquals(itemManager.getApproved().size(), 2);
        itemManager.updateApproval(itemManager.getAllItems().get(2).getItemID(),false);
        assertEquals(itemManager.getDisapproved().size(), 3);
        assertEquals(itemManager.getApproved().size(), 1);
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

