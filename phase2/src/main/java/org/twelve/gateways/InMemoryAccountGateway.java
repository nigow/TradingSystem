package org.twelve.gateways;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;
import org.twelve.usecases.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryAccountGateway implements AccountGateway{
    public final Map<Integer, Account> accountMap;

    public InMemoryAccountGateway(Map<Integer, Account> accountMap) {
        this.accountMap = accountMap;

    }
    @Override
    public void populate(AccountRepository accountRepository) {
        List<Integer> existingAccounts = accountRepository.getAccountIDs();
        for (Account account: accountMap.values()) {
            if (!existingAccounts.contains(account.getAccountID())) {
                accountRepository.addAccount(account);
            }
        }
    }

    @Override
    public void save(int accountId, String username, String password, List<Integer> wishlist, List<String> permissions) {
        List<Permissions> permissionNames = new ArrayList<>();
        for (Permissions permission: Permissions.values()) {
            if (permissions.contains(permission)) {
                permissionNames.add(permission);
            }
        }
        Account account = new Account(username, password, wishlist, permissionNames, accountId);
    }
}
