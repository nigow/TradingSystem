package presenters;

public class ConsoleAppealPresenter implements AppealPresenter{
    @Override
    public void displaySuccessfulAppeal() {
        System.out.println("Your appeal has been received.");
    }
}
