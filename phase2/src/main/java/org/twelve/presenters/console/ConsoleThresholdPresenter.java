package org.twelve.presenters.console;

import org.twelve.presenters.ThresholdPresenter;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for admin handling of changes to restrictions.
 *
 * @author Catherine
 */
public class ConsoleThresholdPresenter implements ThresholdPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String displayThresholdOptions(List<String> thresholdOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an option to do:");
        for (int i = 0; i < thresholdOptions.size(); i++) {
            System.out.println(i + ". " + thresholdOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeLendMoreThanBorrow(int lendMoreThanBorrow) {
        Scanner input = new Scanner(System.in);
        System.out.println("You currently have to lend at least " + lendMoreThanBorrow + " more items than you borrow.");
        System.out.println("Input \"-1\" to go back.");
        System.out.println("Change this to:");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeMaxIncompleteTrades(int maxIncompleteTrades) {
        Scanner input = new Scanner(System.in);
        System.out.println("You can currently have maximum " + maxIncompleteTrades + " incomplete trades.");
        System.out.println("Input \"-1\" to go back.");
        System.out.println("Change this to:");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeMaxWeeklyTrades(int weeklyTrades) {
        Scanner input = new Scanner(System.in);
        System.out.println("You can currently make maximum " + weeklyTrades + " trades in a week.");
        System.out.println("Input \"-1\" to go back.");
        System.out.println("Change this to:");
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
    public void displayChangedThresholds() {
        System.out.println("You changed the restriction threshold.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String lendBorrowLimit() {
        String message = "Lend vs. borrow limit";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String maxIncompleteTrades() {
        String message = "Maximum number of incomplete trades";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String maxWeeklyTrades() {
        String message = "Maximum number of weekly trades";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String returnToMainMenu() {
        String message = "Return to main menu";
        System.out.println(message);
        return message;
    }
}
