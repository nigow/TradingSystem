package presenters;

/**
 * interface for suggesting a time and place for a new trade
 * @author Catherine
 */
public interface TradeCreator {

    /**
     * returns the suggested time and then place in a string
     */
    public String[] suggestTimePlace();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}