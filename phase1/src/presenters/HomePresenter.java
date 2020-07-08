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
     * @param homeOptions Possible actions user can choose from
     * @return Index of chosen action
     */
    String displayHomeOptions(List<String> homeOptions);

    /**
     * Gets user's username.
     *
     * @return Username of account to login
     */
    String logInUsername();

    /**
     * Gets user's password.
     *
     * @return Password of account to login
     */
    String logInPassword();

    /**
     * Gets desired account username.
     *
     * @return Username of the new account
     */
    String newAccountUsername();

    /**
     * Gets desired account password.
     *
     * @return Password of the new account
     */
    String newAccountPassword();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Displays a custom message to the user.
     *
     * @param message Custom message for the user
     */
    void showMessage(String message);
}