package entities;
import java.util.ArrayList;
import java.util.List;
/** Represents an item
 * @author Ethan Lam follow him on instagram @ethannomiddlenamelam
 */
public class Item {
    /**
     * The unique identifier of this item. (cannot be changed)
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
     * The ID of the owner of this item
     */
    private int ownerID;

    /**
     * The accountIDs of users with this item in their wishlist
     */
    private final List<Integer> accountsWithItemInWishlist;

    /**
     * Creates a new item with the given itemID, name, description, and ownerID.
     * isApproved is set to false by default (items have to be manually approved by an admin).
     * accountsWithItemInWishlist is empty by default until an account adds the item to their wishlist
     * @param itemID The ID of the item
     * @param name The name of the item
     * @param description The description of the item
     */
    public Item(int itemID, String name, String description, int ownerID) {
        this.itemID = itemID;
        this.name = name;
        this.description= description;
        this.isApproved = false;
        this.ownerID = ownerID;
        this.accountsWithItemInWishlist = new ArrayList<>();
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
     * Get the ownerID of owner of this item
     * @return ownerID
     */
    public int getOwnerID() {
        return ownerID;
    }

    /**
     * Get the list of accountIDs of users with this item in their wishlist
     * @return accountsWithItemInWishlist
     */
    public List<Integer> getAccountsWithItemInWishlist() {
        return accountsWithItemInWishlist;
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
     * Set the approval status of this item to true.
     */
    public void approve() {
        isApproved = true;
    }

    /**
     * Set the approval status of this item to false
     */
    public void disapprove() {
        isApproved = false;
    }

    /**
     * Set the owner's ID of this item
     * @param ownerID (new owner's ID)
     */
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Add an accountID to accountsWithItemInWishlist
     * @param accountID the ID of the account added
     */
    public void addToAccountsWithItemsInWishlist(int accountID) {
        accountsWithItemInWishlist.add(accountID);
    }

    /**
     * Remove given accountID from the accountsWithItemInWishlist list
     * @param accountID the ID of the account that should be removed
     * @return true if the accountID has been removed
     */
    public boolean removeFromAccountsWithItemsInWishlist(int accountID) {
       return accountsWithItemInWishlist.remove(Integer.valueOf(accountID));
    }

    /**
     * Creates a string representation of this Item
     * @return a string representation of this Item object
     */
    @Override
    public String toString() {
        return String.format("%1$s: %2$s", name, description);
    }

}
