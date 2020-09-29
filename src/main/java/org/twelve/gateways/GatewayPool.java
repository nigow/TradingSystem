package org.twelve.gateways;


/**
 * A pool of gateways which initializes gateways
 */
public interface GatewayPool {

    /**
     * Method that initializes an account gateway
     *
     * @return a desired account gateway
     */
    AccountGateway getAccountGateway();

    /**
     * Method that initializes an item gateway
     *
     * @return a desired item gateway
     */
    ItemsGateway getItemsGateway();

    /**
     * Method that initializes a thresholds gateway
     *
     * @return a desired thresholds gateway
     */
    ThresholdsGateway getThresholdsGateway();

    /**
     * Method that initializes a trade gateway
     *
     * @return a desired trade gateway
     */
    TradeGateway getTradeGateway();

    /**
     * Method that initializes a city gateway
     *
     * @return a desired city gateway
     */
    CitiesGateway getCitiesGateway();

}
