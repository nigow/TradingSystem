package org.twelve.controllers;

import org.twelve.presenters.TradeCollectionPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.TradeManager;
import org.twelve.usecases.UseCasePool;

public class TradeCollectionController {

    private TradeCollectionPresenter tradeCollectionPresenter;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;

    public TradeCollectionController(UseCasePool useCasePool){
        this.tradeManager = useCasePool.getTradeManager();
        this.accountRepository = useCasePool.getAccountRepository();
    }

    public void setTradeCollectionPresenter(TradeCollectionPresenter tradeCollectionPresenter) {
        this.tradeCollectionPresenter = tradeCollectionPresenter;
    }
}
