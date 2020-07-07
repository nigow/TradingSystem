package presenters;

/**
 * presenter that lets user
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
