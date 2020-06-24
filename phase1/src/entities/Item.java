package entities;
import java.util.ArrayList;
/** Represents an item
 * @Author Ethan Lam follow him on instagram @ethannomiddlenamelam
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
     * The username of the owner of this item.
     */
    private String ownerUsername;

    /**
     * The ID of the owner of this item
     */
    private int ownerID;

    /**
     * The accountIDs of users with this item in their wishlist
     */
    private ArrayList<Integer> accountsWithItemInWishlist;

    /**
     * Creates a new item with the given itemID, name, description, and ownerUsername.
     * isApproved is set to false by default (items have to be manually approved by an admin).
     * accountsWithItemInWishlist is empty by default until an account adds the item to their wishlist
     * @param itemID
     * @param name
     * @param description
     * @param ownerUsername
     */
    public Item(int itemID, String name, String description, String ownerUsername, int ownerID) {
        this.itemID = itemID;
        this.name = name;
        this.description= description;
        this.isApproved = false;
        this.ownerUsername = ownerUsername;
        this.ownerID = ownerID;
        this.accountsWithItemInWishlist = new ArrayList<Integer>();
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

    public int getOwnerID() {
        return ownerID;
    }

    /**
     * Get the list of accountIDs of users with this item in their wishlist
     * @return accountsWithItemInWishlist
     */
    public ArrayList<Integer> getAccountsWithItemInWishlist() {
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
     * Set the owner's username of this item.
     * @param ownerUsername (new owner's username)
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
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
     * @param accountID
     */
    public void addToAccountsWithItemsInWishlist(int accountID) {
        accountsWithItemInWishlist.add(accountID);
    }

    /**
     * Remove given accountID from the accountsWithItemInWishlist list
     * @param accountID
     * @return true if the accountID has been removed
     */
    public boolean removeFromAccountsWithItemsInWishlist(int accountID) {
       return accountsWithItemInWishlist.remove(Integer.valueOf(accountID));
    }

    /**
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return String.format("Item name: %1$s\n" +
                "ID: %2$s\n" +
                "Description: %3$s\n" +
                "Approval status: %4$s\n" +
                "Name of owner: %5$s", name, String.valueOf(itemID), description, String.valueOf(isApproved), ownerUsername);
    }

}
