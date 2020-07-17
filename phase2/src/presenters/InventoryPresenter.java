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
     * @param InventoryOptions Possible actions user can choose from
     * @return Index of chosen action
     */
    String displayInventoryOptions(List<String> InventoryOptions);

    /**
     * Displays inventory.
     *
     * @param inventory List of inventory items
     */
    void displayInventory(List<String> inventory);

    /**
     * Add to wishlist.
     *
     * @return Indexes of items that user would like to add to the wishlist
     */

    String addToWishlist();

    /**
     * Asks user what they want to name their item.
     *
     * @return Name of the Item the user wishes to create
     */
    String askName();

    /**
     * Asks user for their item's description.
     *
     * @return Description of the Item the user wishes to create
     */
    String askDescription();

    /**
     * Ask user to confirm their item.
     *
     * @param name        Name that the user has given
     * @param description Description that the user has given
     * @return Description of the item the user wishes to create
     */
    String confirmItem(String name, String description);

    /**
     * Remove an item from inventory.
     *
     * @return Indexes of item that user would like removed from inventory
     */
    String removeFromInventory();


    /**
     * Approve chosen item.
     *
     * @return Indexes of item that can be approved
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
     * Tells user a customMessage.
     */
    void customMessage(String customMessage);
}