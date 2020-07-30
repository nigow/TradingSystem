package org.twelve.presenters;

/**
 * Interface for appealing to be unfrozen.
 *
 * @author Catherine
 */
public interface AppealPresenter {

    /**
     * Lets user know their request has been received.
     *
     * @param username Username of account
     */
    void displayUnfreezeAppeal(String username);

    void displayVacationAppeal(String username);

    void displayVacationCompletion(String username);

    void invalidInput();


}
