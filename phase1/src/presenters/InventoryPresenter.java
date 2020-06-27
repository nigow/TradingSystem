package presenters;

/**
 * interface for the inventory that can create changes to the wishlist
 * @author Catherine
 */
public interface InventoryPresenter {
    /**
     * displays inventory
     */
    public void displayInventory();

    /**
     * using the index of an item, return the item's information for the wishlist
     */
    public String[] addToWishlist();

    /**
     * returns the index of the item to be removed from the inventory
     */
    public int removeFromInventory();

    /**
     * displays pending items
     */
    public void displayPending();

    /**
     * returns indexes of items in the pending list to be approved
     */
    public int[] approveItem();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}