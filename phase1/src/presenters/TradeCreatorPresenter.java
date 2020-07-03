package presenters;

import java.util.List;

/**
 * interface for suggesting a time and place for a new trade
 * @author Catherine
 */
public interface TradeCreatorPresenter {

    /**
     * tells user that their input was invalid
     */
    void invalidInput();

    /**
     * Prompt user whether they would like to perform a two way trade.
     * @return Whether user would like to perform a two way trade.
     */
    String getTwoWayTrade();

    /**
     * Display a certain user's inventory.
     * @param username Username of user whose inventory is being displayed.
     * @param inventory A list of item information.
     */
    void showInventory(String username, List<String> inventory);

    /**
     * Prompt user for the index of an item they would like to receive or give away.
     * @return Index of an item.
     */
    String getItem();

    /**
     * Prompt user for the location they would like their trade to take place at.
     * @return A location.
     */
    String getLocation();

    /**
     * Prompt user for the date they would like their trade to take place on.
     * @return String representation of a date.
     */
    String getDate();

    /**
     * Prompt user for the time they would like their trade to take place during.
     * @return String representation of a time.
     */
    String getTime();

    /**
     * Prompt user whether they would like the trade they are creating to be permanent.
     * @return Whether the trade should be permanent.
     */
    String getIsPerm();
}