package presenters;

/**
 * interface used by administrator to freeze and unfreeze account
 * @author Catherine
 */
public interface FreezingPresenter {

    /**
     * displays list of users that should be frozen
     */
    public void displayPossibleFreeze();

    /**
     * displays list of users that requests to be unfrozen
     */
    public void displayPossibleUnfreeze();

    /**
     * returns indexes of users to freeze
     */
    public int[] freeze();

    /**
     * returns indexes of users to unfreeze
     */
    public int[] unfreeze();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}