package org.twelve.views;

import org.twelve.controllers.ControllerPool;
import org.twelve.gateways.csv.CSVGatewayPool;
import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.json.JsonGatewayPool;

import java.io.IOException;

public class ViewBuilder {

    private WindowHandler windowHandler;

    private GatewayPool gatewayPool;
    private ControllerPool controllerPool;

    public ViewBuilder(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    public boolean buildGateways(String implementation) {

        switch (implementation) {

            case "csv":

                try {
                    gatewayPool = new CSVGatewayPool();
                } catch (IOException e) {
                    return false;
                }

                break;

            case "json":

                gatewayPool = new JsonGatewayPool();
                break;

        }

        return true;
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

                return new MenuView(windowHandler);

            case PROFILE:

                return new ProfileView(windowHandler);

            case RESTRICTIONS:

                return new RestrictionsView(windowHandler);

            case TRADE_CREATOR:

                return new TradeCreatorView(windowHandler);

            case WAREHOUSE:

                return new WarehouseView(windowHandler);
        }

        return null;
    }

}
