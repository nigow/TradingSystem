package org.twelve.gateways;


public interface GatewayPool {
    AccountGateway getAccountGateway();

    ItemsGateway getItemsGateway();

    ThresholdsGateway getThresholdsGateway();

    TradeGateway getTradeGateway();
}
