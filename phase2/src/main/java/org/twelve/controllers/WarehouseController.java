package org.twelve.controllers;

import org.twelve.presenters.WarehousePresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

public class WarehouseController {

    private final ItemManager itemManager;
    private WarehousePresenter warehousePresenter;

    public WarehouseController(UseCasePool useCasePool) {

        itemManager = useCasePool.getItemManager();

    }

    public void setWarehousePresenter(WarehousePresenter warehousePresenter) {
        this.warehousePresenter = warehousePresenter;
    }

    public void approveItem(int itemIndex) {

        itemManager.updateApproval(itemManager.getDisapprovedIDs().get(itemIndex), true);

    }

    public void changeSelectedItem(int itemIndex) {

        String name = itemManager.getItemNameById(itemManager.getDisapprovedIDs().get(itemIndex));
        String desc = itemManager.getItemDescById(itemManager.getDisapprovedIDs().get(itemIndex));

        warehousePresenter.setSelectedItemName(name);
        warehousePresenter.setSelectedItemDesc(desc);

    }

    public void updatePendingItems() {

        List<String> pendingItems = new ArrayList<>();

        for (int id : itemManager.getDisapprovedIDs()) {

            pendingItems.add(itemManager.getItemNameById(id));

        }

        warehousePresenter.setPendingItems(pendingItems);

    }
}
