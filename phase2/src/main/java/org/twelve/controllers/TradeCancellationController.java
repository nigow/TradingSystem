package org.twelve.controllers;

import org.twelve.presenters.TradeCancellationPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.TradeManager;
import org.twelve.usecases.UseCasePool;

public class TradeCancellationController {

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private TradeCancellationPresenter tradeCancellationPresenter;

    public TradeCancellationController(UseCasePool useCasePool){
        this.tradeManager = useCasePool.getTradeManager();
        this.accountRepository = useCasePool.getAccountRepository();
    }

    public void setTradeCancellationPresenter(TradeCancellationPresenter tradeCancellationPresenter) {
        this.tradeCancellationPresenter = tradeCancellationPresenter;
    }

    public void cancelTrade(int tradeIndex){

    }

    public void updateList(){

    }
}
