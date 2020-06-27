package presenters;

/**
 * interface allowing administrator to manage restrictions
 * @author Catherine
 */
public interface RestrictionPresenter {

    /**
     * displays current restrictions
     */
    public void displayRestrictions();

    /**
     * takes the number of a type of restriction, asks user for new
     * value then updaes with list of ints of updated values
     */
    public int[] changeRestriction();

    /**
     * returns user to main menu
     */
    public void returnToMenu();
}