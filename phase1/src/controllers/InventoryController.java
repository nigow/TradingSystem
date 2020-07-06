package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import gateways.ManualConfig;
import presenters.InventoryPresenter;
import usecases.AuthManager;
import usecases.ItemManager;
import usecases.AccountManager;
import usecases.ItemUtility;

/**
 * Controller that deals with the inventory
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class InventoryController {

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
    private final ControllerInputValidator controllerInputValidator;

    /**
     * Constructor to initialize all the instances, from ManualConfig,
     * and add options to actions depending on the user's permissions
     * @param manualConfig the configuration for the program
     * @param inventoryPresenter the presenter for displaying the inventory
     */
    public InventoryController(ManualConfig manualConfig, InventoryPresenter inventoryPresenter) {
        this.itemManager = manualConfig.getItemManager();
        this.accountManager = manualConfig.getAccountManager();
        this.itemUtility = manualConfig.getItemUtility();
        this.inventoryPresenter = inventoryPresenter;
        this.authManager = manualConfig.getAuthManager();
        this.controllerInputValidator = new ControllerInputValidator();
    }


    /**
     * Runs the main menu of the program
     */
    public void run() {
        String option;

        Map<String, Runnable> actions = new LinkedHashMap<>();
        actions.put("View all approved items", this::displayFullInventory);
        actions.put("View your approved items", this::displayYourInventory);
        actions.put("View your pending items", this::displayYourPending);
        actions.put("View all items available for trading", this::displayOthersInventory);
        if (authManager.canAddToWishlist(accountManager.getCurrAccount())) {
            actions.put("Add to wishlist", this::addToWishlist);
        }
        if (authManager.canCreateItem(accountManager.getCurrAccount())) {
            actions.put("Create a new item", this::createItem);
        }
        actions.put("Remove your item from inventory", this::removeFromInventory);
        if (authManager.canConfirmItem(accountManager.getCurrAccount())) {
            actions.put("View items awaiting approval", this::displayPending);
            actions.put("Approve an item awaiting approval", this::approveItems);
        }
        actions.put("Return to main menu", () -> {});

        List<String> menu = new ArrayList<>(actions.keySet());

        do {
            option = inventoryPresenter.displayInventoryOptions(menu);

            if (controllerInputValidator.isNum(option)) {
                int action = Integer.parseInt(option);

                if (action < actions.size()) {
                    actions.values().toArray(new Runnable[0])[action].run();

                } else {
                    inventoryPresenter.customMessage("That number does not correspond to an item");
                }
            }

        } while(!option.equals(String.valueOf(menu.size() - 1)));

    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all the items
     */
    void displayFullInventory() {
        this.inventoryPresenter.customMessage("All Items:");
        List<String> allItems = itemUtility.getApprovedString();
        this.inventoryPresenter.displayInventory(allItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items belonging to the user
     */
    void displayYourInventory() {
        this.inventoryPresenter.customMessage("Your approved items:");
        List<String> allYourItems = itemUtility.getApprovedInventoryOfAccountString(accountManager.getCurrAccountID());
        this.inventoryPresenter.displayInventory(allYourItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items belonging to the user
     */
    void displayYourPending() {
        this.inventoryPresenter.customMessage("Your pending items:");
        List<String> allYourItems = itemUtility.getDisprovedInventoryOfAccountString(accountManager.getCurrAccountID());
        this.inventoryPresenter.displayInventory(allYourItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items except for the ones belonging to the user
     */
    void displayOthersInventory() {
        this.inventoryPresenter.customMessage("Items available for trading: ");
        List<String> othersItems = itemUtility.getNotInAccountString(accountManager.getCurrAccountID());
        this.inventoryPresenter.displayInventory(othersItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all the items awaiting approval
     */
    void displayPending() {
        inventoryPresenter.customMessage("Items awaiting approval:");
        List<String> all_disapproved = itemUtility.getDisapprovedString();
        inventoryPresenter.displayInventory(all_disapproved);
    }
    /**
     * Runs the createItem submenu
     */
    void createItem() {
        boolean confirmedItem = false;
        boolean nameGiven = false;
        boolean descriptionGiven = false;
        boolean exit = false;
        String name = "";
        String description = "";

        while (!confirmedItem && !exit) {
            if (!nameGiven) {
                name = inventoryPresenter.askName();
                if (controllerInputValidator.isExitStr(name)) {
                    exit = true;
                } else if (!controllerInputValidator.isValidCSVStr(name)) {
                    inventoryPresenter.customMessage("You cannot have a comma in your item name");
                } else {
                    nameGiven = true;
                }
            } else if (!descriptionGiven) {
                description = inventoryPresenter.askDescription();
                if (controllerInputValidator.isExitStr(description)) {
                    exit = true;
                } else if (!controllerInputValidator.isValidCSVStr(description)) {
                    inventoryPresenter.customMessage("You cannot have a comma in your item description");
                } else {
                    descriptionGiven = true;
                }
            } else {
                String confirm = inventoryPresenter.confirmItem(name, description);
                if (controllerInputValidator.isExitStr(confirm)) {
                    exit = true;
                } else if (confirm.equals("n")) {
                    inventoryPresenter.customMessage("Item not added.");
                    nameGiven = false;
                    descriptionGiven = false;
                } else if (confirm.equals("y")) {
                    itemManager.createItem(name, description, accountManager.getCurrAccountID());
                    inventoryPresenter.customMessage("Item successfully added!");
                    confirmedItem = true;
                } else {
                    inventoryPresenter.invalidInput();
                }
            }
        }
    }

    /**
     * Runs the add to wishlist submenu
     */
    void addToWishlist() {
        displayOthersInventory();
        boolean isValid = false;
        while (!isValid) {
            String option = inventoryPresenter.addToWishlist();
            if (controllerInputValidator.isExitStr(option)) {
                isValid = true;
            } else if (controllerInputValidator.isNum(option)) {
                int ind = Integer.parseInt(option);
                if (ind < itemUtility.getNotInAccount(accountManager.getCurrAccountID()).size()) {
                    accountManager.addItemToWishlist(itemUtility.getNotInAccount(accountManager.getCurrAccountID()).get(ind).getItemID());
                    inventoryPresenter.customMessage("Item successfully added to your wishlist!");
                    isValid = true;
                } else {
                    inventoryPresenter.customMessage("That number does not correspond to an item");
                }
            } else {
                inventoryPresenter.invalidInput();
            }
        }

    }

    /**
     * Runs the removeFromInventory submenu
     */
    void removeFromInventory() {
        displayYourInventory();
        boolean isValid = false;
        while (!isValid) {
            String option = inventoryPresenter.removeFromInventory();
            if (controllerInputValidator.isNum(option)) {
                int ind = Integer.parseInt(option);
                if (ind < itemUtility.getApprovedInventoryOfAccount(accountManager.getCurrAccountID()).size()) {
                    itemManager.removeItem(itemUtility.getApprovedInventoryOfAccount(accountManager.getCurrAccountID()).get(ind));
                    isValid = true;
                    inventoryPresenter.customMessage("Item successfully removed!");
                } else {
                    inventoryPresenter.customMessage("That number does not correspond to an item");
                }

            } else {
                inventoryPresenter.invalidInput();
            }
        }
    }

    /**
     * Runs the approve item submenu
     */
    void approveItems() {
        displayPending();
        boolean isValid = false;
        while (!isValid) {
            String option = inventoryPresenter.approveItem();
            if (controllerInputValidator.isNum(option)) {
                int ind = Integer.parseInt(option);
                if (ind < itemUtility.getDisapprovedString().size()) {
                    itemManager.updateApproval(itemUtility.getDisapproved().get(ind), true);
                    isValid = true;
                    inventoryPresenter.customMessage("Item successfully approved!");
                } else {
                    inventoryPresenter.customMessage("That number does not correspond to an item");
                }
            } else {
                inventoryPresenter.invalidInput();
            }
        }
    }
}
