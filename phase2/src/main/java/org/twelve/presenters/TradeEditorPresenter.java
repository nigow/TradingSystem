package org.twelve.presenters;

import org.twelve.entities.TradeStatus;

import java.util.Collection;
import java.util.List;

public interface TradeEditorPresenter {
    void setCanEdit(boolean canEdit);
    boolean getCanEdit();
    void setCanCancel(boolean canCancel);
    boolean getCanCancel();
    void setCanConfirm(boolean canConfirm);
    boolean getCanConfirm();
    void setCanComplete(boolean canComplete);
    boolean getCanComplete();
    void setUserItems(List<String> userItems);
    List<String> getUserItems();
    void setPeerItems(List<String> peerItems);
    List<String> getPeerItems();
    void setPeerUsername(String username);
    String getPeerUsername();
    void setTradeStatus(TradeStatus tradeStatus);
    String getTradeStatus();
    void setIsPermanent(boolean isPermanent);
    boolean getIsPermanent();
}
