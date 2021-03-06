package org.twelve.presenters.ui;

import org.twelve.entities.TradeStatus;
import org.twelve.presenters.TradeEditorPresenter;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Presenter for the trade editor view
 */
public class UITradeEditorPresenter extends ObservablePresenter implements TradeEditorPresenter {

    private final ResourceBundle localizedResources;

    private boolean canEdit;
    private boolean canConfirm;
    private boolean canComplete;
    private boolean canCancel;
    private boolean isPermanent;

    private List<String> userItems;
    private List<String> peerItems;

    private String peerUsername;
    private String tradeStatus;

    private int hourChosen;
    private int minuteChosen;
    private LocalDate dateChosen;
    private String locationChosen;

    /**
     * Constructor for the presenter for the trade editor view
     *
     * @param localizedResources Pack containing any localized strings.
     */
    public UITradeEditorPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setPeerItems(new ArrayList<>());
        setUserItems(new ArrayList<>());
        setCanCancel(false);
        setCanComplete(false);
        setCanEdit(false);
        setCanConfirm(false);
        setIsPermanent(false);
        setPeerUsername("");
        setHourChosen(0);
        setMinuteChosen(0);
        setDateChosen(LocalDate.of(2000, 1, 1));
        setTradeStatus(TradeStatus.REJECTED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanEdit(boolean canEdit) {
        boolean old = this.canEdit;
        this.canEdit = canEdit;
        propertyChangeSupport.firePropertyChange("canEdit", old, this.canEdit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanEdit() {
        return canEdit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanCancel(boolean canCancel) {
        boolean old = this.canCancel;
        this.canCancel = canCancel;
        propertyChangeSupport.firePropertyChange("canCancel", old, this.canCancel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanCancel() {
        return canCancel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanConfirm(boolean canConfirm) {
        boolean old = this.canConfirm;
        this.canConfirm = canConfirm;
        propertyChangeSupport.firePropertyChange("canConfirm", old, this.canConfirm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanConfirm() {
        return canConfirm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanComplete(boolean canComplete) {
        boolean old = this.canComplete;
        this.canComplete = canComplete;
        propertyChangeSupport.firePropertyChange("canComplete", old, this.canComplete);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanComplete() {
        return canComplete;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUserItems() {
        return userItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPeerItems() {
        return peerItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserItems(List<String> userItems) {
        List<String> old = this.userItems;
        this.userItems = userItems;
        propertyChangeSupport.firePropertyChange("userItems", old, this.userItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPeerItems(List<String> peerItems) {
        List<String> old = this.peerItems;
        this.peerItems = peerItems;
        propertyChangeSupport.firePropertyChange("peerItems", old, this.peerItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPeerUsername(String username) {
        String old = this.peerUsername;
        this.peerUsername = MessageFormat.format(localizedResources.getString("theirItems"), username);
        propertyChangeSupport.firePropertyChange("peerUsername", old, this.peerUsername);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPeerUsername() {
        return peerUsername;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTradeStatus(TradeStatus tradeStatus) {
        String old = this.tradeStatus;
        this.tradeStatus = MessageFormat.format(localizedResources.getString("tradeStatus"), localizedResources.getString(tradeStatus.name().toLowerCase()));
        propertyChangeSupport.firePropertyChange("tradeStatus", old, this.tradeStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTradeStatus() {
        return tradeStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsPermanent(boolean isPermanent) {
        boolean old = this.isPermanent;
        this.isPermanent = isPermanent;
        propertyChangeSupport.firePropertyChange("isPermanent", old, this.isPermanent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getIsPermanent() {
        return isPermanent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHourChosen() {
        return hourChosen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinuteChosen() {
        return minuteChosen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHourChosen(int hourChosen) {
        int old = this.hourChosen;
        this.hourChosen = hourChosen;
        propertyChangeSupport.firePropertyChange("hourChosen", old, this.hourChosen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinuteChosen(int minuteChosen) {
        int old = this.minuteChosen;
        this.minuteChosen = minuteChosen;
        propertyChangeSupport.firePropertyChange("minuteChosen", old, this.minuteChosen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDateChosen(LocalDate dateChosen) {
        LocalDate old = this.dateChosen;
        this.dateChosen = dateChosen;
        propertyChangeSupport.firePropertyChange("dateChosen", old, this.dateChosen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getDateChosen() {
        return dateChosen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocationChosen() {
        return locationChosen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocationChosen(String locationChosen) {
        String old = this.locationChosen;
        this.locationChosen = locationChosen;
        propertyChangeSupport.firePropertyChange("locationChosen", old, this.locationChosen);
    }
}
