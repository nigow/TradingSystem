package org.twelve.presenters.console;

import org.twelve.presenters.AdminCreatorPresenter;

import java.util.Scanner;

/**
 * Console presenter for letting an admin create an account.
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
    public void displayInvalidInfo() {
        System.out.println("The characters in that username and password are illegal.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySuccessfulAccount() {
        System.out.println("You have added a new admin account.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayOverlappingInfo() {
        System.out.println("That username is taken.");
    }
}
