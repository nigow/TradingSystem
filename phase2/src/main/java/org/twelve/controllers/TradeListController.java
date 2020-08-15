package org.twelve.controllers;

import org.twelve.entities.TradeStatus;
import org.twelve.presenters.TradeListPresenter;
import org.twelve.usecases.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Controller for trades and stats
 */
public class TradeListController {

    private TradeListPresenter tradeListPresenter;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private final UseCasePool useCasePool;

    /**
     * Constructor for the controller for trades and stats
     * @param useCasePool an instance of UseCasePool
     */
    public TradeListController(UseCasePool useCasePool){
        tradeManager = useCasePool.getTradeManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        itemManager = useCasePool.getItemManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Set the presenter for this controller
     * @param tradeListPresenter an instance of a class that implements {@link org.twelve.presenters.TradeListPresenter}
     */
    public void setTradeListPresenter(TradeListPresenter tradeListPresenter) {
        this.tradeListPresenter = tradeListPresenter;
    }

    /**
     * Update the list of trades shown in TradeListPresenter
     * @param tradeType the type of trade to be shown
     */
    public void updateTradeList(int tradeType) {
        useCasePool.populateAll();
        ArrayList<String> trades = new ArrayList<>();
        if (tradeType != -1) {
            TradeStatus tradeStatus = TradeStatus.UNCONFIRMED;
            if (tradeType == 1)
                tradeStatus = TradeStatus.CONFIRMED;
            else if (tradeType == 2)
                tradeStatus = TradeStatus.COMPLETED;
            else if (tradeType == 3)
                tradeStatus = TradeStatus.REJECTED;
            for (int tradeID : tradeManager.getAllTradesAccountID(sessionManager.getCurrAccountID())) {
                if (tradeManager.getTradeStatus(tradeID) == tradeStatus) {
                    trades.add(accountRepository.getUsernameFromID(tradeManager.getNextTraderID(tradeID,
                            sessionManager.getCurrAccountID())));
                    trades.add(tradeManager.getDateTime(tradeID).format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")));
                }
            }
        }
        tradeListPresenter.setTradesShown(trades);
    }

    /**
     * Update the list of stats in TradeListPresenter
     * @param statsIndex The index of the type of stats to show
     */
    public void updateStatsList(int statsIndex) {
        useCasePool.populateAll();
        ArrayList<String> stat = new ArrayList<>();
        if (statsIndex == 0) {
            for (int id : tradeManager.getTopThreePartnersIds(sessionManager.getCurrAccountID()))
                stat.add(accountRepository.getUsernameFromID(id));
        } else if (statsIndex == 1) {
            for (int id : tradeManager.getRecentOneWay(sessionManager.getCurrAccountID()))
                stat.add(itemManager.getItemNameById(id));
        } else if (statsIndex == 2) {
            for (int id : tradeManager.getRecentTwoWay(sessionManager.getCurrAccountID()))
                stat.add(itemManager.getItemNameById(id));
        }
        tradeListPresenter.setStatsShown(stat);
    }

    /**
     * Set the selected trade in TradeListPresenter
     * @param tradeType the type of trade that is selected
     * @param index the index of the selected trade
     */
    public void setSelectedTrade(int tradeType, int index) {
        ArrayList<Integer> trades = new ArrayList<>();
        TradeStatus tradeStatus = TradeStatus.UNCONFIRMED;
        if (tradeType == 1)
            tradeStatus = TradeStatus.CONFIRMED;
        else if (tradeType == 2)
            tradeStatus = TradeStatus.COMPLETED;
        else if (tradeType == 3)
            tradeStatus = TradeStatus.REJECTED;
        for (int tradeID : tradeManager.getAllTradesAccountID(sessionManager.getCurrAccountID())) {
            if (tradeManager.getTradeStatus(tradeID) == tradeStatus) {
                trades.add(tradeID);
            }
        }
        int tradeID = trades.get(index);
        sessionManager.setWorkingTrade(tradeID);
    }
}
