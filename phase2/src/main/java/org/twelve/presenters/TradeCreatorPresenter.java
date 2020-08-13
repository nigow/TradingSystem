package org.twelve.presenters;


import java.util.List;

/**
 * Interface for presenter that deals with the trade creation view.
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public interface TradeCreatorPresenter {
    void isPermanent(boolean isPermanent);
    boolean getIsPermanent();
    void setHourChosen(int i);
    int getHourChosen();
    void setMinuteChosen(int i);
    int getMinuteChosen();
    void setItemsToReceive(List<String> itemsToReceive);
    List<String> getItemsToReceive();
    void setPeerWishlist(List<String> wishlist);
    List<String> getPeerWishlist();
    void setItemsToGive(List<String> itemsToGive);
    List<String> getItemsToGive();
    void setAllUsers(List<String> allUsers);
    List<String> getAllUsers();
    void setSelectedUser(String user);
    String getSelectedUser();
    void setSelectedItemToLend(String itemToLend);
    String getSelectedItemToLend();
    void setSelectedItemToBorrow(String itemToBorrow);
    String getSelectedItemToBorrow();
    boolean getCreatedTrade();
    void setCreatedTrade(boolean createdTrade);


}
