package presenters;

import java.util.ArrayList;

/**
 * interface to manage a user's trade
 * @author Catherine
 */
public interface TradePresenter {
    /**
     * displays possible actions
     * @param tradeOptions possible actions user can choose from
     * @return index of chosen action
     */
    public int displayTradeOptions(ArrayList<String> tradeOptions);

    /**
     * displays user's ongoing trades
     * @param trades list of ongoing trades
     */
    public void displayTrades(ArrayList<String> trades);
    /**
     * select trade to edit
     * @return index of selected trade
     */
    public int selectTrade();

    /**
     * edit trade
     * @return information for edited trade
     */
    public String[] editTrade();

    /**
     * displays user's 3 most recent traded items and
     * most frequent trading partners
     * @param recentInfo user's information on trades
     */
    public void displayRecentInfo(String[] recentInfo);

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}