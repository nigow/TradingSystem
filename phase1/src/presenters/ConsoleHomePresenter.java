package presenters;


import java.util.Scanner;
import java.util.List;

/**
 * presenter that allows user to create new account or log in
 * @author Catherine
 */
public class ConsoleHomePresenter implements HomePresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String displayHomeOptions(List<String> homeOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which action would you like to do?");
        for (int i = 0; i < homeOptions.size(); i++) {
            System.out.println(i + "-" + homeOptions.get(i));
        }
        return input.nextLine();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] logIn() {
         String[] logIn = new String[2];
         Scanner input = new Scanner(System.in);
         System.out.println("Enter your username:");
         logIn[0] = input.nextLine();
         System.out.println("Enter your password:");
         logIn[1] = input.nextLine();
         return logIn;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] newAccount() {
        String[] newAccount = new String[2];
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your desired username:");
        newAccount[0] = input.nextLine();
        System.out.println("Enter your desired password:");
        newAccount[1] = input.nextLine();
        return newAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }
}