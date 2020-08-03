package org.twelve.views;

import org.twelve.controllers.ControllerPool;
import org.twelve.entities.Thresholds;
import org.twelve.gateways.GatewayPoolFactory;
import org.twelve.gateways.GatewayPool;
import org.twelve.presenters.ThresholdPresenter;
import org.twelve.presenters.ui.*;

import java.util.List;
import java.util.ResourceBundle;

public class ViewBuilder {

    private final WindowHandler windowHandler;
    private GatewayPool gatewayPool;
    private ControllerPool controllerPool;

    public ViewBuilder(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    public boolean buildGateways(String implementation) {

        gatewayPool = new GatewayPoolFactory().getGatewayPool(implementation);
        return gatewayPool != null;

    }

    public void buildControllers() {

        controllerPool = new ControllerPool(gatewayPool);

    }

    public SceneView getView(Scenes scene, ResourceBundle localizedResources) {

        switch (scene) {

            case LANDING:

                return new LandingView(windowHandler);

            case LOGIN:

                return new LoginView(windowHandler, controllerPool.getLoginController());

            case MENU:

                return new MenuView(windowHandler, controllerPool.getMenuController());

            case PROFILE:

                return new ProfileView<>(windowHandler, controllerPool.getProfileController(),
                        new UIProfilePresenter());

            case RESTRICTIONS:

                return new RestrictionsView<>(windowHandler, controllerPool.getThresholdController(),
                        new UIThresholdsPresenter());

            case TRADE_CREATOR:

                //Tairi: I edited this
                return new TradeCreatorView<>(windowHandler, controllerPool.getTradeCreatorController(),
                        new UITradeCreatorPresenter(localizedResources));

            case WAREHOUSE:

                return new WarehouseView<>(windowHandler, controllerPool.getWarehouseController(),
                        new UIWarehousePresenter(localizedResources));

            case REGISTRATION:

                return new RegistrationView(windowHandler, controllerPool.getRegistrationController());

            case WISHLIST:

                return new WishlistView<>(windowHandler, controllerPool.getWishlistController(),
                        new UIWishlistPresenter(localizedResources));

            case INVENTORY:

                return new InventoryView<>(windowHandler, controllerPool.getInventoryController(),
                        new UIInventoryPresenter());

            case ACCOUNTS:

                return new AccountsView<>(windowHandler, controllerPool.getFreezingController(),
                        new UIFreezingPresenter(localizedResources));

            case TRADE_COLLECTION:

                return new TradeCollectionView<>(windowHandler, controllerPool.getTradeCollectionController(),
                        new UITradeCollectionPresenter(localizedResources));
        }

        return null;
    }

}
