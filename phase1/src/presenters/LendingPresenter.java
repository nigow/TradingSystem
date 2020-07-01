package presenters;

import entities.Account;
import entities.Item;

import java.util.List;

public interface LendingPresenter {
    void displayAccounts(List<Account> allAccounts);
    void displayInventory(List<Item> inventory);
    String selectAccount();
    String selectItem();
    String startTrade(Account toAccount, Item tradingItem);
    void invalidInput();
    void returnToMenu();
}
