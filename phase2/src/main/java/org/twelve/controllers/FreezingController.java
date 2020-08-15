package org.twelve.controllers;

import org.twelve.entities.Permissions;
import org.twelve.presenters.FreezingPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.SessionManager;
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

    private final SessionManager sessionManager;

    private final UseCasePool useCasePool;

    /**
     * Initializes constructor with necessary use cases
     *
     * @param useCasePool       An instance of ManualConfig to get use cases
     */
    public FreezingController(UseCasePool useCasePool) {
        statusManager = useCasePool.getStatusManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager  = useCasePool.getSessionManager();
        this.useCasePool = useCasePool;
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
        updateAccountLists();
    }

    /**
     * Unfreeze an account that has requested to be unfrozen
     */
    public void unfreeze(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unfreezeAccount(chosenUserID);
        updateAccountLists();
    }

    public void ban(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.banAccount(chosenUserID);
        updateAccountLists();
    }

    public void unban(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unBanAccount(chosenUserID);
        updateAccountLists();
    }

    public void trust(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.trustAccount(chosenUserID);
        updateAccountLists();
    }
    
    public void untrustAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unTrustAccount(chosenUserID);
        updateAccountLists();
    }

    public void modAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.modAccount(chosenUserID);
        updateAccountLists();
    }

    public void unmodAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unModAccount(chosenUserID);
        updateAccountLists();
    }

    public void updateAccountLists() {

        useCasePool.populateAll();

        List<String> bannedAccounts = new ArrayList<>(), unfreezeAccounts = new ArrayList<>(),
                frozenAccounts = new ArrayList<>(), toFreezeAccounts = new ArrayList<>(),
                adminAccounts = new ArrayList<>(), modAccounts = new ArrayList<>(),
                trustedAccounts = new ArrayList<>(), regularAccounts = new ArrayList<>();

        for (int id : accountRepository.getAccountIDs()) {

            String username = accountRepository.getUsernameFromID(id);

            if (statusManager.getUsernamesToFreeze().contains(username)) {
                toFreezeAccounts.add(username);
                continue;
            }

            switch (statusManager.getRoleOfAccount(id)) {

                case ADMIN:
                    adminAccounts.add(username);
                    break;
                case BANNED:
                    bannedAccounts.add(username);
                    break;
                case FROZEN:
                    if (statusManager.isPending(id)) {
                        unfreezeAccounts.add(username);
                    } else {
                        frozenAccounts.add(username);
                    }
                    break;
                case MOD:
                    modAccounts.add(username);
                    break;
                case NORMAL:
                    regularAccounts.add(username);
                    break;
                case TRUSTED:
                    trustedAccounts.add(username);
                    break;
                // there's like no point in displaying these
                case VACATION:

            }

        }

        freezingPresenter.setModAccounts(modAccounts);
        freezingPresenter.setRegularAccounts(regularAccounts);
        freezingPresenter.setFrozenAccounts(frozenAccounts);
        freezingPresenter.setToFreezeAccounts(toFreezeAccounts);
        freezingPresenter.setUnfreezeAccounts(unfreezeAccounts);
        freezingPresenter.setBannedAccounts(bannedAccounts);
        freezingPresenter.setAdminAccounts(adminAccounts);
        freezingPresenter.setTrustedAccounts(trustedAccounts);

        int accountID = sessionManager.getCurrAccountID();
        freezingPresenter.setCanMod(statusManager.hasPermission(accountID, Permissions.ADD_ADMIN));
        freezingPresenter.setCanUnmod(statusManager.hasPermission(accountID, Permissions.ADD_ADMIN));
    }
}
