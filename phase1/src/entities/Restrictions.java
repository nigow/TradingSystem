package entities;

/**
 * Represents restrictions for a user's trades.
 *
 * @author Maryam
 */
public class Restrictions {
    /**
     * Number of items a user has to lend more than borrow to be able to make a trade.
     */
    private int lendMoreThanBorrow;

    /**
     * Maximum number of incomplete trades before a user's account is frozen.
     */
    private int maxIncompleteTrade;

    /**
     * Maximum number of trades a user can have in one week.
     */
    private int maxWeeklyTrade;

    /**
     * Creates Restrictions based on number of items a user has to lend more than borrow to be able to trade, the
     * maximum number of incomplete trades before an account is frozen, and the maximum number of trades a user
     * can have in one week.
     *
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade.
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen.
     * @param maxWeeklyTrade     Maximum number of trades a user can have in one week.
     */
    public Restrictions(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
        this.maxIncompleteTrade = maxIncompleteTrade;
        this.maxWeeklyTrade = maxWeeklyTrade;
    }

    /**
     * Returns the number of items a user has to lend more than borrow to be able to trade.
     *
     * @return Number of items a user has to lend more than borrow to be able to make a trade.
     */
    public int getLendMoreThanBorrow() {
        return lendMoreThanBorrow;
    }

    /**
     * Returns the maximum number of incomplete trades before a user's account is frozen.
     *
     * @return Maximum number of incomplete trades before a user's account is frozen.
     */
    public int getMaxIncompleteTrade() {
        return maxIncompleteTrade;
    }

    /**
     * Sets the number of items a user has to lend more than borrow to be able to make a trade.
     *
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade.
     */
    public void setLendMoreThanBorrow(int lendMoreThanBorrow) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
    }

    /**
     * Sets the maximum number of incomplete trades before a user's account is frozen.
     *
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen.
     */
    public void setMaxIncompleteTrade(int maxIncompleteTrade) {
        this.maxIncompleteTrade = maxIncompleteTrade;
    }

    /**
     * Returns the maximum number of trades a user can have in one week.
     *
     * @return Maximum number of trades a user can have in one week.
     */
    public int getMaxWeeklyTrade() {
        return maxWeeklyTrade;
    }

    /**
     * Changes the maximum number of trades a user can have in one week.
     *
     * @param maxWeeklyTrade Maximum number of trades a user can have in one week.
     */
    public void setMaxWeeklyTrade(int maxWeeklyTrade) {
        this.maxWeeklyTrade = maxWeeklyTrade;
    }
}
