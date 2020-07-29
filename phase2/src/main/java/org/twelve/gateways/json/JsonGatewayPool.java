package org.twelve.gateways.json;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ItemsGateway;
import org.twelve.gateways.ThreshholdsGateway;
import org.twelve.gateways.TradeGateway;

public class JsonGatewayPool implements GatewayPool {

    private AccountGateway accountGateway;
    private ItemsGateway itemsGateway;
    private ThreshholdsGateway thresholdsGateway;
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
    public ThreshholdsGateway getThresholdsGateway() {
        return thresholdsGateway;
    }

    @Override
    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }
}
