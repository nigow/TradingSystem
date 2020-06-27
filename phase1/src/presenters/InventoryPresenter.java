package presenters;

import java.util.ArrayList;

/**
 * interface for the inventory that can create changes to the wishlist
 * @author Catherine
 */
public interface InventoryPresenter {
    /**
     * displays possible actions and returns index of chosen action
     */
    public int displayInventoryOptions(ArrayList<String> InventoryOptions);

    /**
     * displays inventory
     */
    public void displayInventory(ArrayList<String> inventory);

    /**
     * displays possible actions and returns index of chosen action
     */

    public ArrayList<String> addToWishlist();

    /**
     * returns the index of the item to be removed from the inventory
     */
    public int removeFromInventory();

    /**
     * displays pending items
     */
    public void displayPending(ArrayList<String> pendingItem);

    /**
     * returns indexes of items in the pending list to be approved
     */
    public ArrayList<Integer> approveItem();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}