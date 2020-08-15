package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCancellationPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the trade cancellation view
 */
public class UITradeCancellationPresenter extends ObservablePresenter implements TradeCancellationPresenter {

    private List<String> allTrades;

    /**
     * Constructor for the presenter for the trade cancellation view
     */
    public UITradeCancellationPresenter() {
        super();
        setAllTrades(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllTrades() {
        return allTrades;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllTrades(List<String> allTrades) {
        List <String> oldTrades = this.allTrades;
        this.allTrades = allTrades;
        propertyChangeSupport.firePropertyChange("allTrades", oldTrades, allTrades);
    }
}
