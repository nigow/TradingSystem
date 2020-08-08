package org.twelve.gateways.ram;

import org.twelve.entities.Thresholds;
import org.twelve.gateways.*;

import java.util.HashMap;

/**
 * Pool of in-memory gateways
 */
public class InMemoryGatewayPool implements GatewayPool {

    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;
    private final ThresholdsGateway thresholdsGateway;
    private final TradeGateway tradeGateway;
    private final CitiesGateway citiesGateway;

    // TODO: maybe i'll look into factory pattern for this
    /**
     * Initialize all in-memory gateways
     */
    public InMemoryGatewayPool() {

        accountGateway = new InMemoryAccountGateway(new HashMap<>());
        itemsGateway = new InMemoryItemGateway(new HashMap<>());
        thresholdsGateway = new InMemoryThresholdsGateway(new Thresholds(1, 5, 10, 30, 3, 6, 10));
        tradeGateway = new InMemoryTradeGateway(new HashMap<>(), new HashMap<>());
        citiesGateway = new InMemoryCitiesGateway(new HashMap<>());

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
    public CitiesGateway getCitiesGateway() { return citiesGateway; }
}
