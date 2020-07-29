package org.twelve.gateways;

import org.twelve.gateways.json.JsonGatewayPool;
import org.twelve.gateways.ram.InMemoryGatewayPool;

public class GatewayPoolFactory {

    public GatewayPool getGatewayPool(String implementation) {

        switch (implementation) {

            case "csv":

                /* might be getting deprecated for good
                try {
                    gatewayPool = new CSVGatewayPool();
                } catch (IOException e) {
                    return false;
                }
                */
                break;

            case "json":

                return new JsonGatewayPool();

            case "ram":

                return new InMemoryGatewayPool();
        }

        return null;
    }

}
