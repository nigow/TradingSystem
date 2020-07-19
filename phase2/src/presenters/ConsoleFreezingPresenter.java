package presenters;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for freezing and unfreezing an account.
 */
public class ConsoleFreezingPresenter implements FreezingPresenter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayFreezingOptions(List<String> freezingOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which action would you like to do?");
        for (int i = 0; i < freezingOptions.size(); i++) {
            System.out.println(i + ". " + freezingOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayPossibleFreeze(List<String> possibleUsers) {
        System.out.println("These users should be frozen according to current restrictions:");
        for (int i = 0; i < possibleUsers.size(); i++) {
            System.out.println(i + ". " + possibleUsers.get(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayPossibleUnfreeze(List<String> possibleUsers) {
        System.out.println("These users have requested to be unfrozen:");
        for (int i = 0; i < possibleUsers.size(); i++) {
            System.out.println(i + ". " + possibleUsers.get(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String freeze() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the user you'd like to freeze:");
        System.out.println("Enter -1 to go back.");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String unfreeze() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the user you'd like to unfreeze:");
        System.out.println("Enter -1 to go back.");
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
    public void displaySuccessfulFreeze() {
        System.out.println("You have frozen this account.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySuccessfulUnfreeze() {
        System.out.println("You have unfrozen this account.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String freezeUser() {
        String message = "Freeze users";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String unfreezeUser() {
        String message = "Unreeze users";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String returnToHome() {
        String message = "Return to home";
        System.out.println(message);
        return message;
    }

}