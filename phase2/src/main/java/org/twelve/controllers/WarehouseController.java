package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.WarehousePresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

public class WarehouseController {

    private final ItemManager itemManager;
    private WarehousePresenter warehousePresenter;
    private final ItemsGateway itemsGateway;

    public WarehouseController(UseCasePool useCasePool, GatewayPool gatewayPool) {

        itemManager = useCasePool.getItemManager();
        this.itemsGateway = gatewayPool.getItemsGateway();

    }

    public void setWarehousePresenter(WarehousePresenter warehousePresenter) {
        this.warehousePresenter = warehousePresenter;
    }

    public void approveItem(int itemIndex) {

        if (itemIndex < itemManager.getDisapprovedIDs().size())
            itemManager.updateApproval(itemManager.getDisapprovedIDs().get(itemIndex), true);

        updateWarehouseItems();

    }

    public void denyItem(int itemIndex) {

        if (itemIndex < itemManager.getDisapprovedIDs().size()) {

            itemManager.removeItem(itemManager.getDisapprovedIDs().get(itemIndex));

        } else {

            itemManager.removeItem(itemManager.getApprovedIDs().get(itemIndex - itemManager.getDisapprovedIDs().size()));

        }

        updateWarehouseItems();

    }

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

    public void updateWarehouseItems() {

        itemsGateway.populate(itemManager);

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
