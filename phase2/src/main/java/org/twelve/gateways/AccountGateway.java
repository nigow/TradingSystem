package org.twelve.gateways;

import org.twelve.usecases.AccountRepository;

import java.util.List;

public interface AccountGateway {

    boolean populate(AccountRepository accountRepository);

    // account_id,username,password,wishlist,permissions
    //
    // ask Lindsey if controllers/gateways can interact with enums(I think enums aren't entities)
    // gson can't do this without creating a dependency in the enum
    boolean save(int accountId, String username, String password, List<Integer> wishlist, List<String> permissions,
                 String location, boolean newAccount);
}
