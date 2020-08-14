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

    private final TradeGateway tradeGateway;
    private final AccountGateway accountGateway;
    private final ItemsGateway itemsGateway;
    private final ThresholdsGateway thresholdsGateway;
    private final CitiesGateway citiesGateway;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private final CityManager cityManager;
    private final ThresholdRepository thresholdRepository;
    private final UseCasePool useCasePool;

    public TradeListController(UseCasePool useCasePool, GatewayPool gatewayPool){
        this.tradeGateway = gatewayPool.getTradeGateway();
        this.accountGateway = gatewayPool.getAccountGateway();
        this.thresholdsGateway = gatewayPool.getThresholdsGateway();
        this.citiesGateway = gatewayPool.getCitiesGateway();
        this.itemsGateway = gatewayPool.getItemsGateway();

        tradeManager = useCasePool.getTradeManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        itemManager = useCasePool.getItemManager();
        cityManager = useCasePool.getCityManager();
        thresholdRepository = useCasePool.getThresholdRepository();
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
            for (int tradeID : tradeManager.getAllTradesAccountID(sessionManager.getCurrAccountID())) {
                if (tradeManager.getTradeStatus(tradeID) == tradeStatus) {
                    trades.add(accountRepository.getUsernameFromID(tradeManager.getNextTraderID(tradeID,
                            sessionManager.getCurrAccountID())));
                    trades.add(tradeManager.getDateTime(tradeID).format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm")));
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
        for (int tradeID : tradeManager.getAllTradesAccountID(sessionManager.getCurrAccountID())) {
            if (tradeManager.getTradeStatus(tradeID) == tradeStatus) {
                trades.add(tradeID);
            }
        }
        int tradeID = trades.get(index);
        sessionManager.setWorkingTrade(tradeID);
    }

//    public void updateBoxes() {
//        tradeCollectionPresenter.setAllTradeStatus();
//        tradeCollectionPresenter.setStatsTypes();
//    }


//
//    public void changeSelectedUser(String username) {
//        if (username != null) {tradeCollectionPresenter.setSelectedUser(username);}
//    }
//
//    public void updateLists(String username) {
//        List<String> selectedAccountTrades = new ArrayList<>();
//        List<String> recentOneWays = new ArrayList<>();
//        List<String> recentTwoWays = new ArrayList<>();
//        List<String> topTradingPartners = new ArrayList<>();
//        if (username != null) {
//            for (String s : tradeManager.getAllTradesAccountString(accountRepository.getIDFromUsername(username))) {
//                selectedAccountTrades.add(s);
//            }
//            for (int recentOneWay : tradeManager.getRecentOneWay(accountRepository.getIDFromUsername(username))) {
//                recentOneWays.add(accountRepository.getUsernameFromID(recentOneWay));
//            }
//            for (int recentTwoWay : tradeManager.getRecentTwoWay(accountRepository.getIDFromUsername(username))) {
//                recentTwoWays.add(accountRepository.getUsernameFromID(recentTwoWay));
//            }
//            for (int topTradingPartner : tradeManager.getTopThreePartnersIds(accountRepository.getIDFromUsername(username))) {
//                topTradingPartners.add(accountRepository.getUsernameFromID(topTradingPartner));
//            }
//        } else {
//            for (String s : tradeManager.getAllTradesAccountString(sessionManager.getCurrAccountID())) {
//                selectedAccountTrades.add(s);
//            }
//            for (int recentOneWay : tradeManager.getRecentOneWay(sessionManager.getCurrAccountID())) {
//                recentOneWays.add(accountRepository.getUsernameFromID(recentOneWay));
//            }
//            for (int recentTwoWay : tradeManager.getRecentTwoWay(sessionManager.getCurrAccountID())) {
//                recentTwoWays.add(accountRepository.getUsernameFromID(recentTwoWay));
//            }
//            for (int topTradingPartner : tradeManager.getTopThreePartnersIds(sessionManager.getCurrAccountID())) {
//                topTradingPartners.add(accountRepository.getUsernameFromID(topTradingPartner));
//            }
//        }
//        tradeCollectionPresenter.setAllTrades(selectedAccountTrades);
//        tradeCollectionPresenter.setRecentOneWays(recentOneWays);
//        tradeCollectionPresenter.setRecentTwoWays(recentTwoWays);
//        tradeCollectionPresenter.setTopTradingPartners(topTradingPartners);
//
//        List<String> allUsers = new ArrayList<>();
//        for (String s : accountRepository.getAccountStrings()) {
//            allUsers.add(s);
//        }
//        tradeCollectionPresenter.setAllUsers(allUsers);
//
//    }
//
//    public void cancelTrade(int selectedIndex) {
//    }
//
//    public void confirmTrade(int selectedIndex) {
//
//    }
}
