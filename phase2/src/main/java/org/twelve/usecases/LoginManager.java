package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;

public class LoginManager {

    private final AccountRepository accountRepository;

    public LoginManager(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Authenticates a login session provided username and password.
     *
     * @param username Username of account that the user entered
     * @param password Password of account that the user entered
     * @return Whether the login is successful or not
     */
    public boolean authenticateLogin(String username, String password) {
        Account storedAccount = accountRepository.getAccountFromUsername(username);
        if (storedAccount == null) {
            return false;
        }
        return storedAccount.getPassword().equals(password) &&
                storedAccount.getPermissions().contains(Permissions.LOGIN);
    }

    public boolean changePassword(int accountID, String oldPassword, String newPassword){
        Account account = accountRepository.getAccountFromID(accountID);
        if (!account.getPassword().equals(oldPassword))
            return false;
        account.setPassword(newPassword);
        accountRepository.updateToAccountGateway(account);
        return true;
    }
}
