package org.twelve.presenters;

import java.util.List;

/**
 * Interface for managing thresholds.
 *
 * @author Catherine
 */
public interface ThresholdPresenter {
    /**
     * Displays possible actions.
     *
     * @param thresholdOptions Possible actions user can choose from
     * @return Index of chosen action
     */
    String displayThresholdOptions(List<String> thresholdOptions);

    /**
     * Displays current value for the restriction.
     *
     * @param lendMoreThanBorrow Current restriction for how many more items users can lend than borrow
     * @return Updated restriction value
     */
    String changeLendMoreThanBorrow(int lendMoreThanBorrow);

    /**
     * Displays current value for the restriction.
     *
     * @param maxIncompleteTrades Current restriction as to how many incomplete trades there are
     * @return Updated restriction value
     */
    String changeMaxIncompleteTrades(int maxIncompleteTrades);

    /**
     * Displays current value for the restriction.
     *
     * @param weeklyTrades Current restrictions for number of trades a user can make weekly
     * @return Updated restriction value
     */
    String changeMaxWeeklyTrades(int weeklyTrades);

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Tells user that the thresholds has changed.
     */
    void displayChangedThresholds();

    /**
     * Message letting user known they can adjust the lend vs. borrow limit.
     * @return the string message
     */
    String lendBorrowLimit();

    /**
     * Message letting user known they can adjust the maximum number of incomplete trades.
     * @return the string message
     */
    String maxIncompleteTrades();

    /**
     * Message letting user known they can maximum number of weekly trades.
     * @return the string message
     */
    String maxWeeklyTrades();

    /**
     * Message letting user known they can return to the main menu.
     * @return the string message
     */
    String returnToMainMenu();
}