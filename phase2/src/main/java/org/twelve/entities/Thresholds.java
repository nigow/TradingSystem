package org.twelve.entities;

/**
 * Represents restrictions for a user's trades.
 *
 * @author Maryam
 */
public class Thresholds {

    private int lendMoreThanBorrow;
    private int maxIncompleteTrade;
    private int maxWeeklyTrade;
    private int numberOfDays;
    private int numberOfStats;
    private int numberOfEdits;
    private int TradesForTrusted;

    /**
     * Creates Thresholds used throughout the system.
     *
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen
     * @param maxWeeklyTrade     Maximum number of trades a user can have in one week
     * @param numberOfDays       Number of days for when a reverse trade is set up after a temporary trade
     * @param numberOfEdits      Number of edits an account can do with a Trade
     * @param numberOfStats      Number of trading statistics an account should see
     * @param TradesForTrusted   The number of trades required to make an account trusted.
     */
    public Thresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade,
                      int numberOfDays, int numberOfEdits, int numberOfStats, int TradesForTrusted) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
        this.maxIncompleteTrade = maxIncompleteTrade;
        this.maxWeeklyTrade = maxWeeklyTrade;
        this.numberOfDays = numberOfDays;
        this.numberOfEdits = numberOfEdits;
        this.numberOfStats = numberOfStats;
        this.TradesForTrusted = TradesForTrusted;
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

    /**
     * Get the number of days for when a second trade is set after an initial trade.
     *
     * @return The number of days for when a reverse trade is set up after a temporary trade.
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Set the number of days for when a second trade is set after an initial trade.
     *
     * @param numberOfDays The new number of days for when a reverse trade is set up after a temporary trade
     */
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * Get the number of statistics an account should see.
     * This includes two-way trades, one-way trades, top-trading partners.
     *
     * @return The number of stats that an account should see
     */
    public int getNumberOfStats() {
        return numberOfStats;
    }

    /**
     * Sets how many trading statistics can an account see.
     *
     * @param numberOfStats The new number of stats an account should see
     */
    public void setNumberOfStats(int numberOfStats) {
        this.numberOfStats = numberOfStats;
    }

    /**
     * Get the number of edits accounts can perform before a trade is cancelled.
     *
     * @return The number of edits an account can do with a Trade.
     */
    public int getNumberOfEdits() {
        return numberOfEdits;
    }

    /**
     * Set the number of edits accounts can do when trading between users.
     *
     * @param numberOfEdits The number of edits an account can do with a Trade.
     */
    public void setNumberOfEdits(int numberOfEdits) {
        this.numberOfEdits = numberOfEdits;
    }

    /**
     * Get the number of trades it takes for an account to become a trusted community member.
     *
     * @return The number of trades it takes for an account to become a trusted community members.
     */
    public int getRequiredTradesForTrusted() {
        return TradesForTrusted;
    }

    /**
     * Set the number of trades required for an account to become a trusted community member.
     *
     * @param requiredTradesForTrusted number of trades required for an account to become trusted.
     */
    public void setRequiredTradesForTrusted(int requiredTradesForTrusted) {
        this.TradesForTrusted = requiredTradesForTrusted;
    }
}

