package presenters;

/**
 * interface to manage a user's trade
 * @author Catherine
 */
public interface TradePresenter {

    /**
     * displays user's ongoing trades
     */
    public void displayTrades();

    /**
     * returns edited trade information
     */
    public String[] editTrade();

    /**
     * displays user's 3 most recent traded items and
     * most frequent trading partners
     */
    public void displayRecentInfo();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}