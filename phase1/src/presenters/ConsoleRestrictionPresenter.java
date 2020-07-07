package presenters;

import java.util.List;
import java.util.Scanner;

/**
 * presenter for admins handling changes of restrictions
 *
 * @author Catherine
 */
public class ConsoleRestrictionPresenter implements RestrictionPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String displayRestrictionOptions(List<String> restrictionOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an option to do:");
        for (int i = 0; i < restrictionOptions.size(); i++) {
            System.out.println(i + ". " + restrictionOptions.get(i));
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
    public void showMessage(String message) {
        System.out.println(message);
    }
}
