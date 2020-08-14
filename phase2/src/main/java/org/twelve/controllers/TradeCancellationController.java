package org.twelve.controllers;

import org.twelve.gateways.GatewayPool;
import org.twelve.gateways.TradeGateway;
import org.twelve.presenters.TradeCancellationPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.TradeManager;
import org.twelve.usecases.TradeRepository;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

public class TradeCancellationController {

    private final TradeManager tradeManager;
    private final TradeRepository tradeRepository;
    private TradeCancellationPresenter tradeCancellationPresenter;

    private final TradeGateway tradeGateway;

    public TradeCancellationController(UseCasePool useCasePool, GatewayPool gatewayPool){
        this.tradeManager = useCasePool.getTradeManager();
        this.tradeGateway = gatewayPool.getTradeGateway();
        this.tradeRepository = useCasePool.getTradeRepository();
    }

    public void setTradeCancellationPresenter(TradeCancellationPresenter tradeCancellationPresenter) {
        this.tradeCancellationPresenter = tradeCancellationPresenter;
    }

    public void cancelTrade(int tradeIndex){
        int tradeID = tradeRepository.getAllTradesIds().get(tradeIndex);
        tradeManager.adminCancelTrade(tradeID);
    }

    public void updateList(){
        tradeGateway.populate(tradeRepository);
        tradeCancellationPresenter.setAllTrades(tradeRepository.getAllTradesString());
    }
}
