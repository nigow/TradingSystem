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
     * @param restrictionOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayRestrictionOptions(List<String> restrictionOptions);

    /**
     * Displays current value for the restriction.
     *
     * @param lendMoreThanBorrow current restriction for how many more items users can lend than borrow
     * @return updated restriction value
     */
    String changeLendMoreThanBorrow(int lendMoreThanBorrow);

    /**
     * Displays current value for the restriction.
     *
     * @param maxIncompleteTrades current restriction as to how many incomplete trades there are
     * @return updated restriction value
     */
    String changeMaxIncompleteTrades(int maxIncompleteTrades);

    /**
     * Displays current value for the restriction.
     *
     * @param weeklyTrades current restrictions for number of trades a user can make weekly
     * @return updated restriction value
     */
    String changeMaxWeeklyTrades(int weeklyTrades);

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Presents the user with a custom message.
     */
    void showMessage(String message);
}