package presenters;

/**
 * interface for suggesting a time and place for a new trade
 * @author Catherine
 */
interface TradeCreator {

    public String[] suggestTimePlace() {
        /**
         * returns the suggested time and then place in a string
         */
    }

    public void returnToMenu() {
        /**
         * returns user to main menu
         */
    }
}