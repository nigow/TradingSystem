package org.twelve.controllers;

import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.InventoryPresenter;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with your inventory.
 *
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class InventoryController {

    /**
     * The presenter counterpart to this class
     */
    private InventoryPresenter inventoryPresenter;

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
    private final StatusManager statusManager;

    /**
     * An instance of ControllerHelper for helper methods
     */
    private final InputHandler inputHandler;

    private final ItemsGateway itemsGateway;

    /**
     * Constructor to initialize all the instances, from ManualConfig,
     * and add options to actions depending on the user's permissions
     *
     * @param useCasePool       the configuration for the program
     */
    public InventoryController(UseCasePool useCasePool, ItemsGateway itemsGateway) {
        this.itemManager = useCasePool.getItemManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.statusManager = useCasePool.getStatusManager(); //TODO: figure out how we're using permissions to dictate what the view shows the user
        this.inputHandler = new InputHandler();
        this.itemsGateway = itemsGateway;
    }

    /**
     * Calls the presenter to display a user's inventory
     */
    public void displayAllYourInventory() {

        itemsGateway.populate(itemManager);

        List<String> approvedItems = new ArrayList<>();

        for (int id : itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID())) {

            approvedItems.add(itemManager.getItemNameById(id));

        }

        List<String> pendingItems = new ArrayList<>();

        for (int id : itemManager.getDisprovedInventoryOfAccount(sessionManager.getCurrAccountID())) {

            pendingItems.add(itemManager.getItemNameById(id));

        }

        inventoryPresenter.setInventoryItems(approvedItems, pendingItems);
    }

    /**
     * Creates an item
     *
     * @param name The name of the item
     * @param description The description of the item
     */
    public boolean createItem(String name, String description) {

        if(inputHandler.isValidCSVStr(name) && inputHandler.isValidCSVStr(description)) {
            itemManager.createItem(name, description, sessionManager.getCurrAccountID());
            displayAllYourInventory();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an item from the inventory
     *
     * @param itemIndex The index in the list of items in this user's
     */
    public boolean removeFromInventory(int itemIndex) {

        boolean wasSuccessful;
        int approvedInvSize = itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).size();

        if (itemIndex < approvedInvSize) {

            wasSuccessful = itemManager.removeItem(itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemIndex));


        } else {

            wasSuccessful = itemManager.removeItem(itemManager.getDisprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemIndex - approvedInvSize));

        }

        if (wasSuccessful) displayAllYourInventory();
        return wasSuccessful;

    }

    public void setInventoryPresenter(InventoryPresenter inventoryPresenter) {
        this.inventoryPresenter = inventoryPresenter;
    }

    public void setSelectedItem(int itemIndex) {

        int approvedInvSize = itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).size();
        int itemId;

        if (itemIndex < approvedInvSize) {

            itemId = itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemIndex);

        } else {

            itemId = itemManager.getDisprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemIndex - approvedInvSize);

        }

        inventoryPresenter.setSelectedItemName(itemManager.getItemNameById(itemId));
        inventoryPresenter.setSelectedItemDesc(itemManager.getItemDescById(itemId));

    }
}
