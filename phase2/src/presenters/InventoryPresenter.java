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
     * Displays all approved items.
     * @param approvedItems list of approved items
     */
    void displayApprovedItems(List<String> approvedItems);

    /**
     * Displays all items, including pending items.
     * @param allItems list of all items
     */
    void displayAllItems(List<String> allItems);

    /**
     * Displays the pending items.
     * @param pendingItems list of pending items
     */
    void displayUserPendingItems(List<String> pendingItems);

    /**
     * Displays items available to trade
     * @param availableItems list of available items
     */
    void displayAvailableItems(List<String> availableItems);

    /**
     * Displays all pending items.
     * @param pendingItems list of pending items
     */
    void displayAllPendingItems(List<String> pendingItems);

    /**
     * Displays all items belonging to other users
     * @param othersItems list of items from other users
     */
    void displayOthersItems(List<String> othersItems);

    /**
     * Displays all of the user's items.
     * @param userItems list of user's items
     */
    void displayUserItems(List<String> userItems);

    /**
     * Tells user the input does not correspond to any option.
     */
    void displayDoesNotCorrespond();

    /**
     * Tells user they're not allowed to include commas in the input.
     */
    void commaError();

    /**
     * Tells user that an item was not successfully added.
     */
    void itemError();

    /**
     * Tells user that an item was successfully added.
     */
    void itemSuccess();

    /**
     * Tells user that an item is pending approval.
     */
    void pending();

    /**
     * Tells user that an item was successfully removed.
     */
    void itemRemovalSuccess();

    /**
     * Tells user that an item was successfully removed.
     */
    void itemRemovalError();

    /**
     * Tells user that an item has been approved by an admin.
     */
    void itemApproved();

    /**
     * Shows option to view all approved items.
     * @return the string message
     */
    String viewAllApproved();

    /**
     * Shows option to view user's approved items.
     * @return the string message
     */
    String viewYourApproved();
    /**
     * Shows option to view user's approved items.
     * @return the string message
     */
    String viewYourPending();

    /**
     * Shows option to view all tradable items.
     * @return the string message
     */
    String viewAllAvailable();

    /**
     * Shows option to add an item to the wishlist.
     * @return the string message
     */
    String addItemToWishlist();

    /**
     * Shows option to create a new item.
     * @return the string message
     */
    String createNewItem();

    /**
     * Shows option to view all pending items.
     * @return the string message
     */
    String viewAllPending();

    /**
     * Shows option to view approve an item.
     * @return the string message
     */
    String approveAnItem();

    /**
     * Shows option to remove an item from the wishlist.
     * @return the string message
     */
    String removeYourItem();

    /**
     * Shows option to return to home.
     * @return the string message
     */
    String returnToHome();
}