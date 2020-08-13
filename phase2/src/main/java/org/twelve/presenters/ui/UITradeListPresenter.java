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

//    @Override
//    public void setStatsTypes() {
//        List<String> options = new ArrayList<>();
//        options.add("Top Trading Partners");
//        options.add("Recent Items in One-Way Trades");
//        options.add("Recent Items in Two-Way Trades");
//        List<String> old = this.statsTypes;
//        this.statsTypes = options;
//        propertyChangeSupport.firePropertyChange("statsTypes", old, this.statsTypes);
//    }
//
//    @Override
//    public void setAllTradeStatus() {
//        List<String> options = new ArrayList<>();
//        options.add("Unconfirmed Trades");
//        options.add("Confirmed Trades");
//        options.add("Completed Trades");
//        options.add("Canceled Trades");
//        List<String> old = this.allTradeStatus;
//        this.allTradeStatus = options;
//        propertyChangeSupport.firePropertyChange("allTradeStatus", old, this.allTradeStatus);
//    }

//    private final ResourceBundle localizedResources;
//    private List<String> allTrades;
//    private List<String> recentOneWays;
//    private List<String> recentTwoWays;
//    private List<String> tradingPartners;
//    private String selectedTrade;
//    private String yourItems;
//    private String theirItems;
//    private String locationChosen;
//    private String dateChosen;
//    private int hourChosen;
//    private int minuteChosen;
//    private String topTradingPartners;
//
//    public UITradeCollectionPresenter(ResourceBundle localizedResources) {
//        super();
//        this.localizedResources = localizedResources;
//        setAllUsers(new ArrayList<>());
//        setAllTrades(new ArrayList<>());
//        setRecentOneWays(new ArrayList<>());
//        setRecentTwoWays(new ArrayList<>());
//        setSelectedUser("");
//        setSelectedTrade("");
//    }
//
//    public void setAllUsers(List<String> allUsers) {
//        List<String> oldUsers = this.allUsers;
//        this.allUsers = allUsers;
//        propertyChangeSupport.firePropertyChange("allUsers", oldUsers, allUsers);
//    }
//
//    @Override
//    public List<String> getAllTrades() {
//        return allTrades;
//    }
//
//    @Override
//    public void setAllTrades(List<String> allTrades) {
//        List<String> oldTrades = this.allTrades;
//        this.allTrades = allTrades;
//        propertyChangeSupport.firePropertyChange("allTrades", oldTrades, allTrades);
//    }
//
//    @Override
//    public List<String> getAllUsers() {
//        return allUsers;
//    }
//
//    public void setHourChosen(int i) {
//        int oldHourChosen = hourChosen;
//        hourChosen = i;
//        propertyChangeSupport.firePropertyChange("hourChosen", oldHourChosen, i);
//    }
//
//    public int getHourChosen() {return hourChosen;}
//
//    public void setMinuteChosen(int i) {
//        int oldMinuteChosen = minuteChosen;
//        minuteChosen = i;
//        propertyChangeSupport.firePropertyChange("minuteChosen", oldMinuteChosen, i);
//    }
//
//    @Override
//    public List<String> getRecentOneWays() {
//        return recentOneWays;
//    }
//
//    @Override
//    public void setRecentOneWays(List<String> recentOneWays) {
//        List<String> oldRecentOneWays = this.recentOneWays;
//        this.recentOneWays = recentOneWays;
//        propertyChangeSupport.firePropertyChange("recentOneWays", oldRecentOneWays, recentOneWays);
//    }
//
//    @Override
//    public List<String> getRecentTwoWays() {
//        return recentTwoWays;
//    }
//
//    @Override
//    public void setRecentTwoWays(List<String> recentTwoWays) {
//        List<String> oldRecentTwoWays = this.recentTwoWays;
//        this.recentTwoWays = recentTwoWays;
//        propertyChangeSupport.firePropertyChange("recentTwoWays", oldRecentTwoWays, recentTwoWays);
//    }
//
//    @Override
//    public String getSelectedUser() {
//        return selectedUser;
//    }
//
//    @Override
//    public void setSelectedUser(String user) {
//        String oldUser = this.selectedUser;
//        this.selectedUser = user;
//        propertyChangeSupport.firePropertyChange("selectedUser", oldUser, selectedUser);
//    }
//
//    @Override
//    public String getTradingPartners() {
//        return tradingPartners;
//    }
//
//    @Override
//    public void setTradingPartners(String tradingPartners) {
//        String oldTradingPartner = this.tradingPartners;
//        this.tradingPartners = tradingPartners;
//        propertyChangeSupport.firePropertyChange("tradingPartner", oldTradingPartner, tradingPartners);
//    }
//
//    @Override
//    public String getYourItems() {
//        return yourItems;
//    }
//
//    @Override
//    public void setYourItems(List<String> yourItems) {
//        String oldYourItems = this.yourItems;
//        this.yourItems = String.join("", yourItems);
//        propertyChangeSupport.firePropertyChange("yourItems", oldYourItems, yourItems);
//    }
//
//    @Override
//    public String getTheirItems() {
//        return theirItems;
//    }
//
//    @Override
//    public void setTheirItems(List<String> theirItems) {
//        String oldTheirItems = this.theirItems;
//        this.theirItems = String.join("", theirItems);
//        propertyChangeSupport.firePropertyChange("theirItems", oldTheirItems, theirItems);
//    }
//
//    @Override
//    public String getLocationChosen() {
//        return locationChosen;
//    }
//
//    @Override
//    public void setLocationChosen(String location) {
//        String oldLocationChosen = this.locationChosen;
//        this.locationChosen = location;
//        propertyChangeSupport.firePropertyChange("locationChosen", oldLocationChosen, location);
//
//    }
//
//    @Override
//    public String getDateChosen() {
//        return dateChosen;
//    }
//
//    @Override
//    public void setDateChosen(String date) {
//        String oldDateChosen = this.dateChosen;
//        this.dateChosen = date;
//        propertyChangeSupport.firePropertyChange("dateChosen", oldDateChosen, date);
//    }
//
//
//    @Override
//    public String getTopTradingPartners() {
//        return topTradingPartners;
//    }
//
//    @Override
//    public void setTopTradingPartners(List<String> topTradingPartners) {
//        String oldTopTradingPartners = this.topTradingPartners;
//        this.topTradingPartners = String.join(" ", topTradingPartners);
//        propertyChangeSupport.firePropertyChange("", oldTopTradingPartners, topTradingPartners);
//    }
//
//    @Override
//    public String getSelectedTrade() {
//        return selectedTrade;
//    }
//
//    @Override
//    public void setSelectedTrade(String selectedTrade) {
//        String oldSelectedTrade = this.selectedTrade;
//        this.selectedTrade = selectedTrade;
//        propertyChangeSupport.firePropertyChange("", oldSelectedTrade, selectedTrade);
//    }
//
//    public int getMinuteChosen() {return minuteChosen;}

}
