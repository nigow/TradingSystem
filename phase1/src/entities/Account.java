package entities;
/** Represents an item
 * @Author Ethan Lam follow him on instagram @ethannomiddlenamelam
 */
import java.util.ArrayList;

public class Account {

    /**
     * The username of this account (cannot be changed)
     */
    private final String username;

    /**
     * The password of this account
     */
    private String password;

    /**
     * The user's wishlist (stores itemID of the items)
     */
    private ArrayList<Integer> wishlist;

    /**
     * The ID of the role that this user is
     */
    private String rolesID;

    /**
     * The unique identifier of this account (cannot be changed)
     */
    private final int accountID;

    /**
     * Creates a new account with the given username, password, and rolesID.
     * The wishlist is initialized as an empty Arraylist.
     * @param username
     * @param password
     * @param rolesID
     */
    public Account(String username, String password, String rolesID, int accountID) {
        this.username = username;
        this.password = password;
        this.wishlist = new ArrayList<Integer>();
        this.rolesID = rolesID;
        this.accountID = accountID;
    }

    /**
     * Gets the username of the account
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of this account
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a new password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the wishlist of this account
     * @return wishlist
     */
    public ArrayList<Integer> getWishlist() {
        return wishlist;
    }

    /**
     * Get the roleID of this account
     * @return rolesID
     */
    public String getRolesID() {
        return rolesID;
    }

    /**
     * Set the roleID of this account
     * @param rolesID
     */
    public void setRolesID(String rolesID) {
        this.rolesID = rolesID;
    }

    /**
     * Get the accountID of this account
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Adds an item to the end of the wishlist
     * @param itemID
     */
    public void addToWishlist(int itemID) {
        wishlist.add(itemID);
    }

    /**
     * Remove an item from the wishlist.
     * @param itemID
     * @return true if the item has been removed, and false if the item doesn't exist in the wishlist.
     */
    public boolean removeFromWishList(int itemID) {
        return wishlist.remove(Integer.valueOf(itemID));
    }

    @Override
    public String toString() {
        return String.format("Account name: %1$s\n" +
                "ID: %1$d\n" +
                "Role: %2$s", username, accountID, rolesID);
    }
}
