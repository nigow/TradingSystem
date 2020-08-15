package org.twelve.presenters.ui;

import org.twelve.presenters.TradeListPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UITradeListPresenter extends ObservablePresenter implements TradeListPresenter {

    private final ResourceBundle localizedResources;
    private List<String> allTradeStatus;
    private List<String> statsTypes;
    private List<String> tradesShown;
    private List<String> statsShown;

    public UITradeListPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        statsTypes = new ArrayList<>();
        statsTypes.add(localizedResources.getString("partners"));
        statsTypes.add(localizedResources.getString("recentOneWay"));
        statsTypes.add(localizedResources.getString("recentTwoWay"));
        allTradeStatus = new ArrayList<>();
        allTradeStatus.add(localizedResources.getString("unconfirmed"));
        allTradeStatus.add(localizedResources.getString("confirmed"));
        allTradeStatus.add(localizedResources.getString("completed"));
        allTradeStatus.add(localizedResources.getString("rejected"));
        setStatsShown(new ArrayList<>());
        setTradesShown(new ArrayList<>());
//        setAllUsers(new ArrayList<>());
//        setAllTrades(new ArrayList<>());
//        setRecentOneWays(new ArrayList<>());
//        setRecentTwoWays(new ArrayList<>());
//        setSelectedUser("");
//        setSelectedTrade("");
    }


    @Override
    public List<String> getAllTradeStatus() {
        return allTradeStatus;
    }

    @Override
    public List<String> getStatsTypes() {
        return statsTypes;
    }

    @Override
    public List<String> getTradesShown() {
        return tradesShown;
    }

    @Override
    public List<String> getStatsShown() {
        return statsShown;
    }

    @Override
    public void setStatsShown(List<String> statsShown) {
        List<String> old = this.statsShown;
        this.statsShown = statsShown;
        propertyChangeSupport.firePropertyChange("statsShown", old, this.statsShown);
    }

    @Override
    public void setTradesShown(List<String> tradesShown) {
        List<String> trades = new ArrayList<>();
        for (int i = 0; i < tradesShown.size(); i += 2) {
            String name = tradesShown.get(i);
            String date = tradesShown.get(i + 1);
            trades.add(localizedResources.getString("with") + " " + name + " " +
                    localizedResources.getString("on") + " " + date);
        }

        List<String> old = this.tradesShown;
        this.tradesShown = trades;
        propertyChangeSupport.firePropertyChange("tradesShown", old, this.tradesShown);
    }
}
