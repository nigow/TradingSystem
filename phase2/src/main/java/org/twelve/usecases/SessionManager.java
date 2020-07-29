package org.twelve.usecases;

import org.twelve.entities.Account;

// TODO javadoc

/**
 * A class for managing the current session.
 * @author Maryam
 */
public class SessionManager {
    Account account;
    AccountRepository accountRepository;

    public SessionManager(AccountRepository accountRepository) {
        account = null;
        this.accountRepository = accountRepository;
    }

    public void login(String username) {
        account = accountRepository.getAccountFromUsername(username);
    }

    public void logout() {
        account = null;
    }

    public int getCurrAccountID() {
        if (account != null)
            return account.getAccountID();

        return -1;
    }

    public String getCurrAccountUsername() {
        return account.getUsername();
    }
}
