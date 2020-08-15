package org.twelve.presenters.ui;

import org.twelve.presenters.ThresholdPresenter;

public class UIThresholdsPresenter extends ObservablePresenter implements ThresholdPresenter {

    private int lendMoreThanBorrow;
    private int maxIncompleteTrade;
    private int maxWeeklyTrade;
    private int numberOfDays;
    private int numberOfStats;
    private int numberOfEdits;
    private int numberOfTradesUntilTrusted;
    boolean isAdmin;

    @Override
    public void setThresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                              int numberOfStats, int numberOfEdits, int numberOfTradesUntilTrusted) {
        int oldLendMoreThanBorrow = this.lendMoreThanBorrow;
        this.lendMoreThanBorrow = lendMoreThanBorrow;
        propertyChangeSupport.firePropertyChange("lendMoreThanBorrow", oldLendMoreThanBorrow, this.lendMoreThanBorrow);

        int oldMaxWeeklyTrade = this.maxWeeklyTrade;
        this.maxWeeklyTrade = maxWeeklyTrade;
        propertyChangeSupport.firePropertyChange("maxWeeklyTrade", oldMaxWeeklyTrade, this.maxWeeklyTrade);

        int oldMaxIncompleteTrade = this.maxIncompleteTrade;
        this.maxIncompleteTrade = maxIncompleteTrade;
        propertyChangeSupport.firePropertyChange("maxIncompleteTrade", oldMaxIncompleteTrade, this.maxIncompleteTrade);

        int oldNumberOfDays = this.numberOfDays;
        this.numberOfDays = numberOfDays;
        propertyChangeSupport.firePropertyChange("numberOfDays", oldNumberOfDays, this.numberOfDays);

        int oldNumberOfStats = this.numberOfStats;
        this.numberOfStats = numberOfStats;
        propertyChangeSupport.firePropertyChange("numberOfStats", oldNumberOfStats, this.numberOfStats);

        int oldNumberOfEdits = this.numberOfEdits;
        this.numberOfEdits = numberOfEdits;
        propertyChangeSupport.firePropertyChange("numberOfEdits", oldNumberOfEdits, this.numberOfEdits);

        int oldNumberOfTradesUntilTrusted = this.numberOfTradesUntilTrusted;
        this.numberOfTradesUntilTrusted = numberOfTradesUntilTrusted;
        propertyChangeSupport.firePropertyChange("numberOfTradesUntilTrusted", oldNumberOfTradesUntilTrusted, this.numberOfTradesUntilTrusted);
    }

    @Override
    public int getLendMoreThanBorrow() {
        return lendMoreThanBorrow;
    }

    @Override
    public int getMaxIncompleteTrade() {
        return maxIncompleteTrade;
    }

    @Override
    public int getMaxWeeklyTrade() {
        return maxWeeklyTrade;
    }

    @Override
    public int getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public int getNumberOfStats() {
        return numberOfStats;
    }

    @Override
    public int getNumberOfEdits() {
        return numberOfEdits;
    }

    @Override
    public int getNumberOfTradesUntilTrusted() {
        return numberOfTradesUntilTrusted;
    }

    @Override
    public boolean getIsAdmin() {
        return isAdmin;
    }

    @Override
    public void setIsAdmin(boolean isAdmin) {
        boolean oldIsAdmin = this.isAdmin;
        this.isAdmin = isAdmin;
        propertyChangeSupport.firePropertyChange("isAdmin", oldIsAdmin, this.isAdmin);
    }

}
