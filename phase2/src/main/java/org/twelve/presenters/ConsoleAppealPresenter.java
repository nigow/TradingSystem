package org.twelve.presenters;

/**
 * Console presenter for letting user appeal to be unfrozen.
 */
public class ConsoleAppealPresenter implements AppealPresenter {


    /**
     * {@inheritDoc}
     */
    @Override
    public void displayUnfreezeAppeal(String username) {
        System.out.println(username + "'s appeal has been received.");
    }

    @Override
    public void displayVacationAppeal(String username) {

    }

    @Override
    public void displayVacationCompletion(String username) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }


}
