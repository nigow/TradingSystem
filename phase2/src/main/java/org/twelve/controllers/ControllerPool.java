package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.presenters.experimental.ItemApprovalPresenter;
import org.twelve.presenters.experimental.YourInventoryPresenter;
import org.twelve.usecases.UseCasePool;

public class ControllerPool {

    private ItemApprovalController itemApprovalController;
    private LoginController loginController;
    private YourInventoryController yourInventoryController;

    public ControllerPool(GatewayPool gatewayPool) {

        UseCasePool useCasePool = new UseCasePool(gatewayPool);

        // todo: add refactored controllers and their getters

        itemApprovalController = new ItemApprovalController(useCasePool, new ItemApprovalPresenter());

        yourInventoryController = new YourInventoryController(useCasePool, new YourInventoryPresenter());
    }

}
