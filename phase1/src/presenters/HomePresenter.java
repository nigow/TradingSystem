package presenters;

import java.util.ArrayList;

/**
* interface that logs user in and creates accounts
 * @author Catherine
*/
public interface HomePresenter {
    /**
     * displays possible actions
     * @param HomeOptions possible actions user can choose from
     * @return index of chosen action
     */
    public int displayHomeOptions(ArrayList<String> HomeOptions);
    /**
     * log user in
     * @return username and password inputted
     */
    public String[] logIn();

    /**
     * creates account with given information
     * @return username and password they would like to use for account
     */
    public String[] newAccount();
}