package presenters;

import java.util.List;

/**
 * Interface for changing inventory and adding to wishlist.
 *
 * @author Catherine
 */
public interface InventoryPresenter {
    /**
     * Displays possible actions for dealing with inventory.
     *
     * @param InventoryOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayInventoryOptions(List<String> InventoryOptions);

    /**
     * Displays inventory.
     *
     * @param inventory list of inventory item
     */
    void displayInventory(List<String> inventory);

    /**
     * Add to wishlist.
     *
     * @return indexes of items that user would like to add to the wishlist
     */

    String addToWishlist();

    /**
     * Asks user what they want to name their item.
     *
     * @return the Name of the Item the user wishes to create
     */
    String askName();

    /**
     * Asks user for their item's description.
     *
     * @return the Description of the Item the user wishes to create
     */
    String askDescription();

    /**
     * Ask user to confirm their item.
     *
     * @param name        The name that the user has given
     * @param description the description that the user has given
     * @return the Description of the Item the user wishes to create
     */
    String confirmItem(String name, String description);

    /**
     * Remove an item from inventory.
     *
     * @return indexes of item that user would like removed from inventory
     */
    String removeFromInventory();


    /**
     * Approve chosen item.
     *
     * @return indexes of item that can be approved
     */
    String approveItem();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Tells user that they've successfully aborted their choice.
     */
    void abortMessage();

    /**
     * Sends a customizable message to the user.
     *
     * @param message the message that will be printed to the user
     */
    void customMessage(String message);

}