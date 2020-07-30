package org.twelve.presenters;

import java.util.List;

/**
 * Interface for appealing to be unfrozen.
 *
 * @author Catherine
 */
public interface AppealPresenter {

    String displayRequestOptions(List<String> options);

    /**
     * Lets user know their request has been received.
     *
     * @param username Username of account
     */
    void displayUnfreezeAppeal(String username);

    void displayVacationAppeal(String username);

    void displayVacationCompletion(String username);

    String requestUnfreeze();

    String requestVacation();

    String completeVacation();

    void invalidInput();


}
