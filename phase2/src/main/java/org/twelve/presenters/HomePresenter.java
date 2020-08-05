package org.twelve.presenters;

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
     * Gets desired location
     *
     * @return Location of new account
     */
    String newAccountLocation();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * tells user that their inputted username/password is incorrect
     */
    void displayIncorrectInfo();

    /**
     * tells user their username and/or password is invalid
     */
    void displayInvalidInfo();

    /**
     * tells user that the admin account has been made successfully
     */
    void displaySuccessfulAccount();

    /**
     * tells user that an account has already been made with the info
     */
    void displayOverlappingInfo();

    /**
     * shows user option to log in
     * @return string message
     */
    String logIn();

    /**
     * shows user option to create a new account
     * @return string message
     */
    String createAccount();
    /**
     * shows user option to quit the program
     * @return string message
     */
    String quit();
}