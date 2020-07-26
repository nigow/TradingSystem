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
     * Number of days of a trade.
     */
    private int numberOfDays;

    /**
     * Number of stats.
     */
    private int numberOfStats;

    /**
     * Number of edits allowed.
     */
    private int numberOfEdits;

    /**
     * Creates Restrictions based on number of items a user has to lend more than borrow to be able to trade, the
     * maximum number of incomplete trades before an account is frozen, and the maximum number of trades a user
     * can have in one week.
     *
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen
     * @param maxWeeklyTrade     Maximum number of trades a user can have in one week
     */
    public Restrictions(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays, int numberOfEdits, int numberOfStats) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
        this.maxIncompleteTrade = maxIncompleteTrade;
        this.maxWeeklyTrade = maxWeeklyTrade;
        this.numberOfDays = numberOfDays;
        this.numberOfEdits = numberOfEdits;
        this.numberOfStats = numberOfStats;
    }

    /**
     * Returns the number of items a user has to lend more than borrow to be able to trade.
     *
     * @return Number of items a user has to lend more than borrow to be able to make a trade
     */
    public int getLendMoreThanBorrow() {
        return lendMoreThanBorrow;
    }

    /**
     * Returns the maximum number of incomplete trades before a user's account is frozen.
     *
     * @return Maximum number of incomplete trades before a user's account is frozen
     */
    public int getMaxIncompleteTrade() {
        return maxIncompleteTrade;
    }

    /**
     * Sets the number of items a user has to lend more than borrow to be able to make a trade.
     *
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade
     */
    public void setLendMoreThanBorrow(int lendMoreThanBorrow) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
    }

    /**
     * Sets the maximum number of incomplete trades before a user's account is frozen.
     *
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen
     */
    public void setMaxIncompleteTrade(int maxIncompleteTrade) {
        this.maxIncompleteTrade = maxIncompleteTrade;
    }

    /**
     * Returns the maximum number of trades a user can have in one week.
     *
     * @return Maximum number of trades a user can have in one week
     */
    public int getMaxWeeklyTrade() {
        return maxWeeklyTrade;
    }

    /**
     * Changes the maximum number of trades a user can have in one week.
     *
     * @param maxWeeklyTrade Maximum number of trades a user can have in one week
     */
    public void setMaxWeeklyTrade(int maxWeeklyTrade) {
        this.maxWeeklyTrade = maxWeeklyTrade;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getNumberOfStats() {
        return numberOfStats;
    }

    public void setNumberOfStats(int numberOfStats) {
        this.numberOfStats = numberOfStats;
    }

    public int getNumberOfEdits() {
        return numberOfEdits;
    }

    public void setNumberOfEdits(int numberOfEdits) {
        this.numberOfEdits = numberOfEdits;
    }
}
