package org.twelve.presenters.ui;

import org.twelve.presenters.ThresholdPresenter;

/**
 * Class for dealing with the threshold view
 */
public class UIThresholdsPresenter extends ObservablePresenter implements ThresholdPresenter {

    private int lendMoreThanBorrow;
    private int maxIncompleteTrade;
    private int maxWeeklyTrade;
    private int numberOfDays;
    private int numberOfStats;
    private int numberOfEdits;
    private int numberOfTradesUntilTrusted;
    boolean isAdmin;

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLendMoreThanBorrow() {
        return lendMoreThanBorrow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxIncompleteTrade() {
        return maxIncompleteTrade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxWeeklyTrade() {
        return maxWeeklyTrade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfStats() {
        return numberOfStats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfEdits() {
        return numberOfEdits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfTradesUntilTrusted() {
        return numberOfTradesUntilTrusted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsAdmin(boolean isAdmin) {
        boolean oldIsAdmin = this.isAdmin;
        this.isAdmin = isAdmin;
        propertyChangeSupport.firePropertyChange("isAdmin", oldIsAdmin, this.isAdmin);
    }

}
