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
     * @param wishlistOptions Possible actions user can choose from
     * @return Index of chosen action
     */
    String displayWishlistOptions(List<String> wishlistOptions);

    /**
     * Displays user's wishlist.
     *
     * @param wishlist Items on wishlist
     */
    void displayWishlist(List<String> wishlist);

    /**
     * Start new trade.
     *
     * @return Index of item which the trade wants to be started with
     */
    String startTrade();

    /**
     * Remove item from wishlist.
     *
     * @return Index of item which user wants to remove
     */
    String removeFromWishlist();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();
}