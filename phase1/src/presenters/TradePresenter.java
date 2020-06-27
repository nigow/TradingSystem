package presenters;

/**
 * interface to manage a user's trade
 * @author Catherine
 */
interface TradePresenter {

    public void displayTrades() {
        /**
         * displays user's ongoing trades
         */
    }

    public String[] editTrade() {
        /**
         * returns edited trade information
         */
    }

    public void displayRecentInfo() {
        /**
         * displays user's 3 most recent traded items and
         * most frequent trading partners
         */
    }

    public void returnToMenu() {
        /**
         * returns user to main menu
         */
    }
}