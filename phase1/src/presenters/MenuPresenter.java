package presenters;

/**
 * interface that shows possible actions
 */
public interface MenuPresenter {

    /**
     * displays all possible actions for the user
     */
    public void displayMenu();

    /**
     * returns index of user's chosen action using the number designated to each action
     */
    public int menu();
}