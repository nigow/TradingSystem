package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.ItemsGateway;
import org.twelve.presenters.WarehousePresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

public class WarehouseController {

    private final ItemManager itemManager;
    private final SessionManager sessionManager;
    private WarehousePresenter warehousePresenter;
    private final ItemsGateway itemsGateway;

    public WarehouseController(UseCasePool useCasePool, GatewayPool gatewayPool) {

        itemManager = useCasePool.getItemManager();
        sessionManager = useCasePool.getSessionManager();
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

        String name;
        String desc;

        if (itemIndex < 0) {

            name = desc = "";

        } else if (itemIndex < itemManager.getDisapprovedIDs().size()) {

            name = itemManager.getItemNameById(itemManager.getDisapprovedIDs().get(itemIndex));
            desc = itemManager.getItemDescById(itemManager.getDisapprovedIDs().get(itemIndex));

        } else {

            name = itemManager.getItemNameById(itemManager.getApprovedIDs().get(itemIndex - itemManager.getDisapprovedIDs().size()));
            desc = itemManager.getItemDescById(itemManager.getApprovedIDs().get(itemIndex - itemManager.getDisapprovedIDs().size()));

        }

        warehousePresenter.setSelectedItemName(name);
        warehousePresenter.setSelectedItemDesc(desc);

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
