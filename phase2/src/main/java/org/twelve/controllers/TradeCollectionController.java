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
        List<String> selectedAccountTrades = new ArrayList<>();
        List<String> recentOneWays = new ArrayList<>();
        List<String> recentTwoWays = new ArrayList<>();
        List<String> topTradingPartners = new ArrayList<>();
        if (username != null) {
            for (String s : tradeManager.getAllTradesAccountString(accountRepository.getIDFromUsername(username))) {
                selectedAccountTrades.add(s);
            }
            for (int recentOneWay : tradeManager.getRecentOneWay(accountRepository.getIDFromUsername(username))) {
                recentOneWays.add(accountRepository.getUsernameFromID(recentOneWay));
            }
            for (int recentTwoWay : tradeManager.getRecentTwoWay(accountRepository.getIDFromUsername(username))) {
                recentTwoWays.add(accountRepository.getUsernameFromID(recentTwoWay));
            }
            for (int topTradingPartner : tradeManager.getTopThreePartnersIds(accountRepository.getIDFromUsername(username))) {
                topTradingPartners.add(accountRepository.getUsernameFromID(topTradingPartner));
            }
        } else {
            for (String s : tradeManager.getAllTradesAccountString(sessionManager.getCurrAccountID())) {
                selectedAccountTrades.add(s);
            }
            for (int recentOneWay : tradeManager.getRecentOneWay(sessionManager.getCurrAccountID())) {
                recentOneWays.add(accountRepository.getUsernameFromID(recentOneWay));
            }
            for (int recentTwoWay : tradeManager.getRecentTwoWay(sessionManager.getCurrAccountID())) {
                recentTwoWays.add(accountRepository.getUsernameFromID(recentTwoWay));
            }
            for (int topTradingPartner : tradeManager.getTopThreePartnersIds(sessionManager.getCurrAccountID())) {
                topTradingPartners.add(accountRepository.getUsernameFromID(topTradingPartner));
            }
        }
        tradeCollectionPresenter.setAllTrades(selectedAccountTrades);
        tradeCollectionPresenter.setRecentOneWays(recentOneWays);
        tradeCollectionPresenter.setRecentTwoWays(recentTwoWays);
        tradeCollectionPresenter.setTopTradingPartners(topTradingPartners);

        List<String> allUsers = new ArrayList<>();
        for (String s : accountRepository.getAccountStrings()) {
            allUsers.add(s);
        }
        tradeCollectionPresenter.setAllUsers(allUsers);

    }

    public void cancelTrade(int selectedIndex) {
    }

    public void confirmTrade(int selectedIndex) {

    }
}
