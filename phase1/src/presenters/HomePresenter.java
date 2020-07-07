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
    String displayHomeOptions(List<String> homeOptions);
    /**
     * gets user's username
     * @return username
     */
    String logInUsername();

    /**
     * gets user's password
     * @return password
     */
    String logInPassword();

    /**
     * gets desired account username
     * @return username
     */
    String newAccountUsername();

    /**
     * gets desired account password
     * @return password
     */
    String newAccountPassword();

    /**
     * tells user that their input was invalid
     */
    void invalidInput();

    /**
     * displays a custom message to the user
     * @param message a custom message for the user
     */
    void showMessage(String message);
}