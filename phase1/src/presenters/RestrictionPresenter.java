package presenters;

/**
 * interface allowing administrator to manage restrictions
 * @author Catherine
 */
public interface RestrictionPresenter {

    public void displayRestrictions();
    /**
     * displays current restrictions
     */

    public int[] changeRestriction();
    /**
     * takes the number of a type of restriction, asks user for new
     * value then updaes with list of ints of updated values
     */

    public void returnToMenu();
    /**
     * returns user to main menu
     */
}