package controllers.experimental;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import controllers.InputHandler;
import entities.Item;
import gateways.UseCasePool;
import presenters.InventoryPresenter;
import usecases.AuthManager;
import usecases.ItemManager;
import usecases.AccountManager;
import usecases.ItemUtility;

/**
 * Controller that deals with your inventory.
 *
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class YourInventoryController {

    /**
     * The presenter counterpart to this class
     */
    private final InventoryPresenter inventoryPresenter;

    /**
     * An instance of ItemManager to access items
     */
    private final ItemManager itemManager;

    /**
     * An instance of AccountManager to access accounts
     */
    private final AccountManager accountManager;

    /**
     * An instance of ItemUtility to utilize items
     */
    private final ItemUtility itemUtility;

    /**
     * An instance of AuthManager to check permissions
     */
    private final AuthManager authManager;

    /**
     * An instance of ControllerHelper for helper methods
     */
    private final InputHandler inputHandler;

    /**
     * Constructor to initialize all the instances, from ManualConfig,
     * and add options to actions depending on the user's permissions
     *
     * @param useCasePool       the configuration for the program
     * @param inventoryPresenter the presenter for displaying the inventory
     */
    public YourInventoryController(UseCasePool useCasePool, InventoryPresenter inventoryPresenter) {
        this.itemManager = useCasePool.getItemManager();
        this.accountManager = useCasePool.getAccountManager();
        this.itemUtility = useCasePool.getItemUtility();
        this.inventoryPresenter = inventoryPresenter;
        this.authManager = useCasePool.getAuthManager();
        this.inputHandler = new InputHandler();
    }

    private void displayAllYourInventory() {
        List<String> allYourItems = itemUtility.getAllInventoryOfAccountString(accountManager.getCurrAccountID());
        this.inventoryPresenter.displayInventory(allYourItems);
    }

    /**
     * Creates an item
     *
     * @param name The name of the item
     * @param description The description of the item
     */
    private boolean createItem(String name, String description) {
        while(true) {
            if(inputHandler.isValidCSVStr(name) && inputHandler.isValidCSVStr(description)) {
                itemManager.createItem(name, description, accountManager.getCurrAccountID());
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Removes an item from the inventory
     *
     * @param ind The index in the list of items in this user's
     */
    private boolean removeFromYourInventory(String ind) {
        List<Item> items = itemUtility.getAllInventoryOfAccount(accountManager.getCurrAccountID());
        return itemManager.removeItem(items.get(Integer.parseInt(ind)));
    }

}
