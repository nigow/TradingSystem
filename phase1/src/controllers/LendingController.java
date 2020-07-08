package controllers;

import entities.Account;
import entities.Item;
import gateways.ManualConfig;
import presenters.LendingPresenter;
import presenters.TradeCreatorPresenter;
import usecases.AccountManager;
import usecases.ItemManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with lending service.
 *
 * @author Tairi
 */
public class LendingController {
    /**
     * Presenter for lending services.
     */
    private final LendingPresenter lendingPresenter;

    /**
     * Account manager needed to provide account-related services.
     */
    private final AccountManager accountManager;

    /**
     * Item manager needed to provide item-related services.
     */
    private final ItemManager itemManager;

    /**
     * Collection of gateways.
     */
    private final ManualConfig manualConfig;

    private final InputHandler validator;

    /**
     * Presenter that initiates trades.
     */
    private final TradeCreatorPresenter tradeCreatorPresenter;


    /**
     * Initialize the use cases and presenter.
     *
     * @param lendingPresenter Presenter for lending services
     * @param manualConfig Collection of gateways
     * @param tradeCreatorPresenter Presenter for suggesting trades
     */
    public LendingController(LendingPresenter lendingPresenter, ManualConfig manualConfig, TradeCreatorPresenter tradeCreatorPresenter) {
        this.manualConfig = manualConfig;
        this.lendingPresenter = lendingPresenter;
        this.accountManager = manualConfig.getAccountManager();
        this.itemManager = manualConfig.getItemManager();
        this.tradeCreatorPresenter = tradeCreatorPresenter;
        this.validator = new InputHandler();
    }

    /**
     * Helper method that sends all accounts available to trade with, and let the user choose.
     *
     * @return index of the account in the list of accounts provided
     */
    private int chooseAccount() {
        List<Account> allAccounts = accountManager.getAccountsList();

        //remove the current account
        allAccounts.removeIf(account -> account.getAccountID() == accountManager.getCurrAccountID());

        lendingPresenter.displayAccounts(allAccounts);
        boolean flag = true;
        String temp_index = null;
        while (flag) {
            temp_index = lendingPresenter.selectAccount();
            if (validator.isExitStr(temp_index)) {
                abort();
                return -1;
            } else if (!validator.isNum(temp_index)) {
                invalidInput();
            } else {
                int index = Integer.parseInt(temp_index);
                if (index >= allAccounts.size()) invalidInput();
                else flag = false;
            }
        }
        return accountManager.getAccountID(allAccounts.get(Integer.parseInt(temp_index)));
    }


    /**
     * Helper method that sends all tradable items that the user owns.
     *
     * @return index of the item in the list of accounts provided
     */
    private int chooseItem() {
        List<Item> myItems = new ArrayList<>();
        int userId = accountManager.getCurrAccountID();

        //list only tradable items
        for (Item item : itemManager.getAllItems()) {
            if (item.getOwnerID() == userId && item.isApproved()) myItems.add(item);
        }

        lendingPresenter.displayInventory(myItems);

        boolean flag = true;
        String temp_index = null;
        while (flag) {
            temp_index = lendingPresenter.selectItem();
            if (validator.isExitStr(temp_index)) {
                abort();
                return -1;
            } else if (!validator.isNum(temp_index)) {
                invalidInput();
            } else {
                int index = Integer.parseInt(temp_index);
                if (index >= myItems.size()) invalidInput();
                else flag = false;
            }
        }
        return itemManager.getItemId(myItems.get(Integer.parseInt(temp_index)));
    }

    private void invalidInput() {
        lendingPresenter.customMessage("Invalid input. Please try again.");
    }

    private void abort() {
        lendingPresenter.customMessage("Cancellation succeeded.");
    }

    /**
     * Let the user choose who to trade and which item to lend and start trading.
     */
    public void run() {
        int toAccountId = chooseAccount();
        if (toAccountId == -1) return;

        int tradingItemId = chooseItem();
        if (tradingItemId == -1) return;

        TradeCreatorController startTrade;
        startTrade = new TradeCreatorController(tradeCreatorPresenter, manualConfig, toAccountId, tradingItemId, false);
        startTrade.run();
    }

}
