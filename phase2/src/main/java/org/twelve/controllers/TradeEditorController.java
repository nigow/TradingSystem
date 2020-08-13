package org.twelve.controllers;

import org.twelve.gateways.*;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;
import org.twelve.usecases.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TradeEditorController {
    private TradeEditorPresenter tradeEditorPresenter;

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

    public TradeEditorController(UseCasePool useCasePool, GatewayPool gatewayPool) {
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
    }

    public void populateGateways() {
        tradeGateway.populate(tradeManager);
        accountGateway.populate(accountRepository);
        itemsGateway.populate(itemManager);
        citiesGateway.populate(cityManager);
        thresholdsGateway.populate(thresholdRepository);
    }

    public void setTradeEditorPresenter(TradeEditorPresenter tradeEditorPresenter) {
        this.tradeEditorPresenter = tradeEditorPresenter;
    }

    public void removeSelectedTrade() {
        sessionManager.removeWorkingTrade();
    }

    public void setTradeProperties() {
        populateGateways();
        int trade = sessionManager.getWorkingTrade();
        List<Integer> userItemIDs = tradeManager.itemsTraderGives(sessionManager.getCurrAccountID(), trade);
        List<Integer> peerItemIDs = tradeManager.itemsTraderGives(tradeManager.getNextTraderID(trade, sessionManager.getCurrAccountID()), trade);
        List<String> userItems = new ArrayList<>();
        for (int id : userItemIDs)
            userItems.add(itemManager.getItemNameById(id));
        List<String> peerItems = new ArrayList<>();
        for (int id : peerItemIDs)
            peerItems.add(itemManager.getItemNameById(id));
        tradeEditorPresenter.setUserItems(userItems);
        tradeEditorPresenter.setPeerItems(peerItems);
        tradeEditorPresenter.setPeerUsername(accountRepository.getUsernameFromID(tradeManager.getNextTraderID(trade, sessionManager.getCurrAccountID())));
        tradeEditorPresenter.setTradeStatus(tradeManager.getTradeStatus(trade));
        boolean canEdit = false;
        boolean canConfirm = false;
        boolean canComplete = false;
        boolean canCancel = false;
        if (tradeManager.isUnconfirmed(trade)) {
            canCancel = true;
            if (tradeManager.isEditTurn(sessionManager.getCurrAccountID(), trade)) {
                if (tradeManager.getDateTime(trade).isAfter(LocalDateTime.now()))
                    canConfirm = true;
                if (tradeManager.canBeEdited(trade))
                    canEdit = true;
            }
        } else if (tradeManager.isConfirmed(trade)) {
            if (tradeManager.getDateTime(trade).isBefore(LocalDateTime.now()) && !tradeManager.accountCompletedTrade(sessionManager.getCurrAccountID(), trade))
                canComplete = true;
        }
        tradeEditorPresenter.setCanEdit(canEdit);
        tradeEditorPresenter.setCanConfirm(canConfirm);
        tradeEditorPresenter.setCanComplete(canComplete);
        tradeEditorPresenter.setCanCancel(canCancel);
        tradeEditorPresenter.setIsPermanent(tradeManager.isPermanent(trade));
        LocalDateTime dateTime = tradeManager.getDateTime(trade);
        tradeEditorPresenter.setHourChosen(dateTime.getHour());
        tradeEditorPresenter.setMinuteChosen(dateTime.getMinute());
        tradeEditorPresenter.setDateChosen(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()));
        tradeEditorPresenter.setLocationChosen(tradeManager.getLocation(trade));
    }

    public void cancelTrade() {
        tradeManager.rejectTrade(sessionManager.getWorkingTrade());
    }

    public void editTrade(String location, LocalDateTime dateTime) {
        int trade = sessionManager.getWorkingTrade();
        tradeManager.editTimePlace(trade, dateTime, location);
        if (!tradeManager.canBeEdited(trade)) {
            tradeManager.rejectTrade(trade); // trade was edited too many times, and automatically gets cancelled
        }
    }

    public void completeTrade() {
        tradeManager.completeTrade(sessionManager.getCurrAccountID(), sessionManager.getWorkingTrade());
    }

    public void confirmTrade() {
        tradeManager.confirmTrade(sessionManager.getWorkingTrade());
    }
}
