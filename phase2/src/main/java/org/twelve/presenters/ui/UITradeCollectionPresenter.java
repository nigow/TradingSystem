package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCollectionPresenter;

import java.util.List;
import java.util.ResourceBundle;

public class UITradeCollectionPresenter extends ObservablePresenter implements TradeCollectionPresenter {

    private final ResourceBundle localizedResources;

    public UITradeCollectionPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
    }

    @Override
    public List<String> getAllTradesOfAccount(int accountID) {
        return null;
    }
}
