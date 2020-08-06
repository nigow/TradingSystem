package org.twelve.gateways;

import org.twelve.gateways.json.JsonGatewayPool;
import org.twelve.gateways.ram.InMemoryGatewayPool;

public class GatewayPoolFactory {

    public GatewayPool getGatewayPool(String implementation) {

        switch (implementation) {

            case "json":

                return new JsonGatewayPool();

            case "ram":

                return new InMemoryGatewayPool();
        }

        return null;
    }

}
