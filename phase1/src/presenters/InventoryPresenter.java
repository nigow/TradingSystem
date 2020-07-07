package presenters;

import java.util.List;

/**
 * interface for the inventory that can create changes to the wishlist
 *
 * @author Catherine
 */
public interface InventoryPresenter {
    /**
     * displays possible actions
     *
     * @param InventoryOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayInventoryOptions(List<String> InventoryOptions);

    /**
     * displays inventory
     *
     * @param inventory list of inventory item
     */
    void displayInventory(List<String> inventory);

    /**
     * add to wishlist
     *
     * @return indexes of items that user would like to add to the wishlist
     */

    String addToWishlist();

    /**
     * ask user what they want to name their item
     *
     * @return the Name of the Item the user wishes to create
     */
    String askName();

    /**
     * ask user for their item's description
     *
     * @return the Description of the Item the user wishes to create
     */
    String askDescription();

    /**
     * ask user to confirm their item
     *
     * @param name        The name that the user has given
     * @param description the description that the user has given
     * @return the Description of the Item the user wishes to create
     */
    String confirmItem(String name, String description);

    /**
     * remove from inventory
     *
     * @return indexes of item that user would like removed from inventory
     */
    String removeFromInventory();


    /**
     * approve chosen items
     *
     * @return indexes of item that can be approved
     */
    String approveItem();

    /**
     * tells user that their input was invalid
     */
    void invalidInput();

    /**
     * tells user that they've successfully aborted their choice
     */
    void abortMessage();

    /**
     * sends a customizable message to the user
     *
     * @param message the message that will be printed to the user
     */
    void customMessage(String message);

}