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

    /**
     * Runs the main menu of the program
     */
    public void run() {
        String option;

        Map<String, Runnable> actions = new LinkedHashMap<>();
        actions.put("View your items", this::displayAllYourInventory);
        if (authManager.canCreateItem(accountManager.getCurrAccount())) {
            actions.put("Create a new item", this::createItem);
        }
        actions.put("Remove your item from inventory", this::removeFromYourInventory);
        actions.put("Return to main menu", () -> {
        });

        List<String> menu = new ArrayList<>(actions.keySet());

        do {
            option = inventoryPresenter.displayInventoryOptions(menu);

            if (inputHandler.isNum(option)) {
                int action = Integer.parseInt(option);

                if (action < actions.size()) {
                    actions.values().toArray(new Runnable[0])[action].run();

                } else {
                    inventoryPresenter.customMessage("That number does not correspond to an item");
                }
            } else {
                inventoryPresenter.invalidInput();
            }

        } while (!option.equals(String.valueOf(menu.size() - 1)));

    }

    private void displayAllYourInventory() {
        List<String> allYourItems = itemUtility.getAllInventoryOfAccountString(accountManager.getCurrAccountID());
        this.inventoryPresenter.displayInventory(allYourItems);
    }

    /**
     * Runs the createItem submenu
     */
    private void createItem() {
        boolean confirmedItem = false;
        boolean nameGiven = false;
        boolean descriptionGiven = false;
        boolean exit = false;
        String name = "";
        String description = "";

        while (!confirmedItem && !exit) {
            if (!nameGiven) {
                name = inventoryPresenter.askName();
                if (inputHandler.isExitStr(name)) {
                    inventoryPresenter.abortMessage();
                    exit = true;
                } else if (!inputHandler.isValidCSVStr(name)) {
                    inventoryPresenter.customMessage("You cannot have a comma in your item name");
                } else {
                    nameGiven = true;
                }
            } else if (!descriptionGiven) {
                description = inventoryPresenter.askDescription();
                if (inputHandler.isExitStr(description)) {
                    inventoryPresenter.abortMessage();
                    exit = true;
                } else if (!inputHandler.isValidCSVStr(description)) {
                    inventoryPresenter.customMessage("You cannot have a comma in your item description");
                } else {
                    descriptionGiven = true;
                }
            } else {
                String confirm = inventoryPresenter.confirmItem(name, description);
                if (inputHandler.isExitStr(confirm)) {
                    inventoryPresenter.abortMessage();
                    exit = true;
                } else if (inputHandler.isFalse(confirm)) {
                    inventoryPresenter.customMessage("Item not added.");
                    nameGiven = false;
                    descriptionGiven = false;
                } else if (inputHandler.isTrue(confirm)) {
                    itemManager.createItem(name, description, accountManager.getCurrAccountID());
                    inventoryPresenter.customMessage("Item successfully added, pending admin approval!");
                    confirmedItem = true;
                } else {
                    inventoryPresenter.invalidInput();
                }
            }
        }
    }

    /**
     * Runs the remove your item from inventory submenu
     */
    private void removeFromYourInventory() {
        displayAllYourInventory();
        boolean isValid = false;
        while (!isValid) {
            String option = inventoryPresenter.removeFromInventory();
            if (inputHandler.isExitStr(option)) {
                inventoryPresenter.abortMessage();
                isValid = true;
            } else if (inputHandler.isNum(option)) {
                int ind = Integer.parseInt(option);
                List<Item> items = itemUtility.getAllInventoryOfAccount(accountManager.getCurrAccountID());
                if (ind < items.size()) {
                    boolean removed = itemManager.removeItem(items.get(ind));
                    if (removed) {
                        isValid = true;
                        inventoryPresenter.customMessage("Item successfully removed!");
                    } else {
                        inventoryPresenter.customMessage("Item could not be removed.");
                    }
                } else {
                    inventoryPresenter.customMessage("That number does not correspond to an item");
                }

            } else {
                inventoryPresenter.invalidInput();
            }
        }
    }

}
