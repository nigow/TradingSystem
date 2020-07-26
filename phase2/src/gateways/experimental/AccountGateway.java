package gateways.experimental;

import entities.Permissions;
import usecases.AccountRepository;

import java.security.Permission;
import java.util.List;

public interface AccountGateway {

    void populate(AccountRepository accountRepository);

    // account_id,username,password,wishlist,permissions
    //TODO ask Lindsey if controllers/gateways can interact with enums(I think enums aren't entities)
    void save(int accountId, String username, String password, List<Integer> wishlist, List<Permissions> permissions);
}
