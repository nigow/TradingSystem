package presenters;

/**
 * Console presenter for letting user appeal to be unfrozen.
 */
public class ConsoleAppealPresenter implements AppealPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySuccessfulAppeal(String username) {
        System.out.println(username + "'s appeal has been received.");
    }
}
