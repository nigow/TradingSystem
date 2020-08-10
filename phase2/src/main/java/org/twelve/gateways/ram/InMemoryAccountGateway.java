package org.twelve.gateways.ram;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;
import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryAccountGateway implements AccountGateway {

    /**
     * pseudo-external storage of accounts
     */
    private final Map<Integer, Account> accountMap;

    /**
     * Initialize the gateway
     * @param accountMap a key-pair set of an account id and account object
     */
    public InMemoryAccountGateway(Map<Integer, Account> accountMap) {
        this.accountMap = accountMap;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(AccountRepository accountRepository) {
        for(Account account: accountMap.values()){
            List<String> permissions = new ArrayList<>();
            for(Permissions permission: account.getPermissions()){
                permissions.add(permission.toString());
            }
            accountRepository.createAccount(account.getAccountID(), account.getUsername(),account.getPassword(),permissions,account.getWishlist(), account.getLocation());
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int accountId, String username, String password, List<Integer> wishlist,
                        List<String> permissions, String location, boolean newAccount) {
        List<Permissions> permissionNames = new ArrayList<>();
        for (Permissions permission: Permissions.values()) {
            if (permissions.contains(permission.name())) {
                permissionNames.add(permission);
            }
        }
        Account account = new Account(username, password, wishlist, permissionNames, accountId, location);
        accountMap.put(account.getAccountID(), account);
        return true;
    }
}
