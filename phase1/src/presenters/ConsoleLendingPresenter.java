package presenters;


import entities.Account;
import entities.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Presenter to provide lending services on CUI
 *
 * @author Tairi
 */
public class ConsoleLendingPresenter implements LendingPresenter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayAccounts(List<Account> allAccounts) {
        System.out.println("Thank you for choosing to lend an item, select an account you wish to lend an item to:");
        for (int i = 0; i < allAccounts.size(); i++) {

            System.out.println(i + ". " + allAccounts.get(i).getUsername());

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayInventory(List<Item> inventory) {
        System.out.println("Here are your items. Which item would you like to offer?");
        for (int i = 0; i < inventory.size(); i++) {

            System.out.println(i + ". " + inventory.get(i).getName() + "Description: "
                    + inventory.get(i).getDescription());

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again with a valid number listed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abort() {
        System.out.println("Abort");
    }
}
