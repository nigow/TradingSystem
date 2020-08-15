package org.twelve.controllers;

import org.twelve.presenters.TradeCancellationPresenter;
import org.twelve.usecases.TradeManager;
import org.twelve.usecases.TradeRepository;
import org.twelve.usecases.UseCasePool;

/**
 * Controller for dealing with the cancellation of trades
 */
public class TradeCancellationController {

    private final TradeManager tradeManager;
    private final TradeRepository tradeRepository;
    private TradeCancellationPresenter tradeCancellationPresenter;

    private final UseCasePool useCasePool;

    /**
     * Constructor for the controller dealing with the cancellation of trades
     * @param useCasePool an instance of UseCasePool to get all the use cases
     */
    public TradeCancellationController(UseCasePool useCasePool){
        this.tradeManager = useCasePool.getTradeManager();
        this.useCasePool = useCasePool;
        this.tradeRepository = useCasePool.getTradeRepository();
    }

    /**
     * Set the presenter for this controller
     * @param tradeCancellationPresenter an instance of a class that implements {@link org.twelve.presenters.TradeCancellationPresenter}
     */
    public void setTradeCancellationPresenter(TradeCancellationPresenter tradeCancellationPresenter) {
        this.tradeCancellationPresenter = tradeCancellationPresenter;
    }

    /**
     * Cancel the trade at the given tradeIndex
     * @param tradeIndex the index of the trade
     */
    public void cancelTrade(int tradeIndex){
        int tradeID = tradeRepository.getAllTradesIds().get(tradeIndex);
        tradeManager.adminCancelTrade(tradeID);
    }

    /**
     * Update the trades in tradeCancellationPresenter
     */
    public void updateList(){
        useCasePool.populateAll();
        tradeCancellationPresenter.setAllTrades(tradeRepository.getAllTradesString());
    }
}
