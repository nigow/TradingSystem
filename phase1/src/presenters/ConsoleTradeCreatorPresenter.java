package presenters;

import java.util.List;
import java.util.Scanner;

public class ConsoleTradeCreatorPresenter implements TradeCreatorPresenter {

    private Scanner input;

    public ConsoleTradeCreatorPresenter() {
        input = new Scanner(System.in);
    }

    @Override
    public String displayTradeCreatorOptions(List<String> tradeCreatorOptions) {
        return null;
    }

    @Override
    public String[] suggestTimePlace() {
        return new String[0];
    }

    @Override
    public void invalidInput() {
        System.out.println("Invalid input, try again.");
    }

    @Override
    public String getTwoWayTrade() {
        System.out.print("Would you like to make this a two way trade (true/false): ");
        return input.next();
    }

    @Override
    public void showInventory(String username, List<String> inventory) {
        System.out.println("Here is " + username + "'s inventory:");
        for (int i = 0; i < inventory.size(); i++) {

            System.out.println(i + ". " + inventory.get(i));

        }
    }

    @Override
    public String getItem() {
        System.out.print("Select item in return (index): ");
        return input.next();
    }

    @Override
    public String getLocation() {
        System.out.print("Select trade location: ");
        return input.next();
    }

    @Override
    public String getDate() {
        System.out.print("Select trade date (YYYY-MM-DD): ");
        return input.next();
    }

    @Override
    public String getTime() {
        System.out.print("Select Trade time (HH:MM): ");
        return input.next();
    }

    @Override
    public String getIsPerm() {
        System.out.print("Is this trade permanent (y/n): ");
        return input.next();
    }
}
