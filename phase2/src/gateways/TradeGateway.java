package gateways;

import entities.OldTrade;
import entities.TimePlace;

import java.util.List;

/**
 * A gateway for interacting with the persistent storage for trades.
 */
public interface TradeGateway {

    /**
     * Given a trade's ID, return the corresponding OldTrade object.
     *
     * @param id ID of desired trade
     * @return OldTrade possessing the given ID (null if an invalid ID was given)
     */
    OldTrade findTradeById(int id);

    /**
     * Given a trade's ID, return the time + place it's happening at.
     *
     * @param id ID of desired trade
     * @return Time + place of trade possessing the given ID (null if an invalid ID was given)
     */
    TimePlace findTimePlaceById(int id);

    /**
     * Given a oldTrade and the time + place it's taking place at, save its information to persistent storage.
     *
     * @param oldTrade     OldTrade being saved
     * @param timePlace Time + place oldTrade is happening at
     * @return Whether oldTrade's persistent storage was successfully updated or not
     */
    boolean updateTrade(OldTrade oldTrade, TimePlace timePlace);

    /**
     * Retrieve every trade in the system.
     *
     * @return List of every trade in the system
     */
    List<OldTrade> getAllTrades();

    /**
     * Return an ID that does not belong to any trade at the time the method is called.
     *
     * @return An unused ID
     */
    int generateValidId();

}
