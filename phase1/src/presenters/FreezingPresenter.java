package presenters;

import java.util.ArrayList;

/**
 * interface used by administrator to freeze and unfreeze account
 * @author Catherine
 */
public interface FreezingPresenter {
    /**
     * displays possible actions and returns index of chosen action
     */
    public int displayFreezingOptions(ArrayList<String> freezingOptions);
    /**
     * displays list of users that should be frozen
     */
    public void displayPossibleFreeze(ArrayList<String> possibleUsers);

    /**
     * displays list of users that requests to be unfrozen
     */
    public void displayPossibleUnfreeze(ArrayList<String> possibleUsers);

    /**
     * returns indexes of users to freeze
     */
    public ArrayList<Integer> freeze();

    /**
     * returns indexes of users to unfreeze
     */
    public ArrayList<Integer> unfreeze();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}