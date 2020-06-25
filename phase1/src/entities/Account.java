
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
    private List<Integer> wishlist;

    /**
     * The list of roles of the user
     */
    private final List<Roles> rolesID;

    /**
     * The unique identifier of this account (cannot be changed)
     */
    private final int accountID;

    /**
     * Creates a new account with the given username, password, and rolesID.
     * The wishlist is initialized as an empty Arraylist.
     * @param username the name of this account
     * @param password the password for this account
     * @param rolesID the roles of this account
     * @param accountID the id of this account
     */
    public Account(String username, String password, List<Roles> rolesID, int accountID) {
        this.username = username;
        this.password = password;
        this.wishlist = new ArrayList<>();
        this.rolesID = rolesID;
        this.accountID = accountID;
    }

    /**
     * An overloaded constructor
     * Creates a new account with the given username, password, wishlist, and rolesID.
     * @param username the name of this account
     * @param password the password for this account
     * @param wishlist the wishlist that this account should be initialized with
     * @param rolesID the roles for this account
     * @param accountID the ID of this account
     */
    public Account(String username, String password, List<Integer> wishlist, List<Roles> rolesID, int accountID) {
        this(username, password, rolesID, accountID);
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
     * @param password The new passoword that'll be assigned to this account
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
     * Get the rolesID of all the roles that this account belongs to
     * @return rolesID
     */
    public List<Roles> getRolesID() {
        return rolesID;
    }

    /**
     * Set the roleID of this account
     * @param roleID The new roleID that'll be added to this account
     */
    public void addRole(Roles roleID) {
        rolesID.add(roleID);
    }

    /**
     * Remove given roleID from user's list of rolesID
     * @param roleID the roleID that should be removed from this account
     * @return true if the role has been removed
     */
    public boolean removeRole(Roles roleID) {
        return rolesID.remove(roleID);
    }


    /**
     * Adds an item to the end of the wishlist
     * @param itemID The ID of the item to be added to this wishlist
     */
    public void addToWishlist(int itemID) {
        wishlist.add(itemID);
    }

    /**
     * Remove an item from the wishlist.
     * @param itemID The ID of the item to be removed from this wishlist
     * @return true if the item has been removed
     */
    public boolean removeFromWishList(int itemID) {
        return wishlist.remove(Integer.valueOf(itemID));
    }

    @Override
    public String toString() {
        return String.format("Account name: %1$s\n" +
                "ID: %2$s\n" +
                "Role: %3$s", username, accountID, rolesID.toString());
    }
}
