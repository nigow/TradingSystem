package org.twelve.controllers;

import org.twelve.presenters.TradeCollectionPresenter;
import org.twelve.usecases.*;

import java.util.ArrayList;
import java.util.List;

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

    public void changeSelectedUser(String username) {
        if (username != null) {tradeCollectionPresenter.setSelectedUser(username);}
    }

    public void updateLists(String username) {
        if (username != null) {
            List<String> selectedAccountTrades = new ArrayList<>();
            for (String s : tradeManager.getAllTradesAccountString(sessionManager.getCurrAccountID())) {
                selectedAccountTrades.add(s);
            }
            tradeCollectionPresenter.setAllTrades(selectedAccountTrades);
        }

        List<String> recentOneWays = new ArrayList<>();
        for (int recentOneWay : tradeManager.getRecentOneWay(sessionManager.getCurrAccountID())) {
            recentOneWays.add(accountRepository.getUsernameFromID(recentOneWay));
        }
        tradeCollectionPresenter.setRecentOneWays(recentOneWays);

        List<String> recentTwoWays = new ArrayList<>();
        for (int recentTwoWay : tradeManager.getRecentTwoWay(sessionManager.getCurrAccountID())) {
            recentOneWays.add(accountRepository.getUsernameFromID(recentTwoWay));
        }
        tradeCollectionPresenter.setRecentTwoWays(recentTwoWays);

        List<String> topTradingPartners = new ArrayList<>();
        for (int topTradingPartner : tradeManager.getTopThreePartnersIds(sessionManager.getCurrAccountID())) {
            recentOneWays.add(accountRepository.getUsernameFromID(topTradingPartner));
        }
        tradeCollectionPresenter.setTopTradingPartners(topTradingPartners);

        List<String> allUsers = new ArrayList<>();
        for (String s : accountRepository.getAccountStrings()) {
            if (!s.equals(sessionManager.getCurrAccountUsername())) {
                allUsers.add(s);
            }
        }
        tradeCollectionPresenter.setAllUsers(allUsers);

    }

    public void cancelTrade(int selectedIndex) {
    }

    public void confirmTrade(int selectedIndex) {
    }
}
