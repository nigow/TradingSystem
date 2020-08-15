package org.twelve.presenters;

/**
 * Interface for managing thresholds.
 *
 * @author Catherine
 */
public interface ThresholdPresenter {

    /**
     * Set the threshold values for this presenter
     *
     * @param lendMoreThanBorrow         the number of times you can lend more than borrow
     * @param maxIncompleteTrade         the number of incomplete trades you can have
     * @param maxWeeklyTrade             the number of times you can trade in a week
     * @param numberOfDays               the number of days that a reverse trade happens after the initial temporary trade
     * @param numberOfStats              the number of one-way trades, two-way trades, and top-trading partners that
     *                                   an account should see in their trading dashboard.
     * @param numberOfEdits              the number of times a trade can be edited
     * @param numberOfTradesUntilTrusted the number of trades that can be made until a user can be trusted
     */
    void setThresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                       int numberOfStats, int numberOfEdits, int numberOfTradesUntilTrusted);

    /**
     * Getter for the number of times you can lend more than borrow
     *
     * @return the number of times you can lend more than borrow
     */
    int getLendMoreThanBorrow();

    /**
     * Getter for the number of incomplete trades you can have
     *
     * @return the number of incomplete trades you can have
     */
    int getMaxIncompleteTrade();

    /**
     * Getter for the number of times you can trade in a week
     *
     * @return the number of times you can trade in a week
     */
    int getMaxWeeklyTrade();

    /**
     * Getter for the number of days that a reverse trade happens after the initial temporary trade
     *
     * @return the number of days that a reverse trade happens after the initial temporary trade
     */
    int getNumberOfDays();

    /**
     * Getter for the number of one-way trades, two-way trades, and top-trading partners that an account
     * should see in their trading dashboard.
     *
     * @return the number of one-way trades, two-way trades, and top-trading partners that an account should see
     * in their trading dashboard.
     */
    int getNumberOfStats();

    /**
     * Getter for the number of times a trade can be edited
     *
     * @return the number of times a trade can be edited
     */
    int getNumberOfEdits();

    /**
     * Getter for the number of trades that can be made until a user can be trusted
     *
     * @return the number of trades that can be made until a user can be trusted
     */
    int getNumberOfTradesUntilTrusted();

    /**
     * Getter for if the user is an admin
     *
     * @return if the user is an admin
     */
    boolean getIsAdmin();

    /**
     * Setter for if the user is an admin
     *
     * @param isAdmin if the user is an admin
     */
    void setIsAdmin(boolean isAdmin);
}