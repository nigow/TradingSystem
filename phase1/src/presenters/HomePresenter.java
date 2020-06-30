package presenters;

import java.util.List;
/**
* interface that logs user in and creates accounts
 * @author Catherine
*/
public interface HomePresenter {
    /**
     * displays possible actions
     * @param homeOptions possible actions user can choose from
     * @return index of chosen action
     */
    public String displayHomeOptions(List<String> homeOptions);
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

    /**
     * tells user that their input was invalid
     */
    public void invalidInput();
}