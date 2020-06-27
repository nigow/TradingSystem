package presenters;

/**
 * interface for managing the wishlist and starting trades
 * @author Catherine
 */
public interface WishlistPresenter {

    public void displayWishlist();
    /**
     * displays user's wishlist
     */

    public int startTrade();
    /**
     * returns index of item that user wants to use to start trade
     */

    public int removeromWishlist();
    /**
     * returns index of item that user wants to remove from wishlist
     */

    public void returnToMenu();
    /**
     * returns user to main menu
     */
}