package org.twelve.views;

import org.twelve.controllers.ControllerPool;
import org.twelve.gateways.csv.CSVGatewayPool;
import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.json.JsonGatewayPool;

import java.io.IOException;

public class ViewBuilder {

    private GatewayPool gatewayPool;
    private ControllerPool controllerPool;

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



        }

    }

}
