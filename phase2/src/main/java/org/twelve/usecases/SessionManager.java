package org.twelve.usecases;

import org.twelve.entities.Account;

/**
 * A class for managing the current session.
 * @author Maryam
 */
public class SessionManager {
    private Account account;
    private int tradeID;
    private final AccountRepository accountRepository;

    /**
     * @param accountRepository A repository storing all account entities in the system.
     */
    public SessionManager(AccountRepository accountRepository) {
        tradeID = -1;
        account = null;
        this.accountRepository = accountRepository;
    }

    /**
     * Store the account entity in this Session to know what account is currently logged in.
     * @param username The username of the account login in.
     */
    public void login(String username) {
        account = accountRepository.getAccountFromUsername(username);
    }

    /**
     * Logout the currently logged in account
     */
    public void logout() {
        account = null;
    }

    /**
     * Get the id of the currently logged in account.
     * @return The id of current account, or -1 if no account is logged in.
     */
    public int getCurrAccountID() {
        if (account != null)
            return account.getAccountID();

        return -1;
    }

    /**
     * Get the username of the currently logged in account.
     * @return The username of the currently logged in account.
     */
    public String getCurrAccountUsername() {
        return account.getUsername();
    }

    /**
     * set the trade being currently worked on
     *
     * @param tradeID id of trade
     */
    public void setWorkingTrade(int tradeID) {
        this.tradeID = tradeID;
    }

    /**
     * returns the trade currently being worked on
     *
     * @return the id of trade
     */
    public int getWorkingTrade() {
        return tradeID;
    }

    /**
     * removes the current trade being worked on
     */
    public void removeWorkingTrade() {
        tradeID = -1;
    }
}
