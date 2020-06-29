package presenters;

import java.util.ArrayList;

/**
 * interface for suggesting a time and place for a new trade
 * @author Catherine
 */
public interface TradeCreator {
    /**
     * displays possible actions
     * @param tradeCreatorOptions possible actions user can choose from
     * @return index of chosen action
     */
    public String displayTradeCreatorOptions(ArrayList<String> tradeCreatorOptions);

    /**
     * suggest a time and place for trade
     * @return time and place user suggested
     */
    public String[] suggestTimePlace();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}