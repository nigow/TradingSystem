package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import gateways.ManualConfig;
import presenters.InventoryPresenter;
import presenters.ConsoleInventoryPresenter;
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
     * Constructor to initialize all the instances, from ManualConfig,
     * and add options to actions depending on the user's permissions
     * @param manualConfig the configuration for the program
     */
    public InventoryController(ManualConfig manualConfig, InventoryPresenter inventoryPresenter) {
        this.itemManager = manualConfig.getItemManager();
        this.accountManager = manualConfig.getAccountManager();
        this.itemUtility = manualConfig.getItemUtility();

        this.inventoryPresenter = inventoryPresenter;

        this.authManager = manualConfig.getAuthManager();
    }

    /**
     * Helper function to tell if a String is only numeric
     * @param input the string to check
     * @return true if String is numeric, false otherwise
     */
    private boolean isNum(String input) {
        return Pattern.matches("^[0-9]+$", input);
    }


    /**
     * Runs the main menu of the program
     */
    public void run() {

        String option;
        Map<String, Runnable> actions = new LinkedHashMap<>();

        actions.put("View all items", this::displayFullInventory);
        actions.put("View your items", this::displayYourInventory);
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
            displayFullInventory();
            option = inventoryPresenter.displayInventoryOptions(menu);

            if (isNum(option)) {
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
    public void displayFullInventory() {
        this.inventoryPresenter.customMessage("All Items:");
        List<String> allItems = itemManager.getAllItemsString();
        this.inventoryPresenter.displayInventory(allItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items belonging to the user
     */
    public void displayYourInventory() {
        this.inventoryPresenter.customMessage("Your items:");
        List<String> allYourItems = itemUtility.getInventoryOfAccountString(accountManager.getCurrAccount().getAccountID());
        this.inventoryPresenter.displayInventory(allYourItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items except for the ones belonging to the user
     */
    public void displayOthersInventory() {
        this.inventoryPresenter.customMessage("Items available for trading: ");
        List<String> othersItems = itemUtility.getNotInAccountString(accountManager.getCurrAccount().getAccountID());
        this.inventoryPresenter.displayInventory(othersItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all the items awaiting approval
     */
    public void displayPending() {
        inventoryPresenter.customMessage("Items awaiting approval:");
        List<String> all_disapproved = itemUtility.getDisapprovedString();
        inventoryPresenter.displayInventory(all_disapproved);
    }
    /**
     * Runs create item in inventoryPresenter, calls itemManager to add an item to our inventory
     */
    public void createItem() {
        List<String> inputs = inventoryPresenter.createItem();
        if (inputs.get(2).equals("yes")) {
            if (inputs.get(0).contains(",") || inputs.get(1).contains(",")) {
                inventoryPresenter.customMessage("Please do not include commas in your name or description. Item was not added");
            } else {
                itemManager.createItem(inputs.get(0), inputs.get(1), accountManager.getCurrAccount().getAccountID());
                inventoryPresenter.customMessage("Item added!");
            }
        } else {
            inventoryPresenter.customMessage("Item was not added.");
        }

    }

    /**
     * Runs the addToWishlist method in InventoryPresenter,
     * and uses accountManager and itemManager to actually add the user's chosen item to their wishlist
     */
    public void addToWishlist() {
        displayOthersInventory();
        String option = inventoryPresenter.addToWishlist();
        if (isNum(option)) {
            int ind = Integer.parseInt(option);

            if (ind < itemUtility.getNotInAccount(accountManager.getCurrAccount().getAccountID()).size()) {
                accountManager.addItemToWishlist(itemUtility.getNotInAccount(accountManager.getCurrAccount().getAccountID()).get(ind).getItemID());
                inventoryPresenter.customMessage("Item successfully added to your wishlist!");
            } else {
                inventoryPresenter.customMessage("That number does not correspond to an item");
            }

        } else {
            inventoryPresenter.invalidInput();
        }
    }

    /**
     * Runs the removeFromInventory method in InventoryPresenter, checks if a user actually owns the item they want to
     * remove, and if so, uses accountManager and itemManager to remove the item from the inventory
     */
    public void removeFromInventory() {
        displayYourInventory();
        String option = inventoryPresenter.removeFromInventory();
        if (isNum(option)) {
            int ind = Integer.parseInt(option);

            if (ind < itemUtility.getInventoryOfAccount(accountManager.getCurrAccount().getAccountID()).size()) {
                itemManager.removeItem(itemUtility.getInventoryOfAccount(accountManager.getCurrAccount().getAccountID()).get(ind));
            } else {
                inventoryPresenter.customMessage("That number does not correspond to an item");
            }

        } else {
            inventoryPresenter.invalidInput();
        }
    }

    /**
     * Runs the displayPendingItems and approveItem method in InventoryPresenter, uses itemManager to approve an item
     */
    public void approveItems() {

        displayPending();
        String option = inventoryPresenter.approveItem();

        if (isNum(option)) {
            int ind = Integer.parseInt(option);

            if (ind < itemUtility.getDisapprovedString().size()) {
                itemManager.updateApproval(itemUtility.getDisapproved().get(ind), true);

            } else {
                inventoryPresenter.customMessage("That number does not correspond to an item");

            }
        } else {
            inventoryPresenter.invalidInput();
        }
    }


}
