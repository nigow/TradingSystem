package presenters;

import java.util.ArrayList;

/**
 * interface for managing the wishlist and starting trades
 * @author Catherine
 */
public interface WishlistPresenter {
    /**
     * displays possible actions and returns index of chosen action
     */
    public int displayWishlistOptions(ArrayList<String> WishlistOptions);

    /**
     * displays user's wishlist
     */
    public void displayWishlist(ArrayList<String> wishlist);

    /**
     * returns index of item that user wants to use to start trade
     */
    public int startTrade();

    /**
     * returns index of item that user wants to remove from wishlist
     */
    public int removeFromWishlist();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}