package presenters;

import java.util.List;

/**
 * interface used by administrator to freeze and unfreeze account
 * @author Catherine
 */
public interface FreezingPresenter {
    /**
     * displays possible actions
     * @param freezingOptions possible actions user can choose from
     * @return index of chosen item
     */
    public String displayFreezingOptions(List<String> freezingOptions);
    /**
     * displays list of users that should be frozen
     * @param possibleUsers list of usernames that should be frozen
     */
    public void displayPossibleFreeze(List<String> possibleUsers);

    /**
     * displays list of users that requests to be unfrozen
     * @param possibleUsers list of usernames that have requested to be unfrozen
     */
    public void displayPossibleUnfreeze(List<String> possibleUsers);

    /**
     * freezes chosen users
     * @return indexes of users to freeze
     */
    public String freeze();

    /**
     * unfreezes chosen users
     * @return indexes of suers to unfreeze
     */
    public String unfreeze();

    /**
     * tells user that their input was invalid
     */
    public void invalidInput();

}