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
     * @param freezingOptions possible actions user can choose from
     * @return index of chosen item
     */
    String displayFreezingOptions(List<String> freezingOptions);

    /**
     * Displays list of users that should be frozen.
     *
     * @param possibleUsers list of usernames that should be frozen
     */
    void displayPossibleFreeze(List<String> possibleUsers);

    /**
     * Displays list of users that requests to be unfrozen.
     *
     * @param possibleUsers list of usernames that have requested to be unfrozen
     */
    void displayPossibleUnfreeze(List<String> possibleUsers);

    /**
     * Freezes chosen users.
     *
     * @return indexes of users to freeze
     */
    String freeze();

    /**
     * Unfreezes chosen users.
     *
     * @return indexes of users to unfreeze
     */
    String unfreeze();

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Presents the user with a custom message.
     */
    void showMessage(String message);

}