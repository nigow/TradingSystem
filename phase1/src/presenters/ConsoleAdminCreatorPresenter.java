package presenters;

import java.util.Scanner;

/**
 * presenter that lets an admin create an account
 *
 * @author Catherine
 */
public class ConsoleAdminCreatorPresenter implements AdminCreatorPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdminUsername() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your desired username:");
        System.out.println("Enter \"-1\" to go back.");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdminPassword() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your desired password:");
        System.out.println("Enter \"-1\" to go back.");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
