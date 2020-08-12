package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;

public class LoginManager {

    private final AccountRepository accountRepository;
    private final SecurityUtility securityUtility;

    public LoginManager(AccountRepository accountRepository, SecurityUtility securityUtility){
        this.accountRepository = accountRepository;
        this.securityUtility = securityUtility;
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
        return storedAccount.getPassword().equals(securityUtility.encrypt(password)) &&
                storedAccount.getPermissions().contains(Permissions.LOGIN);
    }

    public boolean changePassword(int accountID, String oldPassword, String newPassword){
        Account account = accountRepository.getAccountFromID(accountID);
        String temp = securityUtility.encrypt(oldPassword);
        if (!account.getPassword().equals(securityUtility.encrypt(oldPassword)))
            return false;
        account.setPassword(securityUtility.encrypt(newPassword));
        accountRepository.updateToAccountGateway(account);
        return true;
    }
}
