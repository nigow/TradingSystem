package presenters;

import java.util.List;
import java.util.Scanner;

/**
 * presenter that lets an admin create an account
 * @author Catherine
 */
public class ConsoleAdminPresenter implements AdminPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] createAdmin() {
        String[] adminInfo = new String[2];
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your desired username:");
        adminInfo[0] = input.nextLine();
        System.out.println("Enter your desired password:");
        adminInfo[1] = input.nextLine();
        return adminInfo;
    }

    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }
}
