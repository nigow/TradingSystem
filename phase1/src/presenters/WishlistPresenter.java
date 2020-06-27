package presenters;

import java.util.ArrayList;

/**
 * interface for managing the wishlist and starting trades
 * @author Catherine
 */
public interface WishlistPresenter {
    /**
     * displays possible actions
     * @param WishlistOptions possible actions user can choose from
     * @return index of chosen action
     */
    public int displayWishlistOptions(ArrayList<String> WishlistOptions);

    /**
     * displays user's wishlist
     * @param wishlist items on wishlist
     */
    public void displayWishlist(ArrayList<String> wishlist);

    /**
     * start new trade
     * @return index of item which the trade wants to be started with
     */
    public int startTrade();

    /**
     * remove item from wishlist
     * @return index of item which user wants to remove
     */
    public int removeFromWishlist();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}