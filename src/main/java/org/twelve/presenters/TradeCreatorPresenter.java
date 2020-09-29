package org.twelve.presenters;


import java.util.List;

/**
 * Interface for presenter that deals with the trade creation view.
 */
public interface TradeCreatorPresenter {

    /**
     * Setter for the items to receive list
     *
     * @param itemsToReceive the items to receive
     */
    void setItemsToReceive(List<String> itemsToReceive);

    /**
     * Getter for the items to receive list
     *
     * @return the items to receive
     */
    List<String> getItemsToReceive();

    /**
     * Setter for the peer wishlist list
     *
     * @param wishlist items in your inventory in their wishlist
     */
    void setPeerWishlist(List<String> wishlist);

    /**
     * Getter for the peer wishlist list
     *
     * @return the peer wishlist list
     */
    List<String> getPeerWishlist();

    /**
     * Setter for the items to give list
     *
     * @param itemsToGive items to give
     */
    void setItemsToGive(List<String> itemsToGive);

    /**
     * Getter for the items to give list
     *
     * @return the items to give
     */
    List<String> getItemsToGive();

    /**
     * Setter for the all users list
     *
     * @param allUsers all the users that can be traded with
     */
    void setAllUsers(List<String> allUsers);

    /**
     * Getter for the all users list
     *
     * @return all the users that can be traded with
     */
    List<String> getAllUsers();

    /**
     * Setter for the hour chosen for the trade
     *
     * @param i the hour chosen for thr trade
     */
    void setHourChosen(int i);

    /**
     * Getter for the hour chosen for the trade
     *
     * @return the hour chosen for the trade
     */
    int getHourChosen();

    /**
     * Setter for the minute chosen for the trade
     *
     * @param i the minute chosen for the trade
     */
    void setMinuteChosen(int i);

    /**
     * Getter for the minute chosen for the trade
     *
     * @return the minute chosen for the trade
     */
    int getMinuteChosen();
}
