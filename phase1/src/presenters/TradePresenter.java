package presenters;

import java.util.List;

/**
 * interface to manage a user's trade
 *
 * @author Catherine
 */
public interface TradePresenter {
    /**
     * displays possible actions
     *
     * @param tradeOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayTradeOptions(List<String> tradeOptions);

    /**
     * displays user's ongoing trades
     *
     * @param trades list of ongoing trades
     */
    void displayTrades(List<String> trades);

    /**
     * select trade to edit
     *
     * @return index of selected trade
     */
    String selectTrade();

    /**
     * edit trade's meetup time
     *
     * @return information for trade's new meetup time
     */
    String editTradeTime();

    /**
     * edit trade's meetup date
     *
     * @return information for trade's new meetup date
     */
    String editTradeDate();

    /**
     * edit trade's meetup location
     *
     * @return information for trade's new meetup location
     */
    String editTradeLocation();

    /**
     * displays user's 3 most recent traded items in a one way trade
     *
     * @param recentOneWayTrade user's recent items in a one way trades
     */
    void displayRecentOneWayTrade(List<String> recentOneWayTrade);

    /**
     * displays user's 3 most recent traded items in a two way trade
     *
     * @param recentTwoWayTrade user's recent items in a two way trades
     */
    void displayRecentTwoWayTrade(List<String> recentTwoWayTrade);

    /**
     * displays user's 3 most frequent trading partners
     *
     * @param frequentPartners user's 3 most frequent trading partners
     */
    void displayFrequentPartners(List<String> frequentPartners);

    /**
     * tells user that their input was invalid
     */
    void invalidInput();

    /**
     * presents the user with a custom message
     */
    void showMessage(String message);

    /**
     * presents the user with the option to say yes or no
     *
     * @return user's input y/n
     */
    String yesOrNo();
}