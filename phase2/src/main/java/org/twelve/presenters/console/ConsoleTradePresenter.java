package org.twelve.presenters.console;

import org.twelve.presenters.TradePresenter;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for displaying trades and related options.
 *
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
    public String editTradeTime() {
        Scanner input = new Scanner(System.in);
        System.out.print("Suggest a new time for the meeting hh:mm (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editTradeDate() {
        Scanner input = new Scanner(System.in);
        System.out.print("Suggest a new date for the meeting yyyy-mm-dd (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editTradeLocation() {
        Scanner input = new Scanner(System.in);
        System.out.print("Suggest a new location for the meeting (-1 to go back): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayRecentOneWayTrade(List<String> recentOneWayTrade) {
        System.out.println("These are your most recent items traded in a one-way trade: ");
        for (String s : recentOneWayTrade) {
            System.out.println(s);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayRecentTwoWayTrade(List<String> recentTwoWayTrade) {
        System.out.println("These are your most recent items traded in a two-way trade: ");
        for (String s : recentTwoWayTrade) {
            System.out.println(s);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayFrequentPartners(List<String> frequentPartners) {
        System.out.println("These are your most frequent trading partners: ");
        for (String s : frequentPartners) {
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
    public String isTradeCompleted() {
        Scanner input = new Scanner(System.in);
        System.out.println("Has this trade been completed?");
        System.out.print("y/n: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayTrade(String trade) {
        System.out.println(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayCancelled() {
        System.out.println("This trade has been cancelled.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayCompleted() {
        System.out.println("This trade is now completed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayConfirmed() {
        System.out.println("The time and place of this trade has been confirmed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayRejected() {
        System.out.println("This trade has been rejected.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayIncomplete() {
        System.out.println("This trade is still incomplete.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayLimitReached() {
        System.out.println("\"You have edited \" +\n" +
                "                        \"the time and location for this trade too many times.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayFuture() {
        System.out.println("You need to choose a date and time in the future.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySuggestion() {
        System.out.println("You have suggested a new time and location for this trade.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String viewTrades() {
        String message = "View your trades";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editTrade() {
        String message = "Select a trade to edit";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String twoWayRecent() {
        String message = "View items given away in recent two-way trades";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String oneWayRecent() {
        String message = "View items given away in recent one-way trades";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String frequentPartners() {
        String message = "View your most frequent trading partners";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String returnToPrevious() {
        String message = "Return to previous menu";
        System.out.println(message);
        return message;
    }
}
