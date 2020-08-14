package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.WarehousePresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing the warehouse (all items in the system).
 */
public class WarehouseController {

    private final ItemManager itemManager;
    private WarehousePresenter warehousePresenter;
    private final ItemsGateway itemsGateway;
    private final UseCasePool useCasePool;

    /**
     * Constructor of controller for managing the warehouse.
     * @param useCasePool An instance of {@link org.twelve.usecases.UseCasePool}.
     * @param gatewayPool An instance of {@link org.twelve.gateways.GatewayPool}.
     */
    public WarehouseController(UseCasePool useCasePool, GatewayPool gatewayPool) {

        itemManager = useCasePool.getItemManager();
        this.itemsGateway = gatewayPool.getItemsGateway();
        this.useCasePool = useCasePool;


    }

    /**
     * Provides the warehouse controller with an appropriate presenter.
     * @param warehousePresenter An instance of a class that implements {@link org.twelve.presenters.WarehousePresenter}.
     */
    public void setWarehousePresenter(WarehousePresenter warehousePresenter) {
        this.warehousePresenter = warehousePresenter;
    }

    /**
     * Approves the item at itemIndex.
     * @param itemIndex Index of item to approve.
     */
    public void approveItem(int itemIndex) {

        if (itemIndex < itemManager.getDisapprovedIDs().size())
            itemManager.updateApproval(itemManager.getDisapprovedIDs().get(itemIndex), true);

        updateWarehouseItems();

    }

    /**
     * Removes the item at itemIndex.
     * @param itemIndex Index of item to remove.
     */
    public void denyItem(int itemIndex) {

        if (itemIndex < itemManager.getDisapprovedIDs().size()) {

            itemManager.removeItem(itemManager.getDisapprovedIDs().get(itemIndex));

        } else {

            itemManager.removeItem(itemManager.getApprovedIDs().get(itemIndex - itemManager.getDisapprovedIDs().size()));

        }

        updateWarehouseItems();

    }

    /**
     * Changes which item the user has selected and updates presenter with new name & desc.
     * @param itemIndex Index of selected item.
     */
    public void changeSelectedItem(int itemIndex) {

        int itemId;

        if (itemIndex < itemManager.getDisapprovedIDs().size()) {

            itemId = itemManager.getDisapprovedIDs().get(itemIndex);

        } else {

            itemId = itemManager.getApprovedIDs().get(itemIndex - itemManager.getDisapprovedIDs().size());

        }

        warehousePresenter.setSelectedItemName(itemManager.getItemNameById(itemId));
        warehousePresenter.setSelectedItemDesc(itemManager.getItemDescById(itemId));

    }

    /**
     * Updates items in the warehouse.
     */
    public void updateWarehouseItems() {

        useCasePool.populateAll();

        List<String> pendingItems = new ArrayList<>();
        List<String> approvedItems = new ArrayList<>();

        for (int id : itemManager.getDisapprovedIDs()) {

            pendingItems.add(itemManager.getItemNameById(id));

        }

        for (int id : itemManager.getApprovedIDs()) {

            approvedItems.add(itemManager.getItemNameById(id));

        }

        warehousePresenter.setPendingItems(pendingItems);
        warehousePresenter.setApprovedItems(approvedItems);

    }
}
