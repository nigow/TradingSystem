package presenters;

import java.util.List;

/**
 * Interface for freezing and unfreezing account.
 *
 * @author Catherine
 */
public interface FreezingPresenter {
    /**
     * Displays possible actions for freezing accounts.
     *
     * @param freezingOptions Possible actions user can choose from
     * @return Index of chosen item
     */
    String displayFreezingOptions(List<String> freezingOptions);

    /**
     * Displays list of users that should be frozen.
     *
     * @param possibleUsers List of usernames that should be frozen
     */
    void displayPossibleFreeze(List<String> possibleUsers);

    /**
     * Displays list of users that requests to be unfrozen.
     *
     * @param possibleUsers List of usernames that have requested to be unfrozen
     */
    void displayPossibleUnfreeze(List<String> possibleUsers);

    /**
     * Freezes chosen users.
     *
     * @return Indexes of users to freeze
     */
    String freeze();

    /**
     * Unfreezes chosen users.
     *
     * @return Indexes of users to unfreeze
     */
    String unfreeze();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Tells user that the chosen users have been frozen.
     */
    void displaySuccessfulFreeze();

    /**
     * Tells user that the chosen users have been unfrozen.
     */
    void displaySuccessfulUnfreeze();

    /**
     * Shows "freeze user" option.
     * @return the string message
     */
    String freezeUser();

    /**
     * Shoes "unfreeze user" option.
     * @return the string message
     */
    String unfreezeUser();

    /**
     * Shows "return to home" option.
     * @return the string message
     */
    String returnToHome();
}