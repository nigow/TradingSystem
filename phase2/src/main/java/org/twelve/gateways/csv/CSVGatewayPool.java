package org.twelve.gateways.csv;

import org.twelve.gateways.csv.*;

import java.io.IOException;

public class CSVGatewayPool implements GatewayPool {
    private final String filePath;
    private AccountGateway accountGateway;
    private TradeGateway tradeGateway;
    private ThresholdsGateway thresholdsGateway;
    private ItemsGateway itemsGateway;


    public CSVGatewayPool() throws IOException {
        this.filePath = System.getProperty("user.dir") + "/out/files/";
        buildAccountGateway();
        buildTradeGateway();
        buildThresholdsGateway();
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
    public ThresholdsGateway getThresholdsGateway() {
        return thresholdsGateway;
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

    private void buildThresholdsGateway() throws IOException {
        thresholdsGateway = new CSVThresholdsGateway(filePath + "restrictions.csv");
    }

    private void buildItemsGateway() throws IOException {
        itemsGateway = new CSVItemsGateway(filePath + "items.csv");
    }

}
