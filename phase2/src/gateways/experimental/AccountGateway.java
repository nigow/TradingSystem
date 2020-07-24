package gateways.experimental;

import usecases.AccountRepository;

import java.util.List;

public interface AccountGateway {

    void populate(AccountRepository accountRepository);
    void save(String username, String password, List<Integer> wishlist, List<String> permissions, int accountID);

}
