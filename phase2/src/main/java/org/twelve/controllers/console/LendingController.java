package org.twelve.controllers.console;



import org.twelve.controllers.InputHandler;
import org.twelve.presenters.LendingPresenter;
import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

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
     * Account repository needed to provide account-related services.
     */
    private final AccountRepository accountRepository;

    private final SessionManager sessionManager;

    /**
     * Item manager needed to provide item-related services.
     */
    private final ItemManager itemManager;

    /**
     * Collection of gateways.
     */
    private final UseCasePool useCasePool;

    private final InputHandler validator;

    /**
     * Presenter that initiates trades.
     */
    private final TradeCreatorPresenter tradeCreatorPresenter;


    /**
     * Initialize the use cases and presenter.
     *
     * @param lendingPresenter      Presenter for lending services
     * @param useCasePool          Collection of gateways
     * @param tradeCreatorPresenter Presenter for suggesting trades
     */
    public LendingController(LendingPresenter lendingPresenter, UseCasePool useCasePool, TradeCreatorPresenter tradeCreatorPresenter) {
        this.useCasePool = useCasePool;
        this.lendingPresenter = lendingPresenter;
        this.accountRepository = useCasePool.getAccountRepository();
        this.itemManager = useCasePool.getItemManager();
        this.sessionManager = useCasePool.getSessionManager();
        this.tradeCreatorPresenter = tradeCreatorPresenter;
        this.validator = new InputHandler();
    }

    /**
     * Helper method that sends all accounts available to trade with, and let the user choose.
     *
     * @return index of the account in the list of accounts provided
     */
    private int chooseAccount() {
        List<Integer> allAccounts = accountRepository.getAccountIDs();

        //remove the current account
        allAccounts.removeIf(account -> account == sessionManager.getCurrAccountID());

        lendingPresenter.displayAccounts(accountRepository.getAccountStrings());
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
        return allAccounts.get(Integer.parseInt(temp_index));
    }


    /**
     * Helper method that sends all tradable items that the user owns.
     *
     * @return index of the item in the list of accounts provided
     */
    private int chooseItem() {
        List<Integer> myItemsIDs = itemManager.getApprovedInventoryOfAccount(sessionManager.getCurrAccountID());
        List<String> myItemsStrings = new ArrayList<>();

        for (int itemID : myItemsIDs)
            myItemsStrings.add(itemManager.getItemStringById(itemID));

        lendingPresenter.displayInventory(myItemsStrings);

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
                if (index >= myItemsIDs.size()) invalidInput();
                else flag = false;
            }
        }
        return myItemsIDs.get(Integer.parseInt(temp_index));
    }

    private void invalidInput() {
        lendingPresenter.displayInvalidInput();
    }

    private void abort() {
        lendingPresenter.displaySuccessfulInput();
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
        startTrade = new TradeCreatorController(tradeCreatorPresenter, useCasePool, toAccountId, tradingItemId, false);
        startTrade.run();
    }

}
