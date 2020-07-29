package org.twelve.controllers;

import org.twelve.presenters.WarehousePresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.UseCasePool;

public class WarehouseController {

    private final ItemManager itemManager;
    private final WarehousePresenter warehousePresenter;

    public WarehouseController(UseCasePool useCasePool, WarehousePresenter warehousePresenter) {

        itemManager = useCasePool.getItemManager();
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
}
