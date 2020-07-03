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
        System.out.print("Would you like to make this a two way trade (true/false): ");
        return input.next();
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
        System.out.print("Select item in return (index): ");
        return input.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocation() {
        System.out.print("Select trade location: ");
        return input.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDate() {
        System.out.print("Select trade date (YYYY-MM-DD): ");
        return input.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTime() {
        System.out.print("Select Trade time (HH:MM): ");
        return input.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIsPerm() {
        System.out.print("Is this trade permanent (y/n): ");
        return input.next();
    }
}
