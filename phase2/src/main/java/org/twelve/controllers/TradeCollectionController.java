package org.twelve.controllers;

import org.twelve.presenters.TradeCollectionPresenter;
import org.twelve.usecases.*;

public class TradeCollectionController {

    private TradeCollectionPresenter tradeCollectionPresenter;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private final StatusManager statusManager;

    public TradeCollectionController(UseCasePool useCasePool){
        tradeManager = useCasePool.getTradeManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        itemManager = useCasePool.getItemManager();
        statusManager = useCasePool.getStatusManager();
    }

    public void setTradeCollectionPresenter(TradeCollectionPresenter tradeCollectionPresenter) {
        this.tradeCollectionPresenter = tradeCollectionPresenter;
    }
}
