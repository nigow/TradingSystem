package org.twelve.gateways.json;

import org.twelve.gateways.*;
import org.twelve.gateways.ThresholdsGateway;

public class JsonGatewayPool implements GatewayPool {

    private AccountGateway accountGateway;
    private ItemsGateway itemsGateway;
    private ThresholdsGateway thresholdsGateway;
    private TradeGateway tradeGateway;

    public JsonGatewayPool() {
        accountGateway = new JsonAccountGateway();
        itemsGateway = new JsonItemsGateway();
        thresholdsGateway = new JsonThresholdsGateway();
        tradeGateway = new JsonTradeGateway();
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
}