package org.twelve.gateways.csv;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;

import java.util.List;

/**
 * A gateway for interacting with the persistent storage for trades.
 */
public interface TradeGateway {

    /**
     * Given a trade's ID, return the corresponding Trade object.
     *
     * @param id ID of desired trade
     * @return Trade possessing the given ID (null if an invalid ID was given)
     */
    Trade findTradeById(int id);

    /**
     * Given a trade's ID, return the time + place it's happening at.
     *
     * @param id ID of desired trade
     * @return Time + place of trade possessing the given ID (null if an invalid ID was given)
     */
    TimePlace findTimePlaceById(int id);

    /**
     * Given a trade and the time + place it's taking place at, save its information to persistent storage.
     *
     * @param trade     Trade being saved
     * @param timePlace Time + place trade is happening at
     * @return Whether trade's persistent storage was successfully updated or not
     */
    boolean updateTrade(Trade trade, TimePlace timePlace);

    /**
     * Retrieve every trade in the system.
     *
     * @return List of every trade in the system
     */
    List<Trade> getAllTrades();

    /**
     * Return an ID that does not belong to any trade at the time the method is called.
     *
     * @return An unused ID
     */
    int generateValidId();

}
