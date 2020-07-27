package org.twelve.usecases;

import org.twelve.entities.Account;

// TODO javadoc

/**
 * A class for managing the current session.
 * @author Maryam
 */
public class SessionManager {
    Account account;
    int tradeID;
    AccountRepository accountRepository;

    public SessionManager(AccountRepository accountRepository) {
        tradeID = -1;
        account = null;
        this.accountRepository = accountRepository;
    }

    public void login(String username) {
        account = accountRepository.getAccountFromUsername(username);
    }

    public void logout() {
        account = null;
        tradeID = -1;
    }

    public void setCurrTradeID(int tradeID) {
        this.tradeID = tradeID;
    }

    public void removeCurrTradeID() {
        tradeID = -1;
    }

    public int getCurrTradeID() {
        return tradeID;
    }

    public int getCurrAccountID() {
        return account.getAccountID();
    }

    public String getCurrAccountUsername() {
        return account.getUsername();
    }
}
