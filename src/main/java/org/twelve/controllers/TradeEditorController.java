package org.twelve.controllers;

import org.twelve.entities.TradeStatus;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.usecases.*;
import org.twelve.usecases.account.AccountRepository;
import org.twelve.usecases.item.ItemManager;
import org.twelve.usecases.system.SessionManager;
import org.twelve.usecases.trade.TradeManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to deal with editing trades
 */
public class TradeEditorController {
    private TradeEditorPresenter tradeEditorPresenter;

    private final TradeManager tradeManager;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ItemManager itemManager;
    private final UseCasePool useCasePool;

    /**
     * Constructor for the controller to deal with editing trades
     *
     * @param useCasePool an instance of useCasePool to get all the use cases
     */
    public TradeEditorController(UseCasePool useCasePool) {
        tradeManager = useCasePool.getTradeManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        itemManager = useCasePool.getItemManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Set the presenter for this controller
     *
     * @param tradeEditorPresenter an instance of a class that implements {@link org.twelve.presenters.TradeEditorPresenter}
     */
    public void setTradeEditorPresenter(TradeEditorPresenter tradeEditorPresenter) {
        this.tradeEditorPresenter = tradeEditorPresenter;
    }

    /**
     * Remove the selected trade from the system
     */
    public void removeSelectedTrade() {
        sessionManager.removeWorkingTrade();
    }

    private void setTradeActions() {
        int tradeID = sessionManager.getWorkingTrade();
        boolean canEdit = false;
        boolean canConfirm = false;
        boolean canComplete = false;
        boolean canCancel = false;
        if (tradeManager.getTradeStatus(tradeID) == TradeStatus.UNCONFIRMED) {
            canCancel = true;
            if (tradeManager.isEditTurn(sessionManager.getCurrAccountID(), tradeID)) {
                if (tradeManager.getDateTime(tradeID).isAfter(LocalDateTime.now()))
                    canConfirm = true;
                if (tradeManager.canBeEdited(tradeID))
                    canEdit = true;
            }
        } else if (tradeManager.getTradeStatus(tradeID) == TradeStatus.CONFIRMED) {
            if (tradeManager.getDateTime(tradeID).isBefore(LocalDateTime.now()) && !tradeManager.accountCompletedTrade(sessionManager.getCurrAccountID(), tradeID))
                canComplete = true;
        }
        tradeEditorPresenter.setCanEdit(canEdit);
        tradeEditorPresenter.setCanConfirm(canConfirm);
        tradeEditorPresenter.setCanComplete(canComplete);
        tradeEditorPresenter.setCanCancel(canCancel);
    }

    private void setTradeItems() {
        int tradeID = sessionManager.getWorkingTrade();
        List<Integer> userItemIDs = tradeManager.itemsTraderGives(sessionManager.getCurrAccountID(), tradeID);
        List<Integer> peerItemIDs = tradeManager.itemsTraderGives(tradeManager.getNextTraderID(tradeID, sessionManager.getCurrAccountID()), tradeID);
        List<String> userItems = new ArrayList<>();
        for (int id : userItemIDs)
            userItems.add(itemManager.getItemNameById(id));
        List<String> peerItems = new ArrayList<>();
        for (int id : peerItemIDs)
            peerItems.add(itemManager.getItemNameById(id));
        tradeEditorPresenter.setUserItems(userItems);
        tradeEditorPresenter.setPeerItems(peerItems);
    }

    /**
     * Populate the data in the presenter with the correct properties of the given trade
     */
    public void setTradeProperties() {
        useCasePool.populateAll();
        int tradeID = sessionManager.getWorkingTrade();
        setTradeItems();
        tradeEditorPresenter.setPeerUsername(accountRepository.getUsernameFromID(tradeManager.getNextTraderID(tradeID, sessionManager.getCurrAccountID())));
        tradeEditorPresenter.setTradeStatus(tradeManager.getTradeStatus(tradeID));
        setTradeActions();
        tradeEditorPresenter.setIsPermanent(tradeManager.isPermanent(tradeID));
        LocalDateTime dateTime = tradeManager.getDateTime(tradeID);
        tradeEditorPresenter.setHourChosen(dateTime.getHour());
        tradeEditorPresenter.setMinuteChosen(dateTime.getMinute());
        tradeEditorPresenter.setDateChosen(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()));
        tradeEditorPresenter.setLocationChosen(tradeManager.getLocation(tradeID));
    }

    /**
     * Cancel the trade that is currently being worked on
     */
    public void cancelTrade() {
        tradeManager.rejectTrade(sessionManager.getWorkingTrade());
    }

    /**
     * Edit the trade that is currently being worked on
     *
     * @param location the new location of the trade
     * @param dateTime the new dateTime of the trade
     */
    public void editTrade(String location, LocalDateTime dateTime) {
        int trade = sessionManager.getWorkingTrade();
        tradeManager.editTimePlace(trade, dateTime, location);
        if (!tradeManager.canBeEdited(trade)) {
            tradeManager.rejectTrade(trade); // trade was edited too many times, and automatically gets cancelled
        }
    }

    /**
     * Complete the trade that is current being worked on
     */
    public void completeTrade() {
        tradeManager.completeTrade(sessionManager.getCurrAccountID(), sessionManager.getWorkingTrade());
    }

    /**
     * Confirm that the trade being worked on is has happened
     */
    public void confirmTrade() {
        tradeManager.confirmTrade(sessionManager.getWorkingTrade());
    }
}
