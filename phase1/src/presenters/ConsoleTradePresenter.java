package presenters;

import java.util.List;
import java.util.Scanner;

/**
 * A console presenter for displaying trades and related options.
 * @author Maryam
 */
public class ConsoleTradePresenter implements TradePresenter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayTradeOptions(List<String> tradeOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose one of the following options: ");
        for (int i = 0; i < tradeOptions.size(); i++) {
            System.out.println(i + ". " + tradeOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayTrades(List<String> trades) {
        System.out.println("Your trades:");
        for (int i = 0; i < trades.size(); i++) {
            System.out.println(i + ". ");
            System.out.println(trades.get(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String selectTrade() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the trade you want to change (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] editTradeTimePlace() {
        Scanner input = new Scanner(System.in);
        System.out.print("Suggest a new location for the meeting (-1 to go back): ");
        String[] s = new String[3];
        s[0] = input.nextLine();
        System.out.print("Suggest a new date for the meeting yyyy-mm-dd (-1 to go back): ");
        s[1] = input.nextLine();
        System.out.print("Suggest a new time for the meeting hh-mm (-1 to go back): ");
        s[2] = input.nextLine();
        return s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayRecentOneWayTrade(List<String> recentOneWayTrade) {
        System.out.println("These are your most recent items traded in a one-way trade: ");
        for (String s: recentOneWayTrade) {
            System.out.println(s);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayRecentTwoWayTrade(List<String> recentTwoWayTrade) {
        System.out.println("These are your most recent items traded in a two-way trade: ");
        for (String s: recentTwoWayTrade) {
            System.out.println(s);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayFrequentPartners(List<String> frequentPartners) {
        System.out.println("These are your most frequent trading partners: ");
        for (String s: frequentPartners) {
            System.out.println(s);
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String yesOrNo() {
        Scanner input = new Scanner(System.in);
        System.out.print("y/n: ");
        return input.nextLine();
    }
}
