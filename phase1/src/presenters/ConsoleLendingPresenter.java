package presenters;


import entities.Account;
import entities.Item;

import java.util.List;
import java.util.Scanner;

public class ConsoleLendingPresenter implements LendingPresenter {
    @Override
    public void displayAccounts(List<Account> allAccounts) {
        int count = 0;
        System.out.println("Thank you for choosing to lend an item, select an account you wish to lend an item to:");
        for(Account account: allAccounts){
            System.out.println(String.valueOf(count) + ". " + account.getUsername());
        }
    }

    @Override
    public String selectAccount() {
        System.out.println("Select an account to initiate a trade with:");
        Scanner input = new Scanner(System.in);

        return input.next();
    }

    @Override
    public String selectItem() {
        System.out.println("Select an item you are lending: ");
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    @Override
    public String startTrade(Account toAccount, Item tradingItem) {
        //TODO          === Call TradeCreator ===
        //Assumption: TradeCreator class requires an account to trade to and item to trade.
        //Maybe strings are more appropriate.
        return null;
    }

    @Override
    public void displayInventory(List<Item> inventory) {
        int count = 0;
        System.out.println("Here are your items. Which item would you like to offer?");
        for(Item item: inventory)
            System.out.println(String.valueOf(count) + ". " + item.getName() + " Description: " + item.getDescription());
    }

    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again with a valid number listed");
    }

}
