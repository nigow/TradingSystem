package org.twelve.presenters;

import org.twelve.entities.TradeStatus;

import java.util.Collection;
import java.util.List;

public interface TradeEditorPresenter {
    void setCanEdit(boolean canEdit);
    void setCanCancel(boolean canCancel);
    void setCanConfirm(boolean canConfrim);
    void setCanComplete(boolean canComplete);
    List<String> getUserItems();
    List<String> getPeerItems();
    void setUserItems(List<String> userItems);
    void setPeerItems(List<String> peerItems);
    void setPeerUsername(String username);
    void setTradeStatus(TradeStatus tradeStatus);
    void setIsPermanent(boolean isPermanent);
}
