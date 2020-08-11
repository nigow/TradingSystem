package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCollectionPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UITradeCollectionPresenter extends ObservablePresenter implements TradeCollectionPresenter {

    private final ResourceBundle localizedResources;
    private List<String> allUsers;
    private List<String> allTrades;
    private List<String> recentOneWays;
    private List<String> recentTwoWays;
    private String selectedUser;
    private String selectedTrade;
    private String tradingPartner;
    private String yourItems;
    private String theirItems;
    private String locationChosen;
    private String dateChosen;
    private int hourChosen;
    private int minuteChosen;
    private String topTradingPartners;

    public UITradeCollectionPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setAllUsers(new ArrayList<>());
        setAllTrades(new ArrayList<>());
        setRecentOneWays(new ArrayList<>());
        setRecentTwoWays(new ArrayList<>());
        setSelectedUser("");
        setSelectedTrade("");
    }

    public void setAllUsers(List<String> allUsers) {
        List<String> oldUsers = this.allUsers;
        this.allUsers = allUsers;
        propertyChangeSupport.firePropertyChange("allUsers", oldUsers, allUsers);
    }

    @Override
    public List<String> getAllTrades() {
        return allTrades;
    }

    @Override
    public void setAllTrades(List<String> allTrades) {

    }

    @Override
    public List<String> getAllUsers() {
        return allUsers;
    }

    public void setHourChosen(int i) {
        int oldHourChosen = hourChosen;
        hourChosen = i;
        propertyChangeSupport.firePropertyChange("hourChosen", oldHourChosen, i);
    }

    public int getHourChosen() {return hourChosen;}

    public void setMinuteChosen(int i) {
        int oldMinuteChosen = minuteChosen;
        minuteChosen = i;
        propertyChangeSupport.firePropertyChange("minuteChosen", oldMinuteChosen, i);
    }

    @Override
    public List<String> getRecentOneWays() {
        return recentOneWays;
    }

    @Override
    public void setRecentOneWays(List<String> recentOneWays) {

    }

    @Override
    public List<String> getRecentTwoWays() {
        return recentTwoWays;
    }

    @Override
    public void setRecentTwoWays(List<String> recentTwoWays) {

    }

    @Override
    public String getSelectedUser() {
        return selectedUser;
    }

    @Override
    public void setSelectedUser(String user) {

    }

    @Override
    public String getTradingPartner() {
        return tradingPartner;
    }

    @Override
    public void setTradingPartner(String tradingPartner) {

    }

    @Override
    public String getYourItems() {
        return yourItems;
    }

    @Override
    public void setYourItems(List<String> yourItems) {

    }

    @Override
    public String getTheirItems() {
        return theirItems;
    }

    @Override
    public void setTheirItems(List<String> theirItems) {

    }

    @Override
    public String getLocationChosen() {
        return locationChosen;
    }

    @Override
    public void setLocationChosen(String location) {

    }

    @Override
    public String getDateChosen() {
        return dateChosen;
    }

    @Override
    public void setDateChosen(String date) {

    }


    @Override
    public String getTopTradingPartners() {
        return topTradingPartners;
    }

    @Override
    public void setTopTradingPartners(List<String> topTradingPartners) {

    }

    @Override
    public String getSelectedTrade() {
        return selectedTrade;
    }

    @Override
    public void setSelectedTrade(String selectedTrade) {

    }

    public int getMinuteChosen() {return minuteChosen;}

}
