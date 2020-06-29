package presenters;

import java.util.ArrayList;

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
    public String displayMenu(ArrayList<String> menuOptions);
}