package presenters;

import java.util.ArrayList;

/**
 * interface allowing administrator to manage restrictions
 * @author Catherine
 */
public interface RestrictionPresenter {
    /**
     * displays possible actions and returns index of chosen action
     */
    public int displayRestrictionOptions(String[] restrictionOptions);

    /**
     * displays current value for the restriction and returns updated value
     */
    public int changeLendMoreThanBorrow(int lendMoreThanBorrow);

    /**
     * displays current value for the restriction and returns updated value
     */
    public int changeMaxIncompleteTrades(int maxIncompleteTrades);

    /**
     * displays current value for the restriction and returns updated value
     */
    public int changeMaxWeeklyTrades(int weeklyTrades);

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}