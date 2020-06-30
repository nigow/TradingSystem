package presenters;

import java.util.List;

/**
 * interface that shows possible actions
 * @author Catherine
 */
public interface MenuPresenter {
    /**
     * displays all possible actions for the user
     * @param menuOptions possible actions
     * @return index of chosen action
     */
    public String displayMenu(List<String> menuOptions);

    /**
     * tells user that their input was invalid
     */
    public void invalidInput();
}