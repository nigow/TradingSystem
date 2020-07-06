package gateways;

import entities.Account;

import java.util.*;

public class InMemoryAccountGateway implements AccountGateway {
    private final Map<Integer, Account> accounts;

    public InMemoryAccountGateway(Map<Integer, Account> accounts) {
        this.accounts = accounts;
    }

    public InMemoryAccountGateway() {
        this.accounts = new HashMap<>();
    }

    @Override
    public Account findById(int id) {
        return accounts.get(id);
    }

    @Override
    public Account findByUsername(String username) {
        for (Account account: accounts.values()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public boolean updateAccount(Account account) {
       accounts.put(account.getAccountID(), account);
       return true;
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public int generateValidId() {
        if (accounts.isEmpty()) {
            return 0;
        }
        return Collections.max(accounts.keySet()) + 1;
    }
}
