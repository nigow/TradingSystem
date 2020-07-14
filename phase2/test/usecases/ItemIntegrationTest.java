package usecases;

import entities.Account;
import entities.Item;
import gateways.AccountGateway;
import gateways.InMemoryAccountGateway;
import gateways.InMemoryItemGateway;
import gateways.ItemsGateway;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An integration test to verify integration of usecases, gateways, and entities is successful.
 * @author Isaac
 */
public class ItemIntegrationTest extends TestCase {
    private ItemManager itemManager;
    private ItemUtility itemUtility;
    private ItemsGateway itemsGateway;

    private ItemManager setUpItemManager() {
        Item initial = new Item(0, "Potato", "A Vegetable", 10);
        HashMap<Integer, Item> h = new HashMap<>();
        h.put(0, initial);

        itemsGateway = new InMemoryItemGateway(h);
        itemManager = new ItemManager(itemsGateway);
        itemUtility = new ItemUtility(itemManager);
        return itemManager;
    }

    private ItemUtility setUpItemUtility() {
        itemManager = setUpItemManager();
        itemUtility = new ItemUtility(itemManager);
        return itemUtility;
    }

    /**
     * Verifies that it is possible to initialize an ItemManager and ItemUtility given
     * a valid path provided.
     */
    public void testItemInitialization() {
        itemManager = setUpItemManager();
        itemUtility = setUpItemUtility();
        assertNotNull(this.setUpItemManager());
        assertNotNull(this.setUpItemUtility());
    }


    public void testUpdatingItems() {
        itemManager = setUpItemManager();
        Item item = new Item(1, "Jacket", "A Cool Jacket", 11);
        Item item2 = new Item(2, "CS Hoodie", "A Cool Hoodie", 12);
        itemsGateway.updateItem(item);
        assertTrue(itemManager.getAllItems().contains(item));
        assertFalse(itemManager.getAllItems().contains(item2));
    }

    /**
     * Verifies Items can be properly added and removed from the system
     */
    public void testRemoveItems() {
        itemManager = setUpItemManager();
        itemUtility = setUpItemUtility();
        itemManager.createItem("Tomato", "A Fruit", 11);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        itemManager.createItem("Jacket", "A Cool Jacket", 12);
        itemManager.createItem("CS Hoodie", "A Cool Hoodie", 12);
        assertEquals(itemManager.getAllItems().size(), 6);
        Item item = new Item(10, "Shirt", "A small shirt", 11);
        assertTrue(itemManager.removeItem(itemManager.getAllItems().get(0)));
        assertTrue(itemManager.removeItem(itemManager.getAllItems().get(1)));
        assertFalse(itemManager.removeItem(item));
        assertEquals(itemManager.getAllItems().size(), 4);
        itemsGateway.updateItem(item);
        assertEquals(itemManager.getAllItems().size(), 5);
        assertTrue(itemManager.removeItem(item));
        assertEquals(itemManager.getAllItems().size(), 4);
    }

    /**
     * Verifies Items can be properly be retrieved from the system
     */
    public void testGetAllItems() {
        itemManager = setUpItemManager();
        assertEquals(itemManager.getAllItems().size(), 1);
        assertEquals(itemManager.getAllItems().get(0).getItemID(), 0);
        assertEquals(itemManager.getAllItems().get(0).getOwnerID(), 10);
        assertEquals(itemManager.getAllItems().get(0).getName(), "Potato");
        assertEquals(itemManager.getAllItems().get(0).getDescription(), "A Vegetable");
        assertFalse(itemManager.getAllItems().get(0).isApproved());
        itemManager.createItem("Tomato", "A Fruit", 11);
        assertEquals(itemManager.getAllItems().size(), 2);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        assertEquals(itemManager.getAllItems().size(), 4);
        assertEquals(itemManager.getAllItems().get(3).getName(), "Test");
    }

