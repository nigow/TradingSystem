package org.twelve.presenters.console;

import org.twelve.presenters.AppealPresenter;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for letting user appeal to be unfrozen.
 */
public class ConsoleAppealPresenter implements AppealPresenter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayRequestOptions(List<String> displayOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an option to do:");
        for (int i = 0; i < displayOptions.size(); i++) {
            System.out.println(i + ". " + displayOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayUnfreezeAppeal(String username) {
        System.out.println(username + ", your appeal for unfreezing their account has been received.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayVacationAppeal(String username) {
        System.out.println(username + ", a vacation status has been added to your account.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayVacationCompletion(String username) {
        System.out.println(username + ", a vacation status has been removed from your account.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requestUnfreeze() {
        String message = "Request for your account to be unfrozen";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requestVacation() {
        String message = "Request to go on vacation, giving your account a vacation status";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String completeVacation() {
        String message = "Request to complete your vacation, removing your account's vacation status";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }


}
