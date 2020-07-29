package org.twelve.gateways;


public interface GatewayPool {
    AccountGateway getAccountGateway();

    ItemsGateway getItemsGateway();

    ThreshholdsGateway getThresholdsGateway();

    TradeGateway getTradeGateway();
}
