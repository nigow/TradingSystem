package presenters;


import java.util.Scanner;
import java.util.List;

/**
 * Console presenter for allowing an user to create new account or login.
 *
 * @author Catherine
 */
public class ConsoleHomePresenter implements HomePresenter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayHomeOptions(List<String> homeOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which action would you like to do?");
        for (int i = 0; i < homeOptions.size(); i++) {
            System.out.println(i + ". " + homeOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String logInUsername() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your username (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String logInPassword() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your password (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String newAccountUsername() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your desired username (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String newAccountPassword() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your desired password (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayIncorrectInfo() {
        System.out.println("That username/password combination is incorrect.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayInvalidInfo() {
        System.out.println("The characters in that username and password are illegal.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySuccessfulAccount() {
        System.out.println("You have created an account.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayOverlappingInfo() {
        System.out.println("That username is taken.");
    }

}