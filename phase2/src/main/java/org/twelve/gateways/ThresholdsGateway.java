package org.twelve.gateways;

import org.twelve.usecases.system.ThresholdRepository;

/**
 * A gateway for Thresholds that interacts with external storage.
 */
public interface ThresholdsGateway {

    /**
     * Method that syncs the external storage information into the in-memory thresholds repository.
     *
     * @param thresholdRepository local thresholds repository to populate
     * @return whether or not the population was successful
     */
    boolean populate(ThresholdRepository thresholdRepository);

    /**
     * Method that syncs local thresholds' update to the external storage
     *
     * @param lendMoreThanBorrow       how many items can be lent more than borrowed
     * @param maxIncompleteTrade       maximum trades that can be incomplete
     * @param maxWeeklyTrade           maximum trades a user can make per week
     * @param numberOfDays             number of days of trade
     * @param numberOfStats            number of stats
     * @param requiredTradesForTrusted number of trades that have to be made before getting trusted role
     * @param numberOfEdits            number of edits
     * @return whether or not the population was successful
     */
    boolean save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                 int numberOfEdits, int numberOfStats, int requiredTradesForTrusted);
}
