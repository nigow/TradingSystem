package presenters;

/**
 * interface for the inventory that can create changes to the wishlist
 * @author Catherine
 */
interface InventoryPresenter {

    public void displayInventory() {
        /**
         * displays inventory
         */
    }

    public String[] addToWishlist() {
        /**
         * using the index of an item, return the item's information for the wishlist
         */
    }

    public int removeFromInventory() {
        /**
         * returns the index of the item to be removed from the inventory
         */
    }

    public void displayPending() {
        /**
         * displays pending items
         */
    }

    public int[] approveItem() {
        /**
         * returns indexes of items in the pending list to be approved
         */
    }

    public void returnToMenu() {
        /**
         * returns user to main menu
         */
    }
}