
package entities;
import java.util.ArrayList;
import java.util.List;
/** Represents an item
 * @author Ethan Lam follow him on instagram @ethannomiddlenamelam
 */
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
    private final List<Integer> wishlist;

    /**
     * The list of permissions of the user
     */
    private final List<Permissions> permissions;

    /**
     * The unique identifier of this account (cannot be changed)
     */
    private final int accountID;

    /**
     * Creates a new account with the given username, password, and rolesID.
     * The wishlist is initialized as an empty Arraylist.
     * @param username the name of this account
     * @param password the password for this account
     * @param permissions what this account is allowed to do
     * @param accountID the id of this account
     */
    public Account(String username, String password, List<Permissions> permissions, int accountID) {
        this.username = username;
        this.password = password;
        this.wishlist = new ArrayList<>();
        this.permissions = permissions;
        this.accountID = accountID;
    }

    /**
     * An overloaded constructor
     * Creates a new account with the given username, password, wishlist, and rolesID.
     * @param username the name of this account
     * @param password the password for this account
     * @param wishlist the wishlist that this account should be initialized with
     * @param permissions what this account is allowed to do
     * @param accountID the ID of this account
     */
    public Account(String username, String password, List<Integer> wishlist, List<Permissions> permissions, int accountID) {
        this(username, password, permissions, accountID);
        this.wishlist.addAll(wishlist);
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
     * @param password The new password that'll be assigned to this account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the wishlist of this account
     * @return wishlist
     */
    public List<Integer> getWishlist() {
        return wishlist;
    }


    /**
     * Get the accountID of this account
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Get all permissions that this account has
     * @return permissions
     */
    public List<Permissions> getPermissions() {
        return permissions;
    }

    /**
     * Add a permission to the list of permissions
     * @param permission The new permission that'll be added to this account
     */
    public void addPermission(Permissions permission) {
        permissions.add(permission);
    }

    /**
     * Remove given permission from user's list of permissions
     * @param permission the permission that should be removed from this account
     * @return true if the permission has been removed
     */
    public boolean removePermission(Permissions permission) {
        return permissions.remove(permission);
    }


    /**
     * Adds an item to the end of the wishlist
     * @param itemID The ID of the item to be added to this wishlist
     */
    public void addToWishlist(int itemID) {
        wishlist.add(itemID);
    }

    /**
     * Remove an item from the wishlist
     * @param itemID The ID of the item to be removed from this wishlist
     * @return true if the item has been removed
     */
    public boolean removeFromWishList(int itemID) {
        return wishlist.remove(Integer.valueOf(itemID));
    }


    /**
     * Creates a string representation of this Account
     * @return A string representation of this Account object
     */
    @Override
    public String toString() {
        return String.format("Account name: %1$s\n" +
                "ID: %2$s\n", username, accountID);
    }
}
