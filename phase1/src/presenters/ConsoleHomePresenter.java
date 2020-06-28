package presenters;


import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleHomePresenter implements HomePresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public int displayHomeOptions(ArrayList<String> HomeOptions) {
         Scanner input = new Scanner(System.in);
         System.out.println("Which action would you like to do?");
         for (int i = 0; i < HomeOptions.size(); i++) {
                 System.out.println(i + "-" + HomeOptions.get(i));
         }
         return input.nextInt();
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
}