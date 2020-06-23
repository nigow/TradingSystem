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
     * The user's wishlist
     */
    private ArrayList<Item> wishlist;

    /**
     * The ID of the role that this user is
     */
    private int rolesID;

    /**
     * Creates a new account with the given username, password, and rolesID.
     * The wishlist is initialized as an empty Arraylist.
     * @param username
     * @param password
     * @param rolesID
     */
    public Account(String username, String password, int rolesID) {
        this.username = username;
        this.password = password;
        this.wishlist = new ArrayList<Item>();
        this.rolesID = rolesID;
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
    public ArrayList<Item> getWishlist() {
        return wishlist;
    }

    /**
     * Get the roleID of this account
     * @return rolesID
     */
    public int getRolesID() {
        return rolesID;
    }

    /**
     * Set the roleID of this account
     * @param rolesID
     */
    public void setRolesID(int rolesID) {
        this.rolesID = rolesID;
    }

    /**
     * Adds an item to the end of the wishlist
     * @param item
     */
    public void addToWishlist(Item item) {
        wishlist.add(item);
    }

    public void removeFromWishList(Item item) {

    }
}
