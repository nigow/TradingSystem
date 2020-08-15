package org.twelve.presenters;

import org.twelve.entities.TradeStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the presenter for the trade editor view
 */
public interface TradeEditorPresenter {
    /**
     * Setter for if the user can edit the trade
     * @param canEdit if the user can edit the trade
     */
    void setCanEdit(boolean canEdit);

    /**
     * Getter for if the user can edit the trade
     * @return if the user can edit the trade
     */
    boolean getCanEdit();

    /**
     * Setter for if the user can cancel the trade
     * @param canCancel if the user can cancel the trade
     */
    void setCanCancel(boolean canCancel);

    /**
     * Getter for if the user can cancel the trade
     * @return if the user can cancel the trade
     */
    boolean getCanCancel();

    /**
     * Setter for if the user can confirm the trade
     * @param canConfirm if the user can confirm the trade
     */
    void setCanConfirm(boolean canConfirm);

    /**
     * Getter for if the user can confirm the trade
     * @return if the user can confirm the trade
     */
    boolean getCanConfirm();

    /**
     * Setter for if the user can complete a trade
     * @param canComplete if the user can complete a trade
     */
    void setCanComplete(boolean canComplete);

    /**
     * Getter for if the user can complete a trade
     * @return if the user can complete a trade
     */
    boolean getCanComplete();

    /**
     * Setter for the user's items in the trade
     * @param userItems the user's items in the trade
     */
    void setUserItems(List<String> userItems);

    /**
     * Getter for the user's items in the trade
     * @return the user's items in the trade
     */
    List<String> getUserItems();

    /**
     * Setter for the peer's items in the trade
     * @param peerItems the peer's items in the trade
     */
    void setPeerItems(List<String> peerItems);

    /**
     * Getter for the peer's items in the trade
     * @return the peer's items in the trade
     */
    List<String> getPeerItems();

    /**
     * Setter for the peer's username
     * @param username the peer's username
     */
    void setPeerUsername(String username);

    /**
     * Getter for the peer's username
     * @return the peer's username
     */
    String getPeerUsername();

    /**
     * Setter for the status of the trade
     * @param tradeStatus the status of the trade
     */
    void setTradeStatus(TradeStatus tradeStatus);

    /**
     * Getter for the status of the trade
     * @return the status of the trade
     */
    String getTradeStatus();

    /**
     * Setter for if the trade is permanent
     * @param isPermanent if the trade is permanet
     */
    void setIsPermanent(boolean isPermanent);

    /**
     * Getter for if the trade is permanent
     * @return if the trade is permanent
     */
    boolean getIsPermanent();

    /**
     * Get the hour chosen for the trade
     * @return the hour chosen for the trade
     */
    int getHourChosen();

    /**
     * Get the minute chosen for the trade
     * @return the minute chosen for the trade
     */
    int getMinuteChosen();

    /**
     * Set the hour chosen for the trade
     * @param hourChosen the hour chosen for the trade
     */
    void setHourChosen(int hourChosen);

    /**
     * Set the minute chosen for the trade
     * @param minuteChosen the minute chosen for the trade
     */
    void setMinuteChosen(int minuteChosen);

    /**
     * Set the date chosen for the trade
     * @param dateChosen the date chosen for the trade
     */
    void setDateChosen(LocalDate dateChosen);

    /**
     * Get the date chosen for the date
     * @return the date chosen for the trade
     */
    LocalDate getDateChosen();

    /**
     * Get the location chosen for the trade
     * @return the location chosen for the trade
     */
    String getLocationChosen();

    /**
     * Set the location chosen for the trade
     * @param locationChosen the location chosen for the trade
     */
    void setLocationChosen(String locationChosen);
}
