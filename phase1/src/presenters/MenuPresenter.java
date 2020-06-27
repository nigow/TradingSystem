package presenters;

/**
 * interface that shows possible actions
 */
public interface MenuPresenter {

    public void displayMenu();
    /**
     * displays all possible actions for the user
     */

    public int menu();
    /**
     * returns index of user's chosen action using the number designated to each action
     */
}