    /**
     * Verifies Items can be approved and disproved and they can be retrieved
     * based on approval
     */
    public void testApproveItems() {
        itemManager = setUpItemManager();
        itemUtility = setUpItemUtility();
        itemManager.createItem("Tomato", "A Fruit", 11);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        assertEquals(itemUtility.getDisapproved().size(), 4);
        assertEquals(itemUtility.getApproved().size(), 0);
        itemManager.updateApproval(itemManager.getAllItems().get(0),true);
        itemManager.updateApproval(itemManager.getAllItems().get(2),true);
        assertEquals(itemUtility.getDisapproved().size(), 2);
        assertEquals(itemUtility.getApproved().size(), 2);
        itemManager.updateApproval(itemManager.getAllItems().get(2),false);
        assertEquals(itemUtility.getDisapproved().size(), 3);
        assertEquals(itemUtility.getApproved().size(), 1);
    }

    /**
     * Verifies Items can be properly retrieved from a users inventory
     */
    public void testInventory() {
        Account initial = new Account("admin", "1234", new ArrayList<>(), 10);
        Account initial1 = new Account("admin2", "1234", new ArrayList<>(), 11);
        Account initial2 = new Account("admin3", "1234", new ArrayList<>(), 12);
        Account initial3 = new Account("admin4", "1234", new ArrayList<>(), 14);
        HashMap<Integer, Account> h = new HashMap<>();
        AccountGateway accountGateway = new InMemoryAccountGateway(h);
        h.put(10, initial);
        h.put(11, initial1);
        h.put(12, initial2);
        h.put(14, initial3);
        AccountManager accountManager = new AccountManager(accountGateway);
        itemManager = setUpItemManager();
        itemUtility = setUpItemUtility();
        itemManager.createItem("Tomato", "A Fruit", 11);
        itemManager.createItem("Book", "A Good book", 12);
        itemManager.createItem("Test", "Testing", 11);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(11).size(), 2);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(12).size(), 1);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(10).size(), 1);

        assertEquals(itemManager.getAllItems().size(), 4);
        itemManager.createItem("Jacket", "A Cool Jacket", 12);
        itemManager.createItem("CS Hoodie", "A Cool Hoodie", 12);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(12).size(), 3);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(14).size(), 0);
        itemManager.updateOwner(itemManager.getAllItems().get(5), 11);
        itemManager.updateOwner(itemManager.getAllItems().get(4), 11);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(12).size(), 1);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(11).size(), 4);
        assertEquals(itemUtility.getDisprovedInventoryOfAccount(10).size(), 1);
        for (Item item: itemManager.getAllItems()) {
            itemManager.updateApproval(item, true);
        }
        assertEquals(itemUtility.getApprovedInventoryOfAccount(12).size(), 1);
        assertEquals(itemUtility.getApprovedInventoryOfAccount(11).size(), 4);
        assertEquals(itemUtility.getApprovedInventoryOfAccount(10).size(), 1);
        accountManager.setCurrAccount(accountManager.getUsernameFromID(10));
        assertEquals(itemUtility.getNotInAccount(10, accountManager.getCurrWishlist()).size(), 5);
        accountManager.setCurrAccount(accountManager.getUsernameFromID(11));
        assertEquals(itemUtility.getNotInAccount(11, accountManager.getCurrWishlist()).size(), 2);
        accountManager.setCurrAccount(accountManager.getUsernameFromID(12));
        assertEquals(itemUtility.getNotInAccount(12, accountManager.getCurrWishlist()).size(), 5);
        accountManager.setCurrAccount(accountManager.getUsernameFromID(14));
        assertEquals(itemUtility.getNotInAccount(14, accountManager.getCurrWishlist()).size(), 6);
        List<Integer> wishlist = new ArrayList<>();
        wishlist.add(itemManager.getItemId(itemManager.getAllItems().get(1)));
        wishlist.add(itemManager.getItemId(itemManager.getAllItems().get(2)));
        assertEquals(itemUtility.getNotInAccount(10, wishlist).size(), 3);
        assertEquals(itemUtility.getNotInAccount(11, wishlist).size(), 1);
        assertEquals(itemUtility.getNotInAccount(12, wishlist).size(), 4);
        assertEquals(itemUtility.getNotInAccount(14, wishlist).size(), 4);
    }
}
