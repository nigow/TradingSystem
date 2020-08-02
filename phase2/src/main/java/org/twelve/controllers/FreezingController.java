package org.twelve.controllers;

import org.twelve.presenters.FreezingPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;


import java.util.ArrayList;
import java.util.List;

/**
 * Controller that allows user to freeze/unfreeze users
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 * @author Catherine
 */
public class FreezingController {
    /**
     * An instance of StatusManager to get qualifying accounts.
     */
    private final StatusManager statusManager;

    /**
     * An instance of InputValidator to check if input is valid.
     */
    private FreezingPresenter freezingPresenter;

    /**
     * An instance of AccountRepository to check accounts
     */
    private final AccountRepository accountRepository;

    /**
     * Initializes constructor with necessary use cases
     *
     * @param useCasePool       An instance of ManualConfig to get use cases
     */
    public FreezingController(UseCasePool useCasePool) {
        statusManager = useCasePool.getStatusManager();
        accountRepository = useCasePool.getAccountRepository();
    }

    public void setFreezingPresenter(FreezingPresenter freezingPresenter) {
        this.freezingPresenter = freezingPresenter;
    }

    /**
     * Freezes an account that should be frozen.
     */
    public void freeze(int chosenUser) { //Assume that the list we're given of accountIDs is the same given to the view/presenter
        List<Integer> accounts = statusManager.getAccountIDsToFreeze();
        statusManager.freezeAccount(accounts.get(chosenUser));
    }

    /**
     * Unfreeze an account that has requested to be unfrozen
     */
    public void unfreeze(int chosenUser) {
        List<Integer> accounts = statusManager.getAccountsToUnfreeze();
        statusManager.unfreezeAccount(accounts.get(chosenUser));
    }

    public void ban(int chosenUser) {
        List<Integer> accounts = statusManager.getAccountIDsToBan();
        statusManager.banAccount(accounts.get(chosenUser));
    }

    public void unban(int chosenUser) {
        List<Integer> accounts = statusManager.getAccountIDsToUnBan();
        statusManager.unbanAccount(accounts.get(chosenUser));
    }

    public void updateAccountLists() {

        /*
        List<String> accountList = new ArrayList<>();
        List<String> frozenAccountList = new ArrayList<>();
        List<String> bannedAccountList = new ArrayList<>();

        for (String s : accountRepository.getAccountStrings()) {
            accountList.add(s);
        }
        for (String s : statusManager.getUsernamesToFreeze()) {
            frozenAccountList.add(s);
        }
        for (String s : statusManager.getUsernamesToUnBan()) {
            bannedAccountList.add(s);
        }

        freezingPresenter.setAllBannedAccounts(bannedAccountList);
        freezingPresenter.setAllFrozenAccounts(frozenAccountList);
        freezingPresenter.setAllAccounts(accountList);

         */

        // demo (don't plan on including since they're not permanent anyways)

        // banned
        freezingPresenter.setBannedAccounts(statusManager.getUsernamesToUnBan());
        // frozen (todo)

        // frozen (req)
        freezingPresenter.setUnfreezeAccounts(statusManager.getUsernamesToUnfreeze());
        // to freeze
        freezingPresenter.setToFreezeAccounts(statusManager.getUsernamesToFreeze());

        // regular (todo)

        // on vacation
        freezingPresenter.setVacationingAccounts(statusManager.getVacationUsernames());

        // tcm (todo)

        // mod (todo)

        // admin
        freezingPresenter.setAdminAccounts(statusManager.getAdminUsernames());


    }
}
