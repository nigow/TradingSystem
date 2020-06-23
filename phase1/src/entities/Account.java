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


    private String password;

    private ArrayList<Item> wishlist;

    private int rolesID;




    public Account(String username) {
        this.username = username;
    }
}
