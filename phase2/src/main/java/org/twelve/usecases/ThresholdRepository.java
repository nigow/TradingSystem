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
    private ThresholdsGateway thresholdsGateway;

    /**
     * Set and get thresholds in the system.
     * @param thresholdsGateway An instance of a thresholds gateway.
     */
    public ThresholdRepository(ThresholdsGateway thresholdsGateway) {
        this.thresholdsGateway = thresholdsGateway;
        thresholdsGateway.populate(this);
    }

    /**
     * Change thresholdsGateway to a demo mode instance
     * @param thresholdsGateway A new gateway in demo mode
     */
    void switchToDemoMode(ThresholdsGateway thresholdsGateway) {
        this.thresholdsGateway = thresholdsGateway;
        updateThresholds();
    }

    /**
     * Change thresholdsGateway to a normal mode instance
     * @param thresholdsGateway A new instance
     */
    void switchToNormalMode(ThresholdsGateway thresholdsGateway) {
        this.thresholdsGateway = thresholdsGateway;
        thresholds = null;
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

    /**
     * Get the number of days for when a reverse trade is set up after a temporary trade.
     * @return number of days for when a reverse trade is set up after a temporary trade.
     */
    public int getNumberOfDays() {
        return thresholds.getNumberOfDays();
    }

    /**
     * Set number of days for when a reverse trade is set up after a temporary trade
     * @param numberOfDays New number of days for when a reverse trade is set after a temporary trade.
     */
    public void setNumberOfDays(int numberOfDays) {
        thresholds.setNumberOfDays(numberOfDays);
        updateThresholds();
    }

    /**
     * Get the number of one-way trades, two-way trades, and top-trading partners that
     * an account should see in their trading dashboard.
     * @return Number of trading statistics an account should see.
     */
    public int getNumberOfStats() {
        return thresholds.getNumberOfStats();
    }

    /**
     * Set the number of one-way trades, two-way trades, and top-trading partners that
     * an account should see in their trading dashboard.
     * @param numberOfStats The new number of trading statistics an account should see.
     */
    public void setNumberOfStats(int numberOfStats) {
        thresholds.setNumberOfStats(numberOfStats);
        updateThresholds();
    }

    /**
     * @return Number of edits that each user can do with a trade.
     */
    public int getNumberOfEdits() {
        return thresholds.getNumberOfEdits();
    }

    public void setNumberOfEdits(int numberOfEdits) {
        thresholds.setNumberOfEdits(numberOfEdits);
        updateThresholds();
    }

    /**
     * @return number of trades required to make an account trusted
     */
    public int getRequiredTradesForTrusted() {
        return thresholds.getRequiredTradesForTrusted();
    }

    /**
     * @param requiredTradesForTrusted number of trades required to make an account trusted
     */
    public void setRequiredTradesForTrusted(int requiredTradesForTrusted) {
        thresholds.setRequiredTradesForTrusted(requiredTradesForTrusted);
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

    /**
     * Create the thresholds for this program.
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen
     * @param maxWeeklyTrade     Maximum number of trades a user can have in one week
     * @param numberOfDays       Number of days for when a reverse trade is set up after a temporary trade
     * @param numberOfEdits      Number of edits an account can do with a Trade
     * @param numberOfStats      Number of trading statistics an account should see
     * @param requiredTradesForTrusted   The number of trades required to make an account trusted.
     */
    public void createThresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                                 int numberOfEdits, int numberOfStats, int requiredTradesForTrusted) {
        thresholds = new Thresholds(lendMoreThanBorrow, maxIncompleteTrade, maxWeeklyTrade, numberOfDays,
                numberOfEdits, numberOfStats, requiredTradesForTrusted);
    }

    private void updateThresholds() {
        thresholdsGateway.save(thresholds.getLendMoreThanBorrow(), thresholds.getMaxIncompleteTrade(),
                thresholds.getMaxWeeklyTrade(), thresholds.getNumberOfDays(), thresholds.getNumberOfEdits(),
                thresholds.getNumberOfStats(), thresholds.getRequiredTradesForTrusted());
    }


}
