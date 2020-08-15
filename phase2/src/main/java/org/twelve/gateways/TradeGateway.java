package org.twelve.gateways;

import org.twelve.usecases.TradeRepository;

import java.util.List;

/**
 * A gateway for Trades that interacts with external storage.
 *
 */
public interface TradeGateway {

    /**
     * Method that syncs the external storage information into the in-memory trades repository.
     *
     * @param tradeRepository local trades repository to populate
     * @return whether or not the population was successful
     */
    boolean populate(TradeRepository tradeRepository);

    /**
     * Method that syncs a local trade update to the external storage
     *
     * @param tradeId id of the trade
     * @param isPermanent whether or not the trade is permanent
     * @param traderIds list of account ids of trades involved in this trade
     * @param itemIds list of item ids that are involved in this trade
     * @param editedCounter number of times this trade has been edited
     * @param tradeStatus current status of this trade
     * @param tradeCompletions whether or not an item's trade has been completed in the same order as itemIds
     * @param time time of this trade
     * @param location location of this trade
     * @param newTrade true if it is a newly created trade. false if it is meant to update the external information
     * @return whether or not the population was successful
     */
    boolean save(int tradeId, boolean isPermanent, List<Integer> traderIds, List< List<Integer> > itemIds, int editedCounter,
              String tradeStatus, List<Boolean> tradeCompletions, String time, String location, boolean newTrade);

}
