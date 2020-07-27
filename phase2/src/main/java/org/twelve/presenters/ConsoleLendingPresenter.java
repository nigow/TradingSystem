package org.twelve.presenters;

import org.twelve.entities.Account;
import org.twelve.entities.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for providing lending services on GUI.
 *
 * @author Tairi
 */
public class ConsoleLendingPresenter implements LendingPresenter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayAccounts(List<String> allAccounts) {
        System.out.println("Thank you for choosing to lend an item, select an account you wish to lend an item to:");
        for (int i = 0; i < allAccounts.size(); i++) {

            System.out.println(i + ". " + allAccounts.get(i));

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String selectAccount() {
        System.out.println("Select an account to initiate a trade with (-1 to return to menu):");
        Scanner input = new Scanner(System.in);

        return input.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String selectItem() {
        System.out.println("Select an item you are lending (-1 to return to menu): ");
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    @Override
    public void displayInvalidInput() {
        System.out.println("Invalid input. Please try again.");
    }

    @Override
    public void displaySuccessfulInput() {
        System.out.println("Cancellation succeeded.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayInventory(List<String> inventory) {
        System.out.println("Here are your items. Which item would you like to offer?");
        for (int i = 0; i < inventory.size(); i++) {

            System.out.println(i + ". " + inventory.get(i));

        }
    }


}
