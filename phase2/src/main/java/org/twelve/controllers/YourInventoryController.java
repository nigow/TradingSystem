package org.twelve.controllers;

import org.twelve.controllers.console.InputHandler;
import org.twelve.presenters.experimental.YourInventoryPresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.PermissionManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

import java.util.List;



/**
 * Controller that deals with your inventory.
 *
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class YourInventoryController {

    /**
     * The presenter counterpart to this class
     */
    private final YourInventoryPresenter yourInventoryPresenter;

    /**
     * An instance of ItemManager to access items
     */
    private final ItemManager itemManager;

    /**
     * An instance of SessionManager to access accounts
     */
    private final SessionManager sessionManager;

    /**
     * An instance of PermissionManager to check permissions
     */
    private final PermissionManager permissionManager;

    /**
     * An instance of ControllerHelper for helper methods
     */
    private final InputHandler inputHandler;

    /**
     * Constructor to initialize all the instances, from ManualConfig,
     * and add options to actions depending on the user's permissions
     *
     * @param useCasePool       the configuration for the program
     * @param yourInventoryPresenter the presenter for displaying the inventory
     */
    public YourInventoryController(UseCasePool useCasePool, YourInventoryPresenter  yourInventoryPresenter) {
        this.itemManager = useCasePool.getItemManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.yourInventoryPresenter = yourInventoryPresenter;
        this.permissionManager = useCasePool.getPermissionManager(); //TODO: figure out how we're using permissions to dictate what the view shows the user
        this.inputHandler = new InputHandler();
    }

    /**
     * Calls the presenter to display a user's inventory
     */
    private void displayAllYourInventory() {
        List<String> allYourItems = itemManager.getAllInventoryOfAccountString(sessionManager.getCurrAccountID());
        this.yourInventoryPresenter.displayInventory(allYourItems);
    }

    /**
     * Creates an item
     *
     * @param name The name of the item
     * @param description The description of the item
     */
    private boolean createItem(String name, String description) {

        if(inputHandler.isValidCSVStr(name) && inputHandler.isValidCSVStr(description)) {
            itemManager.createItem(name, description, sessionManager.getCurrAccountID());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an item from the inventory
     *
     * @param ind The index in the list of items in this user's
     */
    private boolean removeFromYourInventory(String ind) {
        List<Integer> items = itemManager.getAllInventoryOfAccountIDs(sessionManager.getCurrAccountID());
        return itemManager.removeItem(items.get(Integer.parseInt(ind)));
    }

}
