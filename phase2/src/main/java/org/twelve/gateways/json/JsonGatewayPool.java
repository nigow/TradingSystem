package org.twelve.gateways.json;

import org.twelve.gateways.*;
import org.twelve.gateways.ThresholdsGateway;

public class JsonGatewayPool implements GatewayPool {

    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;
    private final ThresholdsGateway thresholdsGateway;
    private final TradeGateway tradeGateway;
    private final CitiesGateway citiesGateway;

    public JsonGatewayPool() {
        accountGateway = new JsonAccountGateway();
        itemsGateway = new JsonItemsGateway();
        thresholdsGateway = new JsonThresholdsGateway();
        tradeGateway = new JsonTradeGateway();
        citiesGateway = new JsonCitiesGateway();
    }

    @Override
    public AccountGateway getAccountGateway() {
        return accountGateway;
    }

    @Override
    public ItemsGateway getItemsGateway() {
        return itemsGateway;
    }

    @Override
    public ThresholdsGateway getThresholdsGateway() {
        return thresholdsGateway;
    }

    @Override
    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }

    @Override
    public CitiesGateway getCitiesGateway() { return citiesGateway; }
}
