package presenters;

import java.util.Scanner;
import java.util.List;

/**
 * presenter that lets user choose what they want to do in their account
 * @author Catherine
 */
public class ConsoleMenuPresenter implements MenuPresenter{
    /**
     * {@inheritDoc}
     */
    @Override
    public String displayMenu(List<String> menuOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which action would you like to do?");
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println(i + ". " + menuOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }
}