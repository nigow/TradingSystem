package org.twelve.controllers;

import org.twelve.presenters.TradeCancellationPresenter;
import org.twelve.usecases.TradeManager;
import org.twelve.usecases.TradeRepository;
import org.twelve.usecases.UseCasePool;

public class TradeCancellationController {

    private final TradeManager tradeManager;
    private final TradeRepository tradeRepository;
    private TradeCancellationPresenter tradeCancellationPresenter;

    private final UseCasePool useCasePool;

    public TradeCancellationController(UseCasePool useCasePool){
        this.tradeManager = useCasePool.getTradeManager();
        this.useCasePool = useCasePool;
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
        useCasePool.populateAll();
        tradeCancellationPresenter.setAllTrades(tradeRepository.getAllTradesString());
    }
}
