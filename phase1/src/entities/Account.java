package entities;
/** Represents an item
 * @Author Ethan Lam follow him on instagram @ethannomiddlenamelam
 */
import java.util.ArrayList;
import java.util.List;

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
    private List<Roles> rolesID;

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
    public Account(String username, String password, ArrayList<Roles> rolesID, int accountID) {
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
     * @param roleID
     */
    public void addRole(Roles roleID) {
        rolesID.add(roleID);
    }

    /**
     * Remove given roleID from user's list of rolesID
     * @param roleID
     * @return true if the role has been removed
     */
    public boolean removeRole(Roles roleID) {
        return rolesID.remove(roleID);
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
     * @return true if the item has been removed
     */
    public boolean removeFromWishList(int itemID) {
        return wishlist.remove(Integer.valueOf(itemID));
    }

    @Override
    public String toString() {
        return String.format("Account name: %1$s\n" +
                "ID: %2$s\n" +
                "Role: %3$s", username, String.valueOf(accountID), rolesID.toString());
    }
}
