package org.twelve.presenters;

import java.util.List;

/**
 * Interface for the presenter for the trade cancellation view
 */
public interface TradeCancellationPresenter{

    /**
     * Get the list of all the trades
     * @return the list of all the trades
     */
    List<String> getAllTrades();

    /**
     * Set the list of all the trades
     * @param trades the list of all the trades
     */
    void setAllTrades(List<String> trades);
}
