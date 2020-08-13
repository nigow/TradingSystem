package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCancellationPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UITradeCancellationPresenter extends ObservablePresenter implements TradeCancellationPresenter {

    private List<String> allTrades;
    private final ResourceBundle localizedResources;

    public UITradeCancellationPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setAllTrades(new ArrayList<>());
    }

    @Override
    public List<String> getAllTrades() {
        return allTrades;
    }

    @Override
    public void setAllTrades(List<String> allTrades) {
        List <String> oldTrades = this.allTrades;
        this.allTrades = allTrades;
        propertyChangeSupport.firePropertyChange("allTrades", oldTrades, allTrades);
    }
}
