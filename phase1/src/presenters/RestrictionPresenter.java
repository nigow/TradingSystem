package presenters;

import java.util.ArrayList;

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
    public int displayRestrictionOptions(String[] restrictionOptions);

    /**
     * displays current value for the restriction
     * @param lendMoreThanBorrow current restriction for how many more items users
     *                           can lend than borrow
     * @return updated restriction value
     */
    public int changeLendMoreThanBorrow(int lendMoreThanBorrow);

    /**
     * displays current value for the restriction
     * @param maxIncompleteTrades current restriction as to how many incomplete trades
     *                            there are
     * @return updated restriction value
     */
    public int changeMaxIncompleteTrades(int maxIncompleteTrades);

    /**
     * displays current value for the restriction
     * @param weeklyTrades current restrictions for  number of trades a user can make weekly
     * @return updated restriction value
     */
    public int changeMaxWeeklyTrades(int weeklyTrades);

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}