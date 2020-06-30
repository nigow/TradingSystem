package presenters;

import java.util.List;

/**
 * interface allowing administrator to manage restrictions
 * @author Catherine
 */
public interface RestrictionPresenter {
    /**
     * displays possible actions
     * @param restrictionOptions possible actions user can choose from
     * @return index of chosen action
     */
    public String displayRestrictionOptions(List<String> restrictionOptions);

    /**
     * displays current value for the restriction
     * @param lendMoreThanBorrow current restriction for how many more items users
     *                           can lend than borrow
     * @return updated restriction value
     */
    public String changeLendMoreThanBorrow(int lendMoreThanBorrow);

    /**
     * displays current value for the restriction
     * @param maxIncompleteTrades current restriction as to how many incomplete trades
     *                            there are
     * @return updated restriction value
     */
    public String changeMaxIncompleteTrades(int maxIncompleteTrades);

    /**
     * displays current value for the restriction
     * @param weeklyTrades current restrictions for  number of trades a user can make weekly
     * @return updated restriction value
     */
    public String changeMaxWeeklyTrades(int weeklyTrades);

    /**
     * tells user that their input was invalid
     */
    public void invalidInput();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}