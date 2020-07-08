package presenters;

import java.util.List;

/**
 * Interface for showing possible actions.
 *
 * @author Catherine
 */
public interface MenuPresenter {
    /**
     * Displays all possible actions for the user.
     *
     * @param menuOptions Possible actions
     * @return Index of chosen action
     */
    String displayMenu(List<String> menuOptions);

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();
}