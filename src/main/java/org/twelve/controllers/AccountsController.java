package org.twelve.controllers;

import org.twelve.entities.Permissions;
import org.twelve.presenters.AccountsPresenter;
import org.twelve.usecases.account.AccountRepository;
import org.twelve.usecases.system.SessionManager;
import org.twelve.usecases.account.StatusManager;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that allows user to change an account's status
 */
public class AccountsController {

    private final StatusManager statusManager;

    private AccountsPresenter accountsPresenter;

    private final AccountRepository accountRepository;

    private final SessionManager sessionManager;

    private final UseCasePool useCasePool;

    /**
     * Initializes constructor with necessary use cases
     *
     * @param useCasePool An instance of ManualConfig to get use cases
     */
    public AccountsController(UseCasePool useCasePool) {
        statusManager = useCasePool.getStatusManager();
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        this.useCasePool = useCasePool;
    }

    /**
     * Set the presenter for this controller
     *
     * @param accountsPresenter an instance of a class that implements {@link AccountsPresenter}
     */
    public void setAccountsPresenter(AccountsPresenter accountsPresenter) {
        this.accountsPresenter = accountsPresenter;
    }

    /**
     * Freeze the chosen user
     *
     * @param chosenUser the username of the chosen user
     */
    public void freeze(String chosenUser) { //Assume that the list we're given of accountIDs is the same given to the view/presenter
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        if (statusManager.freezeAccount(chosenUserID)) {
            updateAccountLists();
        }
    }

    /**
     * Unfreeze the chosen user
     *
     * @param chosenUser the username of the chosen user
     */
    public void unfreeze(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        if (statusManager.unfreezeAccount(chosenUserID)) {
            updateAccountLists();
        }
    }

    /**
     * Ban the chosen user
     *
     * @param chosenUser the username of the chosen user
     */
    public void ban(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.banAccount(chosenUserID);
        updateAccountLists();
    }

    /**
     * Unban the chosen user
     *
     * @param chosenUser the username of the chosen user
     */
    public void unban(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unBanAccount(chosenUserID);
        updateAccountLists();
    }

    /**
     * Set the chosen user's status to Trusted
     *
     * @param chosenUser the username of the chosen user
     */
    public void trust(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.trustAccount(chosenUserID);
        updateAccountLists();
    }

    /**
     * Take away the chosen user's trusted status
     *
     * @param chosenUser the username of the chosen user
     */
    public void untrustAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unTrustAccount(chosenUserID);
        updateAccountLists();
    }

    /**
     * Make the chosen user a moderator
     *
     * @param chosenUser the username of the chosen user
     */
    public void modAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.modAccount(chosenUserID);
        updateAccountLists();
    }

    /**
     * Tale away the chosen user's moderator status
     *
     * @param chosenUser the username of the chosen user
     */
    public void unmodAccount(String chosenUser) {
        int chosenUserID = accountRepository.getIDFromUsername(chosenUser);
        statusManager.unModAccount(chosenUserID);
        updateAccountLists();
    }

    /**
     * Update all the lists in AccountsPresenter
     */
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
                // there's like no point in displaying this
                case VACATION:
                    break;
            }

        }

        accountsPresenter.setModAccounts(modAccounts);
        accountsPresenter.setRegularAccounts(regularAccounts);
        accountsPresenter.setFrozenAccounts(frozenAccounts);
        accountsPresenter.setToFreezeAccounts(toFreezeAccounts);
        accountsPresenter.setUnfreezeAccounts(unfreezeAccounts);
        accountsPresenter.setBannedAccounts(bannedAccounts);
        accountsPresenter.setAdminAccounts(adminAccounts);
        accountsPresenter.setTrustedAccounts(trustedAccounts);

        int accountID = sessionManager.getCurrAccountID();
        accountsPresenter.setCanMod(statusManager.hasPermission(accountID, Permissions.ADD_ADMIN));
        accountsPresenter.setCanUnmod(statusManager.hasPermission(accountID, Permissions.ADD_ADMIN));
    }
}
