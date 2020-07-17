package presenters;

import java.util.List;

/**
 * Interface for managing a user's trade.
 *
 * @author Catherine
 */
public interface TradePresenter {
    /**
     * Displays possible actions.
     *
     * @param tradeOptions Possible actions user can choose from
     * @return Index of chosen action
     */
    String displayTradeOptions(List<String> tradeOptions);

    /**
     * Displays user's ongoing trades.
     *
     * @param trades List of ongoing trades
     */
    void displayTrades(List<String> trades);

    /**
     * Select trade to edit.
     *
     * @return Index of selected trade
     */
    String selectTrade();

    /**
     * Edit trade's meetup time.
     *
     * @return Information for trade's new meetup time
     */
    String editTradeTime();

    /**
     * Edit trade's meetup date.
     *
     * @return Information for trade's new meetup date
     */
    String editTradeDate();

    /**
     * Edit trade's meetup location.
     *
     * @return Information for trade's new meetup location
     */
    String editTradeLocation();

    /**
     * Displays user's 3 most recent traded items in a one way trade.
     *
     * @param recentOneWayTrade User's recent items in a one way trades
     */
    void displayRecentOneWayTrade(List<String> recentOneWayTrade);

    /**
     * Displays user's 3 most recent traded items in a two way trade.
     *
     * @param recentTwoWayTrade User's recent items in a two way trades
     */
    void displayRecentTwoWayTrade(List<String> recentTwoWayTrade);

    /**
     * Displays user's 3 most frequent trading partners.
     *
     * @param frequentPartners User's 3 most frequent trading partners
     */
    void displayFrequentPartners(List<String> frequentPartners);

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Lets user say if a trade has been completed.
     *
     * @return User's input y/n
     */
    String isTradeCompleted();

    /**
     * Displays a trade.
     * @param trade a string representation of a string
     */
    void displayTrade(String trade);

    /**
     *Tells user that the trade has been cancelled.
     */
    void displayCancelled();

    /**
     * Tells user that a trade has been completed.
     */
    void displayCompleted();

    /**
     * Tells user that a trade time and place has been confirmed.
     */
    void displayConfirmed();

    /**
     * Tells user that the trade has been rejected.
     */
    void displayRejected();

    /**
     * Tells user that the trade is still incomplete.
     */
    void displayIncomplete();

    /**
     * Tells user that their edit limit has been reached.
     */
    void displayLimitReached();

    /**
     * Tells user that their date/time must be in the future.
     */
    void displayFuture();

    /**
     * Tells user that a new time/date has been suggested.
     */
    void displaySuggestion();
}