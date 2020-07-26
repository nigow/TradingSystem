package gateways.experimental;

import usecases.AccountRepository;

import java.util.List;

public interface AccountGateway {

    void populate(AccountRepository accountRepository);

    // account_id,username,password,wishlist,permissions
    void save(int accountId, String username, String password, List<Integer> wishlist, List<String> permissions);
}
