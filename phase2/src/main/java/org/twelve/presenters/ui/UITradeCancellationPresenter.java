package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCancellationPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Presenter for the trade cancellation view
 */
public class UITradeCancellationPresenter extends ObservablePresenter implements TradeCancellationPresenter {

    private List<String> allTrades;
    private final ResourceBundle localizedResources;

    /**
     * Constructor for the presenter for the trade cancellation view
     * @param localizedResources pack for containing any localized strings
     */
    public UITradeCancellationPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
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
