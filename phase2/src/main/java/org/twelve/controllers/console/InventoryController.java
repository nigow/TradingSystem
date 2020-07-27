package org.twelve.controllers.console;

import org.twelve.controllers.InputHandler;
import org.twelve.presenters.InventoryPresenter;
import org.twelve.usecases.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;



/**
 * Controller that deals with the inventory.
 *
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
     * An instance of PermissionManager to check permissions
     */
    private final PermissionManager permissionManager;

    private final SessionManager sessionManager;

    private final WishlistManager wishlistManager;

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
    public InventoryController(UseCasePool useCasePool, InventoryPresenter inventoryPresenter) {
        this.itemManager = useCasePool.getItemManager();
        this.inventoryPresenter = inventoryPresenter;
        this.permissionManager = useCasePool.getPermissionManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.wishlistManager = useCasePool.getWishlistManager();
        this.inputHandler = new InputHandler();
    }


    /**
     * Runs the main menu of the program
     */
    public void run() {
        String option;

        Map<String, Runnable> actions = new LinkedHashMap<>();
        actions.put(inventoryPresenter.viewAllApproved(), this::displayFullInventory);
        actions.put(inventoryPresenter.viewYourApproved(), this::displayYourInventory);
        actions.put(inventoryPresenter.viewYourPending(), this::displayYourPending);
        actions.put(inventoryPresenter.viewAllAvailable(), this::displayOthersInventory);
        if (permissionManager.canAddToWishlist(sessionManager.getCurrAccountID())) {
            actions.put(inventoryPresenter.addItemToWishlist(), this::addToWishlist);
        }
        if (permissionManager.canCreateItem(sessionManager.getCurrAccountID())) {
            actions.put(inventoryPresenter.createNewItem(), this::createItem);
        }
        actions.put(inventoryPresenter.removeYourItem(), this::removeFromYourInventory);
        if (permissionManager.canConfirmItem(sessionManager.getCurrAccountID())) {
            actions.put(inventoryPresenter.viewAllPending(), this::displayPending);
            actions.put(inventoryPresenter.approveAnItem(), this::approveItems);
        }
        actions.put(inventoryPresenter.returnToHome(), () -> {
        });

        List<String> menu = new ArrayList<>(actions.keySet());

        do {
            option = inventoryPresenter.displayInventoryOptions(menu);

            if (inputHandler.isNum(option)) {
                int action = Integer.parseInt(option);

                if (action < actions.size()) {
                    actions.values().toArray(new Runnable[0])[action].run();

                } else {
                    inventoryPresenter.displayDoesNotCorrespond();
                }
            } else {
                inventoryPresenter.invalidInput();
            }

        } while (!option.equals(String.valueOf(menu.size() - 1)));

    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all the items
     */
    private void displayFullInventory() {
        List<String> allItems = itemManager.getApprovedString();
        this.inventoryPresenter.displayAllItems(allItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items belonging to the user
     */
    private void displayYourInventory() {
        List<String> allYourItems = itemManager.getApprovedInventoryOfAccountString(sessionManager.getCurrAccountID());
        this.inventoryPresenter.displayApprovedItems(allYourItems);
    }

    /**
     *Runs the displayInventory method in InventoryPresenter, passing all items
     */
    private void displayAllYourInventory() {
        List<String> allYourItems = itemManager.getAllInventoryOfAccountString(sessionManager.getCurrAccountID());
        this.inventoryPresenter.displayUserPendingItems(allYourItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items belonging to the user
     */
    private void displayYourPending() {
        List<String> allYourItems = itemManager.getDisprovedInventoryOfAccountString(sessionManager.getCurrAccountID());
        this.inventoryPresenter.displayUserPendingItems(allYourItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all items except for the ones belonging to the user
     */
    private void displayOthersInventory() {
        List<String> othersItems = itemManager.getNotInAccountString(sessionManager.getCurrAccountID(),
                wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()));
        this.inventoryPresenter.displayOthersItems(othersItems);
    }

    /**
     * Runs the displayInventory method in InventoryPresenter, passing in all the items awaiting approval
     */
    private void displayPending() {
        List<String> all_disapproved = itemManager.getDisapprovedString();
        inventoryPresenter.displayAllPendingItems(all_disapproved);
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
                    inventoryPresenter.commaError();
                } else {
                    nameGiven = true;
                }
            } else if (!descriptionGiven) {
                description = inventoryPresenter.askDescription();
                if (inputHandler.isExitStr(description)) {
                    inventoryPresenter.abortMessage();
                    exit = true;
                } else if (!inputHandler.isValidCSVStr(description)) {
                    inventoryPresenter.commaError();
                } else {
                    descriptionGiven = true;
                }
            } else {
                String confirm = inventoryPresenter.confirmItem(name, description);
                if (inputHandler.isExitStr(confirm)) {
                    inventoryPresenter.abortMessage();
                    exit = true;
                } else if (inputHandler.isFalse(confirm)) {
                    inventoryPresenter.itemError();
                    nameGiven = false;
                    descriptionGiven = false;
                } else if (inputHandler.isTrue(confirm)) {
                    itemManager.createItem(name, description, sessionManager.getCurrAccountID());
                    inventoryPresenter.itemSuccess();
                    inventoryPresenter.pending();
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
    private void addToWishlist() {
        displayOthersInventory();
        boolean isValid = false;
        while (!isValid) {
            String option = inventoryPresenter.addToWishlist();
            if (inputHandler.isExitStr(option)) {
                inventoryPresenter.abortMessage();
                isValid = true;
            } else if (inputHandler.isNum(option)) {
                int ind = Integer.parseInt(option);
                List<Integer> notInAccount = itemManager.getNotInAccountIDs(sessionManager.getCurrAccountID(),
                        wishlistManager.getWishlistFromID(sessionManager.getCurrAccountID()));
                if (ind < notInAccount.size()) {

                    /* TODO andrew refactored this so it doesn't have a boolean output. i dont know how you want it
                        fixed.  --maryam

                    if (wishlistManager.addItemToWishlist(sessionManager.getCurrAccountID(), notInAccount.get(ind))) {
                        inventoryPresenter.itemSuccess();
                        isValid = true;
                    } else {
                        inventoryPresenter.itemError();
                    }

                    temp fix so i can test below, check this over when we refactor this

                    */

                    wishlistManager.addItemToWishlist(sessionManager.getCurrAccountID(), notInAccount.get(ind));
                    inventoryPresenter.itemSuccess();
                    isValid = true;

                } else {
                    inventoryPresenter.displayDoesNotCorrespond();
                }
            } else {
                inventoryPresenter.invalidInput();
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
//                if (ind < itemUtility.getApprovedInventoryOfAccount(accountManager.getCurrAccountID()).size()) {
//                    boolean removed = itemManager.removeItem(itemUtility.getApprovedInventoryOfAccount(accountManager.getCurrAccountID()).get(ind));
                List<Integer> items = itemManager.getAllInventoryOfAccountIDs(sessionManager.getCurrAccountID());
                if (ind < items.size()) {
                    boolean removed = itemManager.removeItem(items.get(ind));
                    if (removed) {
                        isValid = true;
                        inventoryPresenter.itemRemovalSuccess();
                    } else {
                        inventoryPresenter.itemError();
                    }
                } else {
                    inventoryPresenter.displayDoesNotCorrespond();
                }

            } else {
                inventoryPresenter.invalidInput();
            }
        }
    }

    /**
     * Runs the approve item submenu
     */
    private void approveItems() {
        displayPending();
        boolean isValid = false;
        while (!isValid) {
            String option = inventoryPresenter.approveItem();
            if (inputHandler.isExitStr(option)) {
                inventoryPresenter.abortMessage();
                isValid = true;
            } else if (inputHandler.isNum(option)) {
                int ind = Integer.parseInt(option);
                if (ind < itemManager.getDisapprovedString().size()) {
                    itemManager.updateApproval(itemManager.getDisapprovedIDs().get(ind), true);
                    isValid = true;
                    inventoryPresenter.approveItem();
                } else {
                    inventoryPresenter.displayDoesNotCorrespond();
                }
            } else {
                inventoryPresenter.invalidInput();
            }
        }
    }
}
