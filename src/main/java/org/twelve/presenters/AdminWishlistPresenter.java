package org.twelve.presenters;

import java.util.List;

/**
 * Interface for the admin wishlist menu
 */
public interface AdminWishlistPresenter {
    /**
     * Setter for the wishlist of the selected user
     *
     * @param itemsToReceive the wishlist of the selected user
     */
    void setWishlistOfUser(List<String> itemsToReceive);

    /**
     * Getter for the wishlist of the selected user
     *
     * @return the wishlist of the selected user
     */
    List<String> getWishlistOfUser();

    /**
     * Setter for the list of all users
     *
     * @param allUsers all users
     */
    void setAllUsers(List<String> allUsers);

    /**
     * Getter for the list of all users
     *
     * @return all users
     */
    List<String> getAllUsers();

    /**
     * Setter for the description of the item selected
     *
     * @param itemToLend the description of the item selected
     */
    void setSelectedItemDescription(String itemToLend);

    /**
     * Getter for the description of the item selected
     *
     * @return the description of the item selected
     */
    String getSelectedItemDescription();
}
