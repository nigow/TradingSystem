package org.twelve.usecases;

import org.twelve.entities.Thresholds;
import org.twelve.gateways.ThresholdsGateway;

// TODO javadoc

/**
 * A class to store threshold values of the program.
 * @author Maryam
 */
public class ThresholdRepository {
    private Thresholds thresholds;
    private final ThresholdsGateway thresholdsGateway;

    public ThresholdRepository(ThresholdsGateway thresholdsGateway) {
        this.thresholdsGateway = thresholdsGateway;
        thresholdsGateway.populate(this);
    }

    /**
     * Updates the restriction of the amount of items needed to be lent before borrowing.
     *
     * @param lendMoreThanBorrow Amount of items needed to be lent before borrowing
     */
    public void setLendMoreThanBorrow(int lendMoreThanBorrow) {
        thresholds.setLendMoreThanBorrow(lendMoreThanBorrow);
        updateThresholds();
    }

    /**
     * Updates the restriction of the max number of incomplete trades before an account is frozen.
     *
     * @param maxIncompleteTrade Max number of incomplete trades
     */
    public void setMaxIncompleteTrade(int maxIncompleteTrade) {
        thresholds.setMaxIncompleteTrade(maxIncompleteTrade);
        updateThresholds();
    }

    /**
     * Updates the restriction of the max number of weekly trades before an account is frozen.
     *
     * @param maxWeeklyTrade Max number of weekly trades
     */
    public void setMaxWeeklyTrade(int maxWeeklyTrade) {
        thresholds.setMaxWeeklyTrade(maxWeeklyTrade);
        updateThresholds();
    }

    public int getNumberOfDays() {
        return thresholds.getNumberOfDays();
    }

    public void setNumberOfDays(int numberOfDays) {
        thresholds.setNumberOfDays(numberOfDays);
        updateThresholds();
    }

    public int getNumberOfStats() {
        return thresholds.getNumberOfStats();
    }

    public void setNumberOfStats(int numberOfStats) {
        thresholds.setNumberOfStats(numberOfStats);
        updateThresholds();
    }

    public int getNumberOfEdits() {
        return thresholds.getNumberOfEdits();
    }

    public void setNumberOfEdits(int numberOfEdits) {
        thresholds.setNumberOfEdits(numberOfEdits);
        updateThresholds();
    }

    /**
     * Gets the current restriction for amount of items needed to be lent before borrowing.
     *
     * @return Amount of items needed to be lent before borrowing
     */
    public int getLendMoreThanBorrow() {
        return thresholds.getLendMoreThanBorrow();
    }

    /**
     * Gets the current restriction of the max number of incomplete trades before an account is frozen.
     *
     * @return Max number of incomplete trades
     */
    public int getMaxIncompleteTrade() {
        return thresholds.getMaxIncompleteTrade();
    }

    /**
     * Gets the current restriction of the max number of weekly trades before an account is frozen.
     *
     * @return Max number of weekly trades
     */
    public int getMaxWeeklyTrade() {
        return thresholds.getMaxWeeklyTrade();
    }

    private void updateThresholds() {
        thresholdsGateway.save(thresholds.getLendMoreThanBorrow(), thresholds.getMaxIncompleteTrade(),
                thresholds.getMaxWeeklyTrade(), thresholds.getNumberOfDays(), thresholds.getNumberOfEdits(),
                thresholds.getNumberOfStats());
    }

    public void createThresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                                 int numberOfEdits, int numberOfStats) {
        thresholds = new Thresholds(lendMoreThanBorrow, maxIncompleteTrade, maxWeeklyTrade, numberOfDays,
                numberOfEdits, numberOfStats);
    }
}
