package org.twelve.controllers;

import org.twelve.entities.TradeStatus;
import org.twelve.gateways.*;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.presenters.TradeListPresenter;
import org.twelve.usecases.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TradeListController {

    private TradeListPresenter tradeListPresenter;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private final TradeRepository tradeRepository;
    private final UseCasePool useCasePool;

    public TradeListController(UseCasePool useCasePool, GatewayPool gatewayPool){
        tradeManager = useCasePool.getTradeManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        itemManager = useCasePool.getItemManager();
        tradeRepository = useCasePool.getTradeRepository();
        this.useCasePool = useCasePool;

    }

    public void setTradeListPresenter(TradeListPresenter tradeListPresenter) {
        this.tradeListPresenter = tradeListPresenter;
    }

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
            for (int tradeID : tradeRepository.getAllTradesAccountID(sessionManager.getCurrAccountID())) {
                if (tradeRepository.getTradeStatus(tradeID) == tradeStatus) {
                    trades.add(accountRepository.getUsernameFromID(tradeRepository.getNextTraderID(tradeID,
                            sessionManager.getCurrAccountID())));
                    trades.add(tradeRepository.getDateTime(tradeID).format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")));
                }
            }
        }
        tradeListPresenter.setTradesShown(trades);
    }

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

    public void setSelectedTrade(int tradeType, int index) {
        ArrayList<Integer> trades = new ArrayList<>();
        TradeStatus tradeStatus = TradeStatus.UNCONFIRMED;
        if (tradeType == 1)
            tradeStatus = TradeStatus.CONFIRMED;
        else if (tradeType == 2)
            tradeStatus = TradeStatus.COMPLETED;
        else if (tradeType == 3)
            tradeStatus = TradeStatus.REJECTED;
        for (int tradeID : tradeRepository.getAllTradesAccountID(sessionManager.getCurrAccountID())) {
            if (tradeRepository.getTradeStatus(tradeID) == tradeStatus) {
                trades.add(tradeID);
            }
        }
        int tradeID = trades.get(index);
        sessionManager.setWorkingTrade(tradeID);
    }
}
