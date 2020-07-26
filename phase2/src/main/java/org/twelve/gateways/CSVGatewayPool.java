package org.twelve.gateways;

import java.io.IOException;

public class CSVGatewayPool implements GatewayPool {
    private final String filePath;
    private AccountGateway accountGateway;
    private TradeGateway tradeGateway;
    private RestrictionsGateway restrictionsGateway;
    private ItemsGateway itemsGateway;


    public CSVGatewayPool() throws IOException {
        this.filePath = System.getProperty("user.dir") + "/out/files/";
        buildAccountGateway();
        buildTradeGateway();
        buildRestrictionsGateway();
        buildItemsGateway();
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
    public RestrictionsGateway getRestrictionsGateway() {
        return restrictionsGateway;
    }

    @Override
    public TradeGateway getTradeGateway() {
        return tradeGateway;
    }

    private void buildAccountGateway() throws IOException {
        accountGateway = new CSVAccountGateway(filePath + "accounts.csv");
    }

    private void buildTradeGateway() throws IOException {
        tradeGateway = new CSVTradeGateway(filePath + "accounts.csv");
    }

    private void buildRestrictionsGateway() throws IOException {
        restrictionsGateway = new CSVRestrictionsGateway(filePath + "restrictions.csv");
    }

    private void buildItemsGateway() throws IOException {
        itemsGateway = new CSVItemsGateway(filePath + "items.csv");
    }

}
