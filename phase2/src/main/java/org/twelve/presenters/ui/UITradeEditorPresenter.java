package org.twelve.presenters.ui;

import org.twelve.entities.TradeStatus;
import org.twelve.presenters.TradeEditorPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        setTradeStatus(TradeStatus.REJECTED);
    }

    @Override
    public void setCanEdit(boolean canEdit) {
        boolean old = this.canEdit;
        this.canEdit = canEdit;
        propertyChangeSupport.firePropertyChange("canEdit", old, this.canEdit);
    }

    @Override
    public boolean getCanEdit() {
        return canEdit;
    }

    @Override
    public void setCanCancel(boolean canCancel) {
        boolean old = this.canCancel;
        this.canCancel = canCancel;
        propertyChangeSupport.firePropertyChange("canCancel", old, this.canCancel);
    }

    @Override
    public boolean getCanCancel() {
        return canCancel;
    }

    @Override
    public void setCanConfirm(boolean canConfirm) {
        boolean old = this.canConfirm;
        this.canConfirm = canConfirm;
        propertyChangeSupport.firePropertyChange("canConfirm", old, this.canConfirm);
    }

    @Override
    public boolean getCanConfirm() {
        return canConfirm;
    }

    @Override
    public void setCanComplete(boolean canComplete) {
        boolean old = this.canComplete;
        this.canComplete = canComplete;
        propertyChangeSupport.firePropertyChange("canComplete", old, this.canComplete);
    }

    @Override
    public boolean getCanComplete() {
        return canComplete;
    }

    @Override
    public List<String> getUserItems() {
        return userItems;
    }

    @Override
    public List<String> getPeerItems() {
        return peerItems;
    }

    @Override
    public void setUserItems(List<String> userItems) {
        List<String> old = this.userItems;
        this.userItems = userItems;
        propertyChangeSupport.firePropertyChange("userItems", old, this.userItems);
    }

    @Override
    public void setPeerItems(List<String> peerItems) {
        List<String> old = this.peerItems;
        this.peerItems = peerItems;
        propertyChangeSupport.firePropertyChange("peerItems", old, this.peerItems);
    }

    @Override
    public void setPeerUsername(String username) {
        String old = this.peerUsername;
        this.peerUsername = username;
        propertyChangeSupport.firePropertyChange("peerUsername", old, this.peerUsername);
    }

    @Override
    public String getPeerUsername() {
        return peerUsername;
    }

    @Override
    public void setTradeStatus(TradeStatus tradeStatus) {
        String old = this.tradeStatus;
        this.tradeStatus = localizedResources.getString(tradeStatus.name().toLowerCase());
        propertyChangeSupport.firePropertyChange("tradeStatus", old, this.tradeStatus);
    }

    @Override
    public String getTradeStatus() {
        return tradeStatus;
    }

    @Override
    public void setIsPermanent(boolean isPermanent) {
        boolean old = this.isPermanent;
        this.isPermanent = isPermanent;
        propertyChangeSupport.firePropertyChange("isPermanent", old, this.isPermanent);
    }

    @Override
    public boolean getIsPermanent() {
        return isPermanent;
    }
}
