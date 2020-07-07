package presenters;

/**
 * Console presenter for letting user appeal to be unfrozen.
 */
public class ConsoleAppealPresenter implements AppealPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySuccessfulAppeal() {
        System.out.println("Your appeal has been received.");
    }
}
