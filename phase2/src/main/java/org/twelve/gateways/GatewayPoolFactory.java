package org.twelve.gateways;

import org.twelve.gateways.json.JsonGatewayPool;
import org.twelve.gateways.ram.InMemoryGatewayPool;

/**
 * Factory class that decides implementation based on input
 */
public class GatewayPoolFactory {

    /**
     * Decide implementation
     * @param implementation "json" or "ram" which represents a type of implementation
     * @return a desired pool of gateways
     */
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
