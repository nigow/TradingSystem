package entities;

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
     * Creates Restrictions based on number of items a user has to lend more than borrow to be able to trade, and the
     * maximum number of incomplete trades before an account is frozen.
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade.
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen.
     */
    public Restrictions(int lendMoreThanBorrow, int maxIncompleteTrade) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
        this.maxIncompleteTrade = maxIncompleteTrade;
    }

    /**
     * Returns the number of items a user has to lend more than borrow to be able to trade.
     * @return Number of items a user has to lend more than borrow to be able to make a trade.
     */
    public int getLendMoreThanBorrow() {
        return lendMoreThanBorrow;
    }

    /**
     * Returns the maximum number of incomplete trades before a user's account is frozen.
     * @return Maximum number of incomplete trades before a user's account is frozen.
     */
    public int getMaxIncompleteTrade() {
        return maxIncompleteTrade;
    }

    /**
     * Sets the number of items a user has to lend more than borrow to be able to make a trade.
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade.
     */
    public void setLendMoreThanBorrow(int lendMoreThanBorrow) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
    }

    /**
     * Sets the maximum number of incomplete trades before a user's account is frozen.
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen.
     */
    public void setMaxIncompleteTrade(int maxIncompleteTrade) {
        this.maxIncompleteTrade = maxIncompleteTrade;
    }
}
