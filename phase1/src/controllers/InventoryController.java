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

    private InventoryPresenter inventoryPresenter;
    private ItemManager itemManager;
    private AuthManager authManager;
    private AccountManager accountManager;
    private ItemUtility itemUtility;
    private Map<String, Runnable> actions;

    public InventoryController(ManualConfig manualConfig) {
        this.itemManager = manualConfig.getItemManager();
        this.accountManager = manualConfig.getAccountManager();
        this.authManager = manualConfig.getAuthManager();
        this.inventoryPresenter = new ConsoleInventoryPresenter();
        this.itemUtility = new ItemUtility(this.itemManager);

        this.actions = new LinkedHashMap<>();
        if (authManager.canAddToWishlist(accountManager.getCurrAccount())) {
            actions.put("Add to wishlist", this::addToWishlist);
        }
        actions.put("Remove your item from inventory", this::removeFromInventory);
        if (authManager.canConfirmItem(accountManager.getCurrAccount())) {
            actions.put("View items awaiting approval", this::displayPendingItems);
        }
        actions.put("Return to main menu", () -> {});
    }

    private boolean isNum(String input) {
        return Pattern.matches("^[0-9]+$", input);
    }

    public void run() {

        String option;
        List<String> menu = new ArrayList<>(actions.keySet());

        do {
            displayInventory();
            option = inventoryPresenter.displayInventoryOptions(menu);
            if (isNum(option)) {
                int action = Integer.parseInt(option);
                if (action < actions.size()) {
                    actions.values().toArray(new Runnable[0])[action].run();
                } else {
                    inventoryPresenter.invalidInput("That number does not correspond to an item");
                }
            }
        } while(!option.equals(String.valueOf(menu.size() - 1)));

    }

    public void displayInventory() {
        List<String> allItems = itemManager.getAllItemsString();
        this.inventoryPresenter.displayInventory(allItems);
    }

    public void addToWishlist() {
        String option = inventoryPresenter.addToWishlist();
        if (isNum(option)) {
            int ind = Integer.parseInt(option);
            if (ind < itemManager.getAllItems().size()) {
                accountManager.addItemToWishlist(itemManager.getAllItems().get(ind).getItemID());
            } else {
                inventoryPresenter.invalidInput("That number does not correspond to an item");
            }
        } else {
            inventoryPresenter.invalidInput();
        }
    }

    public void removeFromInventory() throws NumberFormatException, IndexOutOfBoundsException {
        String option = inventoryPresenter.removeFromInventory();
        if (isNum(option)) {
            int ind = Integer.parseInt(option);
            if (ind < itemManager.getAllItems().size()) {
                if (itemManager.getAllItems().get(ind).getOwnerID() == accountManager.getCurrAccount().getAccountID()) {
                    itemManager.removeItem(itemManager.getAllItems().get(ind).getItemID());
                } else {
                    inventoryPresenter.invalidInput("You cannot remove an item that does not belong to you");
                }
            } else {
                inventoryPresenter.invalidInput("That number does not correspond to an item");
            }
        } else {
            inventoryPresenter.invalidInput();
        }
    }

    public void displayPendingItems() {
        List<String> all_disapproved = itemUtility.getDisapprovedString();
        inventoryPresenter.displayPending(all_disapproved);
        String option = inventoryPresenter.approveItem();
        if (isNum(option)) {
            int ind = Integer.parseInt(option);
            if (ind < all_disapproved.size()) {
                itemManager.setItem(itemManager.getAllItems().get(ind));
                itemManager.updateApproval(true);
            } else {
                inventoryPresenter.invalidInput("That number does not correspond to an item");
            }
        } else {
            inventoryPresenter.invalidInput();
        }
    }


}
