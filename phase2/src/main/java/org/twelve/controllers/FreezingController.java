package org.twelve.controllers;

import org.twelve.entities.Account;
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
    public void freeze(String chosenUser) { //Assume that the list we're given of accountIDs is the same given to the view/presenter
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.freezeAccount(chosenUserID);
    }

    /**
     * Unfreeze an account that has requested to be unfrozen
     */
    public void unfreeze(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unfreezeAccount(chosenUserID);
    }

    public void ban(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.banAccount(chosenUserID);
        updateAccountLists();
    }

    public void unban(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unbanAccount(chosenUserID);
        updateAccountLists();
    }

    public void trust(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.trustAccount(chosenUserID);
    }
    
    public void untrustAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unTrustAccount(chosenUserID);
    }

    public void modAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.modAccount(chosenUserID);
    }

    public void unmodAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unmodAccount(chosenUserID);
    }
    public void updateAccountLists() {

        // demo (don't plan on including since they're not permanent anyways)

        // banned
        List<String> bannedAccounts = statusManager.getUsernamesToUnBan();
        freezingPresenter.setBannedAccounts(bannedAccounts);

        // frozen (req)
        List<String> unfreezeAccounts = statusManager.getUsernamesToUnfreeze();
        unfreezeAccounts.removeAll(bannedAccounts);
        freezingPresenter.setUnfreezeAccounts(unfreezeAccounts);

        // frozen
        List<String> frozenAccounts = statusManager.getFrozenUsernames();
        freezingPresenter.setFrozenAccounts(frozenAccounts);

        // to freeze
        List<String> toFreezeAccounts = statusManager.getUsernamesToFreeze();
        freezingPresenter.setToFreezeAccounts(toFreezeAccounts);

        // on vacation (we are not displaying this anymore)
        // freezingPresenter.setVacationingAccounts(statusManager.getVacationUsernames());

        // we can't display all of these at once right now without duplication because an admin is recognized to be a mod, trusted, etc
        // tcm
        // freezingPresenter.setTrustedAccounts(statusManager.getTrustedUsernames());
        // mod
        // freezingPresenter.setModAccounts(statusManager.getModeratorUsernames());
        // admin
        List<String> adminAccounts = statusManager.getAdminUsernames();
        adminAccounts.removeAll(bannedAccounts);
        freezingPresenter.setAdminAccounts(adminAccounts);

        // regular (todo: we need a better way of distinguishing accounts)
        List<String> regularAccounts = new ArrayList<>();
        for (int accountId : accountRepository.getAccountIDs()) {
            regularAccounts.add(accountRepository.getUsernameFromID(accountId));
        }
        regularAccounts.removeAll(bannedAccounts);
        regularAccounts.removeAll(unfreezeAccounts);
        regularAccounts.removeAll(frozenAccounts);
        regularAccounts.removeAll(toFreezeAccounts);
        regularAccounts.removeAll(adminAccounts);

        freezingPresenter.setRegularAccounts(regularAccounts);
    }
}
