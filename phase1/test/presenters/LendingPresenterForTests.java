package presenters;

import entities.Account;
import entities.Item;

import java.util.List;

public class LendingPresenterForTests implements LendingPresenter{

    @Override
    public void displayAccounts(List<Account> allAccounts) {
        //pass
    }

    @Override
    public void displayInventory(List<Item> inventory) {
        //pass
    }

    @Override
    public String selectAccount() {
        return null;
    }


    @Override
    public String selectItem() {
        return null;
    }


    @Override
    public void invalidInput() {
        //pass
    }

    @Override
    public void abort() {
        //pass
    }
}
