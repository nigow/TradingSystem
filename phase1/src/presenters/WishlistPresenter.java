package presenters;

/**
 * interface for managing the wishlist and starting trades
 * @author Catherine
 */
public interface WishlistPresenter {

    /**
     * displays user's wishlist
     */
    public void displayWishlist();

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