package org.twelve.presenters.console;

import org.twelve.presenters.MenuPresenter;

import java.util.Scanner;
import java.util.List;

/**
 * Console presenter for letting users choose what they want to do in their account.
 *
 * @author Catherine
 */
public class ConsoleMenuPresenter implements MenuPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String displayMenu(List<String> menuOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which action would you like to do?");
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println(i + ". " + menuOptions.get(i));
        }
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
    public String manageTrades() {
        String message = "Manage your existing trades";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String browseInventory() {
        String message = "Browse the inventory";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String manageWishlist() {
        String message = "Manage your wishlist";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String initiateTrade() {
        String message = "Initiate a trade with a specific account";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String modifyThresholds() {
        String message = "Modify the restriction values of the program";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String manageFrozen() {
        String message = "Manage the frozen accounts";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addAdmin() {
        String message = "Add an admin account";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requestUnfreeze() {
        String message = "Request to be unfrozen";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String logout() {
        String message = "Logout";
        System.out.println(message);
        return message;
    }
}