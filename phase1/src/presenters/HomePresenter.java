package presenters;

import java.util.*;
/**
* interface that logs user in and creates accounts
 * @author Catherine
*/
public interface HomePresenter {

    /**
     * returns a string array containing the entered username, then password of an account
     */
    public String[] logIn();

    /**
     * returns a string containing the desired username and password for a new account
     */
    public String[] newAccount();
}