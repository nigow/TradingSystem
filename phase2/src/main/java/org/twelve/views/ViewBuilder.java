package org.twelve.views;

import org.twelve.controllers.ControllerPool;
import org.twelve.gateways.GatewayPoolFactory;
import org.twelve.gateways.GatewayPool;
import org.twelve.presenters.*;

import java.util.Locale;
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

    public SceneView getView(Scenes scene) {

        switch (scene) {

            case LANDING:

                return new LandingView(windowHandler);

            case LOGIN:

                return new LoginView(windowHandler, controllerPool.getLoginController());

            case MENU:

                return new MenuView(windowHandler, controllerPool.getMenuController());

            case PROFILE:

                return new ProfileView(windowHandler);

            case RESTRICTIONS:

                return new RestrictionsView(windowHandler);

            case TRADE_CREATOR:

                return new TradeCreatorView(windowHandler);

            case WAREHOUSE:

                return new WarehouseView(windowHandler, controllerPool.getWarehouseController());

            case REGISTRATION:

                return new RegistrationView(windowHandler, controllerPool.getRegistrationController());

            case WISHLIST:

                return new WishlistView(windowHandler, controllerPool.getWishlistController());
        }

        return null;
    }

}
