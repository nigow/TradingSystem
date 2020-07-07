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
     * @param tradeOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayTradeOptions(List<String> tradeOptions);

    /**
     * Displays user's ongoing trades.
     *
     * @param trades list of ongoing trades
     */
    void displayTrades(List<String> trades);

    /**
     * Select trade to edit.
     *
     * @return index of selected trade
     */
    String selectTrade();

    /**
     * Edit trade's meetup time.
     *
     * @return information for trade's new meetup time
     */
    String editTradeTime();

    /**
     * Edit trade's meetup date.
     *
     * @return information for trade's new meetup date
     */
    String editTradeDate();

    /**
     * Edit trade's meetup location.
     *
     * @return information for trade's new meetup location
     */
    String editTradeLocation();

    /**
     * Displays user's 3 most recent traded items in a one way trade.
     *
     * @param recentOneWayTrade user's recent items in a one way trades
     */
    void displayRecentOneWayTrade(List<String> recentOneWayTrade);

    /**
     * Displays user's 3 most recent traded items in a two way trade.
     *
     * @param recentTwoWayTrade user's recent items in a two way trades
     */
    void displayRecentTwoWayTrade(List<String> recentTwoWayTrade);

    /**
     * Displays user's 3 most frequent trading partners.
     *
     * @param frequentPartners user's 3 most frequent trading partners
     */
    void displayFrequentPartners(List<String> frequentPartners);

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Presents the user with a custom message.
     */
    void showMessage(String message);

    /**
     * Presents the user with the option to say yes or no.
     *
     * @return user's input y/n
     */
    String yesOrNo();
}