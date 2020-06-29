package presenters;

import java.util.ArrayList;

/**
 * interface for the inventory that can create changes to the wishlist
 * @author Catherine
 */
public interface InventoryPresenter {
    /**
     * displays possible actions
     * @param InventoryOptions possible actions user can choose from
     * @return index of chosen action
     */
    public String displayInventoryOptions(ArrayList<String> InventoryOptions);

    /**
     * displays inventory
     * @param inventory list of inventory item
     */
    public void displayInventory(ArrayList<String> inventory);

    /**
     * add to wishlist
     * @return indexes of items that user would like to add to the wishlist
     */

    public String addToWishlist();

    /**
     * remove from inventory
     * @return indexes of item that user would like removed from inventory
     */
    public String removeFromInventory();

    /**
     * displays pending items
     * @param pendingItem list of items pending to be added to the inventory
     */
    public void displayPending(ArrayList<String> pendingItem);

    /**
     * approve chosen items
     * @return indexes of item that can be approved
     */
    public String approveItem();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}