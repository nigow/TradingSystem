package org.twelve.controllers;

import org.twelve.entities.TradeStatus;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.usecases.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TradeEditorController {
    private TradeEditorPresenter tradeEditorPresenter;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private final TradeRepository tradeRepository;
    private final UseCasePool useCasePool;

    public TradeEditorController(UseCasePool useCasePool) {
        tradeManager = useCasePool.getTradeManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        itemManager = useCasePool.getItemManager();
        tradeRepository = useCasePool.getTradeRepository();
        this.useCasePool = useCasePool;
    }

    public void setTradeEditorPresenter(TradeEditorPresenter tradeEditorPresenter) {
        this.tradeEditorPresenter = tradeEditorPresenter;
    }

    public void removeSelectedTrade() {
        sessionManager.removeWorkingTrade();
    }

    public void setTradeProperties() {
        useCasePool.populateAll();
        int trade = sessionManager.getWorkingTrade();
        List<Integer> userItemIDs = tradeRepository.itemsTraderGives(sessionManager.getCurrAccountID(), trade);
        List<Integer> peerItemIDs = tradeRepository.itemsTraderGives(tradeRepository.getNextTraderID(trade, sessionManager.getCurrAccountID()), trade);
        List<String> userItems = new ArrayList<>();
        for (int id : userItemIDs)
            userItems.add(itemManager.getItemNameById(id));
        List<String> peerItems = new ArrayList<>();
        for (int id : peerItemIDs)
            peerItems.add(itemManager.getItemNameById(id));
        tradeEditorPresenter.setUserItems(userItems);
        tradeEditorPresenter.setPeerItems(peerItems);
        tradeEditorPresenter.setPeerUsername(accountRepository.getUsernameFromID(tradeRepository.getNextTraderID(trade, sessionManager.getCurrAccountID())));
        tradeEditorPresenter.setTradeStatus(tradeRepository.getTradeStatus(trade));
        boolean canEdit = false;
        boolean canConfirm = false;
        boolean canComplete = false;
        boolean canCancel = false;
        if (tradeRepository.getTradeStatus(trade) == TradeStatus.UNCONFIRMED) {
            canCancel = true;
            if (tradeManager.isEditTurn(sessionManager.getCurrAccountID(), trade)) {
                if (tradeRepository.getDateTime(trade).isAfter(LocalDateTime.now()))
                    canConfirm = true;
                if (tradeManager.canBeEdited(trade))
                    canEdit = true;
            }
        } else if (tradeRepository.getTradeStatus(trade) == TradeStatus.CONFIRMED) {
            if (tradeRepository.getDateTime(trade).isBefore(LocalDateTime.now()) && !tradeManager.accountCompletedTrade(sessionManager.getCurrAccountID(), trade))
                canComplete = true;
        }
        tradeEditorPresenter.setCanEdit(canEdit);
        tradeEditorPresenter.setCanConfirm(canConfirm);
        tradeEditorPresenter.setCanComplete(canComplete);
        tradeEditorPresenter.setCanCancel(canCancel);
        tradeEditorPresenter.setIsPermanent(tradeRepository.isPermanent(trade));
        LocalDateTime dateTime = tradeRepository.getDateTime(trade);
        tradeEditorPresenter.setHourChosen(dateTime.getHour());
        tradeEditorPresenter.setMinuteChosen(dateTime.getMinute());
        tradeEditorPresenter.setDateChosen(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()));
        tradeEditorPresenter.setLocationChosen(tradeRepository.getLocation(trade));
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
