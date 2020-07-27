package org.twelve.gateways.csv;

import org.twelve.gateways.csv.*;

public interface GatewayPool {
    AccountGateway getAccountGateway();

    ItemsGateway getItemsGateway();

    ThresholdsGateway getThresholdsGateway();

    TradeGateway getTradeGateway();
}
