package controllers;
import java.util.List;
import java.util.ArrayList;
import presenters.InventoryPresenter;
import presenters.ConsoleInventoryPresenter;
import usecases.ItemManager;
import usecases.AccountManager;
import entities.Item;

/**
 * Controller that deals with the inventory
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class InventoryController {

    InventoryPresenter inventoryPresenter;
    ItemManager itemManager;
    AccountManager accountManager;

    public InventoryController(ItemManager itemManager, AccountManager accountManager) {
        this.itemManager = itemManager;
        this.accountManager = accountManager;
        this.inventoryPresenter = new ConsoleInventoryPresenter();
    }

    public void run() {
        List<String> menu = new ArrayList<>();
        menu.add("Display inventory");
        menu.add("Add to wishlist");
        menu.add("Remove from inventory");
        menu.add("Display pending items");
        menu.add("Approve a pending item");
        // TODO: how to make admin options secret?
        String option = inventoryPresenter.displayInventoryOptions(menu);
        if (option.equals("0")) {
            displayInventory();
        }
    }

    public void displayInventory() {
        List<Item> allItems = itemManager.getAllItems();
        //TODO: is allItems supposed to be a list of Strings or Items?
    }
    //TODO: talk to tairi and hai yang about how to add to wishlist
}
