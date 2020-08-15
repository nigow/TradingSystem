package org.twelve.presenters;


import java.util.List;

/**
 * Interface for presenters for the trade list view
 */
public interface TradeListPresenter {

    /**
     * Getter for all the trade statuses
     *
     * @return the trade statuses
     */
    List<String> getAllTradeStatus();

    /**
     * Getter for the types of stats
     *
     * @return the types of stats
     */
    List<String> getStatsTypes();

    /**
     * Getter for the trades shown
     *
     * @return the trades shown
     */
    List<String> getTradesShown();

    /**
     * Getter for the stats shown
     *
     * @return the stats shown
     */
    List<String> getStatsShown();

    /**
     * Setter for the trades shown
     *
     * @param tradesShown the trades shown
     */
    void setTradesShown(List<String> tradesShown);

    /**
     * Setter for the stats shown
     *
     * @param statsShown the stats shown
     */
    void setStatsShown(List<String> statsShown);

}
