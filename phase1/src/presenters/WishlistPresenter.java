package presenters;

import java.util.List;

/**
 * Interface for managing the wishlist and starting trades.
 *
 * @author Catherine
 */
public interface WishlistPresenter {
    /**
     * Displays possible actions.
     *
     * @param wishlistOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayWishlistOptions(List<String> wishlistOptions);

    /**
     * Displays user's wishlist.
     *
     * @param wishlist items on wishlist
     */
    void displayWishlist(List<String> wishlist);

    /**
     * Start new trade.
     *
     * @return index of item which the trade wants to be started with
     */
    String startTrade();

    /**
     * Remove item from wishlist.
     *
     * @return index of item which user wants to remove
     */
    String removeFromWishlist();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();
}