package org.twelve.gateways;


import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ItemsGateway;
import org.twelve.gateways.ThresholdsGateway;
import org.twelve.gateways.TradeGateway;

public interface GatewayPool {
    AccountGateway getAccountGateway();

    ItemsGateway getItemsGateway();

    ThresholdsGateway getThresholdsGateway();

    TradeGateway getTradeGateway();
}
