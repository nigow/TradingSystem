package presenters;

import java.util.List;

/**
 * interface for suggesting a time and place for a new trade
 * @author Catherine
 */
public interface TradeCreatorPresenter {
    /**
     * displays possible actions
     * @param tradeCreatorOptions possible actions user can choose from
     * @return index of chosen action
     */
    public String displayTradeCreatorOptions(List<String> tradeCreatorOptions);

    /**
     * suggest a time and place for trade
     * @return time and place user suggested
     */
    public String[] suggestTimePlace();

    /**
     * tells user that their input was invalid
     */
    public void invalidInput();
}