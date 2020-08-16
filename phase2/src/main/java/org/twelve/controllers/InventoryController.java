package org.twelve.controllers;

import org.twelve.presenters.InventoryPresenter;
import org.twelve.usecases.item.ItemManager;
import org.twelve.usecases.system.SessionManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with your inventory.
 */
public class InventoryController {

    private InventoryPresenter inventoryPresenter;

    private final ItemManager itemManager;

    private final SessionManager sessionManager;

    private final UseCasePool useCasePool;

    /**
     * Constructor to initialize all the instances, from ManualConfig,
     * and add options to actions depending on the user's permissions
     *
     * @param useCasePool the configuration for the program
     */
    public InventoryController(UseCasePool useCasePool) {
        this.itemManager = useCasePool.getItemManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Calls the presenter to display a user's inventory
     */
    public void displayAllYourInventory() {

        useCasePool.populateAll();

        List<String> approvedItems = new ArrayList<>();

        for (int id : itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID())) {

            approvedItems.add(itemManager.getItemNameById(id));

        }

        List<String> pendingItems = new ArrayList<>();

        for (int id : itemManager.getDisprovedInventoryOfAccount(sessionManager.getCurrAccountID())) {

            pendingItems.add(itemManager.getItemNameById(id));

        }

        inventoryPresenter.setApprovedItems(approvedItems);
        inventoryPresenter.setPendingItems(pendingItems);
    }

    /**
     * Creates an item
     *
     * @param name        The name of the item
     * @param description The description of the item
     */
    public void createItem(String name, String description) {

        useCasePool.populateAll();
        itemManager.createItem(name, description, sessionManager.getCurrAccountID());
        displayAllYourInventory();

    }

    /**
     * Removes an item from the inventory.
     *
     * @param itemIndex The index in the list of items in this user's
     */
    public void removeFromInventory(int itemIndex) {

        int approvedInvSize = itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).size();

        if (itemIndex < approvedInvSize) {

            itemManager.removeItem(itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemIndex));

        } else {

            itemManager.removeItem(itemManager.getDisprovedInventoryOfAccount(sessionManager.getCurrAccountID()).get(itemIndex - approvedInvSize));

        }

        displayAllYourInventory();

    }

    /**
     * Provides the inventory controller with an appropriate presenter.
     *
     * @param inventoryPresenter An instance of a class that implements {@link org.twelve.presenters.InventoryPresenter}.
     */
    public void setInventoryPresenter(InventoryPresenter inventoryPresenter) {
        this.inventoryPresenter = inventoryPresenter;
    }

    /**
     * Changes which item the user has selected and updates presenter with new name and description.
     *
     * @param itemIndex Index of selected item.
     */
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
