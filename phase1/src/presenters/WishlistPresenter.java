package presenters;

import java.util.List;

/**
 * interface for managing the wishlist and starting trades
 * @author Catherine
 */
public interface WishlistPresenter {
    /**
     * displays possible actions
     * @param wishlistOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayWishlistOptions(List<String> wishlistOptions);

    /**
     * displays user's wishlist
     * @param wishlist items on wishlist
     */
    void displayWishlist(List<String> wishlist);

    /**
     * start new trade
     * @return index of item which the trade wants to be started with
     */
    String startTrade();

    /**
     * remove item from wishlist
     * @return index of item which user wants to remove
     */
    String removeFromWishlist();

    /**
     * tells user that their input was invalid
     */
    public void invalidInput();

    /**
     * returns user to main menu
     */
    void returnToMenu();
}