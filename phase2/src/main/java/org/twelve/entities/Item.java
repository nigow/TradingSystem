package org.twelve.entities;

/**
 * Represents an item.
 *
 * @author Ethan Lam follow him on instagram @ethannomiddlenamelam
 */
public class Item {

    private final int itemID;
    private String name;
    private String description;
    private boolean isApproved;
    private int ownerID;

    /**
     * Creates a new item with the given itemID, name, description, and ownerID.
     * isApproved is set to false by default (items have to be manually approved by an admin).
     * accountsWithItemInWishlist is empty by default until an account adds the item to their wishlist.
     *
     * @param itemID      Unique identifier of the item
     * @param name        Name of the item
     * @param description Description of the item
     * @param ownerID     Unique identifier of the owner of the item
     */
    public Item(int itemID, String name, String description, int ownerID) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.isApproved = false;
        this.ownerID = ownerID;
    }

    /**
     * Initializes an existing item with the given itemID, name, description, ownerID, and isApproved.
     *
     * @param itemID      Unique identifier of the item
     * @param name        Name of the item
     * @param description Description of the item
     * @param ownerID     Unique identifier of the owner of the item
     * @param isApproved  The approval status of the item.
     */
    public Item(int itemID, String name, String description, int ownerID, boolean isApproved) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.isApproved = isApproved;
        this.ownerID = ownerID;
    }

    /**
     * Get the itemID of this item.
     *
     * @return Unique identifier of the item
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Get the name of this item.
     *
     * @return Name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of this item.
     *
     * @return Description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the approval status of this item.
     *
     * @return Whether the item is approved
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Get the ownerID of this item.
     *
     * @return Unique identifier of the owner
     */
    public int getOwnerID() {
        return ownerID;
    }

    /**
     * Set the name of this item.
     *
     * @param name New name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of this item.
     *
     * @param description New description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the approval status of this item to true.
     */
    public void approve() {
        isApproved = true;
    }

    /**
     * Set the approval status of this item to false.
     */
    public void disapprove() {
        isApproved = false;
    }

    /**
     * Set the owner's ID of this item.
     *
     * @param ownerID New unique identifier of the owner
     */
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Creates a string representation of this item.
     *
     * @return String representation of this Item object
     */
    @Override
    public String toString() {
        return String.format("%1$s: %2$s", name, description);
    }

}
