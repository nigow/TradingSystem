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
    public String displayTradeOptions(List<String> tradeOptions) {
        return null;
    }

    @Override
    public void displayTrades(List<String> trades) {

    }

    @Override
    public String selectTrade() {
        return null;
    }

    @Override
    public String editTradeTime() {
        return null;
    }

    @Override
    public String editTradeDate() {
        return null;
    }

    @Override
    public String editTradeLocation() {
        return null;
    }

    @Override
    public void displayRecentOneWayTrade(List<String> recentOneWayTrade) {

    }

    @Override
    public void displayRecentTwoWayTrade(List<String> recentTwoWayTrade) {

    }

    @Override
    public void displayFrequentPartners(List<String> frequentPartners) {

    }

    @Override
    public String isTradeCompleted() {
        return null;
    }

    @Override
    public void displayTrade(String trade) {

    }

    @Override
    public void displayCancelled() {

    }

    @Override
    public void displayCompleted() {

    }

    @Override
    public void displayConfirmed() {

    }

    @Override
    public void displayRejected() {

    }

    @Override
    public void displayIncomplete() {

    }

    @Override
    public void displayLimitReached() {

    }

    @Override
    public void displayFuture() {

    }

    @Override
    public void displaySuggestion() {

    }

    @Override
    public String viewTrades() {
        return null;
    }

    @Override
    public String editTrade() {
        return null;
    }

    @Override
    public String twoWayRecent() {
        return null;
    }

    @Override
    public String oneWayRecent() {
        return null;
    }

    @Override
    public String frequentPartners() {
        return null;
    }

    @Override
    public void setSelectedUser(String user) {

    }

    @Override
    public String getSelectedUser() {
        return null;
    }
}
