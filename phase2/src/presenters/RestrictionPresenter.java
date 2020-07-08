package presenters;

import java.util.List;

/**
 * Interface for managing restrictions.
 *
 * @author Catherine
 */
public interface RestrictionPresenter {
    /**
     * Displays possible actions.
     *
     * @param restrictionOptions Possible actions user can choose from
     * @return Index of chosen action
     */
    String displayRestrictionOptions(List<String> restrictionOptions);

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
     * Presents the user with a custom message.
     *
     * @param message Message to display
     */
    void showMessage(String message);
}