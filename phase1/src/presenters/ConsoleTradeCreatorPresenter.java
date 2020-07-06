package presenters;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for {@link controllers.TradeCreatorController}.
 */
public class ConsoleTradeCreatorPresenter implements TradeCreatorPresenter {

    private Scanner input;

    /**
     * Create a console presenter for {@link controllers.TradeCreatorController}.
     */
    public ConsoleTradeCreatorPresenter() {
        input = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Invalid input, try again.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTwoWayTrade() {
        System.out.print("Would you like to make this a two way trade (y/n, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInventory(String username, List<String> inventory) {
        System.out.println("Here is " + username + "'s inventory:");
        for (int i = 0; i < inventory.size(); i++) {

            System.out.println(i + ". " + inventory.get(i));

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItem() {
        System.out.print("Select item in return (index, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocation() {
        System.out.print("Select trade location (-1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDate() {
        System.out.print("Select trade date (YYYY-MM-DD, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTime() {
        System.out.print("Select trade time (HH:MM, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIsPerm() {
        System.out.print("Is this trade permanent (y/n, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void successMessage() {
        System.out.println("Trade creation successful.");
    }
}
