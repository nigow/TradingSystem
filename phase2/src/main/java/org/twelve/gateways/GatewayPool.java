package org.twelve.gateways;


public interface GatewayPool {
    AccountGateway getAccountGateway();

    ItemsGateway getItemsGateway();

    RestrictionsGateway getRestrictionsGateway();

    TradeGateway getTradeGateway();
}
