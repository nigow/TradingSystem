package org.twelve.presenters;

import java.util.List;

/**
 * Presenter for wishlist
 */
public interface WishlistPresenter {

    /**
     * Set wishlist items.
     *
     * @param wishlistItems List of names of wishlist items.
     */
    void setWishlistItems(List<String> wishlistItems);

    /**
     * Get wishlist items.
     *
     * @return List of names of wishlist items.
     */
    List<String> getWishlistItems();

    /**
     * Set local items.
     *
     * @param localItems List of names of local items.
     */
    void setLocalItems(List<String> localItems);

    /**
     * Get local items.
     *
     * @return List of names of local items.
     */
    List<String> getLocalItems();

    /**
     * Set name of selected item.
     *
     * @param name Name of selected item.
     */
    void setSelectedItemName(String name);

    /**
     * Get name of selected item.
     *
     * @return Name of selected item.
     */
    String getSelectedItemName();

    /**
     * Set description of selected item.
     *
     * @param desc Description of selected item.
     */
    void setSelectedItemDesc(String desc);

    /**
     * Get description of selected item.
     *
     * @return Description of selected item.
     */
    String getSelectedItemDesc();

    /**
     * Set owner of selected item.
     *
     * @param owner Owner of selected item.
     */
    void setSelectedItemOwner(String owner);

    /**
     * Get owner of selected item.
     *
     * @return Owner of selected item.
     */
    String getSelectedItemOwner();
}
