package org.twelve.gateways.ram;

import org.twelve.gateways.ThresholdsGateway;
import org.twelve.usecases.ThresholdRepository;

public class InMemoryThresholdsGateway implements ThresholdsGateway {

    /**
     * pseudo-external storage of thresholds
     */
    private int lendMoreThanBorrow;
    private int maxIncompleteTrade;
    private int maxWeeklyTrade;
    private int numberOfDays;
    private int numberOfStats;
    private int numberOfEdits;
    private int tradesForTrusted;


    /**
     * Initialize pseudo-external storage of thresholds
     *
     * @param lendMoreThanBorrow Number of items a user has to lend more than borrow to be able to make a trade
     * @param maxIncompleteTrade Maximum number of incomplete trades before a user's account is frozen
     * @param maxWeeklyTrade     Maximum number of trades a user can have in one week
     * @param numberOfDays       Number of days for when a reverse trade is set up after a temporary trade
     * @param numberOfEdits      Number of edits an account can do with a Trade
     * @param numberOfStats      Number of trading statistics an account should see
     * @param tradesForTrusted   The number of trades required to make an account trusted.
     *
     */
    public InMemoryThresholdsGateway(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade,
                      int numberOfDays, int numberOfEdits, int numberOfStats, int tradesForTrusted) {
        this.lendMoreThanBorrow = lendMoreThanBorrow;
        this.maxIncompleteTrade = maxIncompleteTrade;
        this.maxWeeklyTrade = maxWeeklyTrade;
        this.numberOfDays = numberOfDays;
        this.numberOfEdits = numberOfEdits;
        this.numberOfStats = numberOfStats;
        this.tradesForTrusted = tradesForTrusted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(ThresholdRepository thresholdRepository) {
        thresholdRepository.createThresholds(lendMoreThanBorrow, maxIncompleteTrade, maxWeeklyTrade, numberOfDays,
                numberOfEdits, numberOfStats, tradesForTrusted);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int lendMoreThanBorrowNew, int maxIncompleteTradeNew, int maxWeeklyTradeNew,
                        int numberOfDaysNew, int numberOfEditsNew, int numberOfStatsNew, int tradesForTrustedNew) {
        lendMoreThanBorrow = lendMoreThanBorrowNew;
        maxIncompleteTrade = maxIncompleteTradeNew;
        maxWeeklyTrade = maxIncompleteTradeNew;
        numberOfDays = numberOfDaysNew;
        numberOfEdits = numberOfEditsNew;
        numberOfStats = numberOfStatsNew;
        tradesForTrusted = tradesForTrustedNew;
        return true;
    }
}
