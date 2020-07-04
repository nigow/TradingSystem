package presenters;

import java.util.List;
import java.util.Scanner;

public class ConsoleFreezingPresenter implements FreezingPresenter{

    @Override
    public String displayFreezingOptions(List<String> freezingOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which action would you like to do?");
        for (int i = 0; i < freezingOptions.size(); i++) {
            System.out.println(i + "-" + freezingOptions.get(i));
        }
        return input.nextLine();
    }

    @Override
    public void displayPossibleFreeze(List<String> possibleUsers) {
        System.out.println("These users should be frozen according to current restrictions:");
        for (int i = 0; i < possibleUsers.size(); i++) {
            System.out.println(i + "-" + possibleUsers.get(i));
        }
    }

    @Override
    public void displayPossibleUnfreeze(List<String> possibleUsers) {
        System.out.println("These users have requested to be unfrozen:");
        for (int i = 0; i < possibleUsers.size(); i++) {
            System.out.println(i + "-" + possibleUsers.get(i));
        }
    }

    @Override
    public String freeze() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the user you'd like to freeze:");
        System.out.println("Enter -1 to go back.");
        return input.nextLine();
    }

    @Override
    public String unfreeze() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the user you'd like to unfreeze:");
        System.out.println("Enter -1 to go back.");
        return input.nextLine();
    }

    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }

}