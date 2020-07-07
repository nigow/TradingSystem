package presenters;

import java.util.List;

/**
 * Interface for logging an user in and creating accounts.
 *
 * @author Catherine
 */
public interface HomePresenter {
    /**
     * Displays possible actions for logging in or creating accounts.
     *
     * @param homeOptions possible actions user can choose from
     * @return index of chosen action
     */
    String displayHomeOptions(List<String> homeOptions);

    /**
     * Gets user's username.
     *
     * @return username of Account
     */
    String logInUsername();

    /**
     * Gets user's password.
     *
     * @return password of Account
     */
    String logInPassword();

    /**
     * Gets desired account username.
     *
     * @return username
     */
    String newAccountUsername();

    /**
     * Gets desired account password.
     *
     * @return password
     */
    String newAccountPassword();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Displays a custom message to the user.
     *
     * @param message a custom message for the user
     */
    void showMessage(String message);
}