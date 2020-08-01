package org.twelve.presenters.ui;

import org.twelve.presenters.ThresholdPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UIThresholdsPresenter extends ObservablePresenter implements ThresholdPresenter {

    private String lendMoreThanBorrow;
    private String maxIncompleteTrade;
    private String maxWeeklyTrade;
    private String numberOfDays;
    private String numberOfStats;
    private String numberOfEdits;

    @Override
    public void setThresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays, int numberOfStats, int numberOfEdits) {

        String oldLendMoreThanBorrow = this.lendMoreThanBorrow;
        this.lendMoreThanBorrow = String.valueOf(lendMoreThanBorrow);
        propertyChangeSupport.firePropertyChange("lendMoreThanBorrow", oldLendMoreThanBorrow, this.lendMoreThanBorrow);

        String oldMaxIncompleteTrade = this.maxIncompleteTrade;
        this.maxIncompleteTrade = String.valueOf(maxIncompleteTrade);
        propertyChangeSupport.firePropertyChange("maxIncompleteTrade", oldMaxIncompleteTrade, this.maxIncompleteTrade);

        String oldMaxWeeklyTrade = this.maxWeeklyTrade;
        this.maxWeeklyTrade = String.valueOf(maxWeeklyTrade);
        propertyChangeSupport.firePropertyChange("maxWeeklyTrade", oldMaxWeeklyTrade, this.maxWeeklyTrade);

        String oldNumberOfDays = this.numberOfDays;
        this.numberOfDays = String.valueOf(numberOfDays);
        propertyChangeSupport.firePropertyChange("numberOfDays", oldNumberOfDays, this.numberOfDays);

        String oldNumberOfStats = this.numberOfStats;
        this.numberOfStats = String.valueOf(numberOfStats);
        propertyChangeSupport.firePropertyChange("numberOfStats", oldNumberOfStats, this.numberOfStats);

        String oldNumberOfEdits = this.numberOfEdits;
        this.numberOfEdits = String.valueOf(numberOfEdits);
        propertyChangeSupport.firePropertyChange("numberOfEdits", oldNumberOfEdits, this.numberOfEdits);

    }

    @Override
    public String getLendMoreThanBorrow() {
        return lendMoreThanBorrow;
    }

    @Override
    public String getMaxIncompleteTrade() {
        return maxIncompleteTrade;
    }

    @Override
    public String getMaxWeeklyTrade() {
        return maxWeeklyTrade;
    }

    @Override
    public String getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public String getNumberOfStats() {
        return numberOfStats;
    }

    @Override
    public String getNumberOfEdits() {
        return numberOfEdits;
    }
}
