package presenters;

import java.util.ArrayList;

/**
 * interface to manage a user's trade
 * @author Catherine
 */
public interface TradePresenter {
    /**
     * displays possible actions and returns index of chosen action
     */
    public int displayTradeOptions(ArrayList<String> tradeOptions);

    /**
     * displays user's ongoing trades
     */
    public void displayTrades(String[] tradeInfo);

    /**
     * displays edited trade information
     */
    public String[] editTrade();

    /**
     * displays user's 3 most recent traded items and
     * most frequent trading partners
     */
    public void displayRecentInfo(String[] recentInfo);

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}