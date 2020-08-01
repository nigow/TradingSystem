package org.twelve.usecases;

import junit.framework.TestCase;
import org.twelve.entities.Account;
import org.twelve.entities.Item;
import org.twelve.entities.Permissions;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ram.InMemoryAccountGateway;
import org.twelve.gateways.ram.InMemoryGatewayPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountIntegrationTest extends TestCase {
    AccountGateway inMemoryAccountGateway;
    AccountRepository accountRepository;

    private void setUpAccountRepository() {
        List<Permissions> Perm = new ArrayList<>();

        Account initial = new Account("User1", "Potato", Perm, 0, "M");
        HashMap<Integer, Account> h = new HashMap<>();
        h.put(0, initial);

        inMemoryAccountGateway = new InMemoryAccountGateway(h);
        accountRepository = new AccountRepository(inMemoryAccountGateway);
    }
    
    public void testInitialization() {
        setUpAccountRepository();
        assertNotNull(accountRepository);
    }
}
