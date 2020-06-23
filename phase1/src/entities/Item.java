package entities;
/** Represents an item
 * @Author Ethan Lam follow him on instagram @ethannomiddlenamelam
 */
public class Item {
    /**
     * The unique identifier of this item.
      */
    private final int itemID;

    /**
     * The name of this item.
     */
    private String name;

    /**
     * The description of this item.
     */
    private String description;

    /**
     * Whether or not this item has been approved by an admin to add to a list.
     */
    private boolean isApproved;

    /**
     * The username of the owner of this item.
     */
    private String ownerUsername;

    /**
     * Creates a new item with the given itemID, name, description, and ownerUsername.
     * isApproved is set to false by default (items have to be manually approved by an admin).
     * @param itemID
     * @param name
     * @param description
     * @param ownerUsername
     */
    public Item(int itemID, String name, String description, String ownerUsername) {
        this.itemID = itemID;
        this.name = name;
        this.description= description;
        this.isApproved = false;
        this.ownerUsername = ownerUsername;
    }

    /**
     * Get the itemID of this item.
     * @return itemID
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Get the name of this item.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of this item.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the approval status of this item.
     * @return isApproved
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Get the name of the owner of this item.
     * @return ownerUsername
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Set the name of this item.
     * @param name (new name of item)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of this item.
     * @param description (new description)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the approval status of this item.
     * @param approved
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * Set the owner's username of this item.
     * @param ownerUsername (new owner's username)
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return String.format("Item name: %1$s\n" +
                "ID: %1$d\n" +
                "Description: %2$s\n" +
                "Approval status: %3$s\n" +
                "Name of owner: %4$s", name, itemID, description, String.valueOf(isApproved), ownerUsername);
    }

}
