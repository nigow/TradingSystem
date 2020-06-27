package presenters;

import java.util.ArrayList;

/**
 * interface for suggesting a time and place for a new trade
 * @author Catherine
 */
public interface TradeCreator {
    /**
     * displays possible actions and returns index of chosen action
     */
    public int displayTradeCreatorOptions(ArrayList<String> tradeCreatorOptions);

    /**
     * returns the suggested time and then place in a string
     */
    public String[] suggestTimePlace();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}