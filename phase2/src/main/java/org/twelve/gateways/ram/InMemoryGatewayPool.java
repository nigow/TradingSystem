package org.twelve.gateways.ram;

import org.twelve.entities.Thresholds;
import org.twelve.gateways.*;

import java.util.HashMap;

public class InMemoryGatewayPool implements GatewayPool {

    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;
    private final ThresholdsGateway thresholdsGateway;
    private final TradeGateway tradeGateway;

    // maybe i'll look into factory pattern for this
    public InMemoryGatewayPool() {

        accountGateway = new InMemoryAccountGateway(new HashMap<>());
        itemsGateway = new InMemoryItemGateway(new HashMap<>());
        thresholdsGateway = new InMemoryThresholdsGateway(new Thresholds(1, 5, 10, 30, 3, 6));
        tradeGateway = new InMemoryTradeGateway(new HashMap<>(), new HashMap<>());

    }

    public AccountGateway getAccountGateway() {
        return accountGateway;
    }

    public ItemsGateway getItemsGateway() {
        return itemsGateway;
    }

    public ThresholdsGateway getThresholdsGateway() {
        return thresholdsGateway;
    }

    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }
}
