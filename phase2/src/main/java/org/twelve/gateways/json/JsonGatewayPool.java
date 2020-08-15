package org.twelve.gateways.json;

import org.twelve.gateways.*;

/**
 * Pool of JSON gateways
 */
public class JsonGatewayPool implements GatewayPool {

    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;
    private final ThresholdsGateway thresholdsGateway;
    private final TradeGateway tradeGateway;
    private final CitiesGateway citiesGateway;

    /**
     * Initialize the JSON gateways
     */
    public JsonGatewayPool() {
        accountGateway = new JsonAccountGateway();
        itemsGateway = new JsonItemsGateway();
        thresholdsGateway = new JsonThresholdsGateway();
        tradeGateway = new JsonTradeGateway();
        citiesGateway = new JsonCitiesGateway();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountGateway getAccountGateway() {
        return accountGateway;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemsGateway getItemsGateway() {
        return itemsGateway;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThresholdsGateway getThresholdsGateway() {
        return thresholdsGateway;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CitiesGateway getCitiesGateway() {
        return citiesGateway;
    }
}
