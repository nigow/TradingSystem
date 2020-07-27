package org.twelve.gateways.json;

import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.AccountRepository;

import java.util.List;

public class JsonAccountGateway implements AccountGateway {

    @Override
    public void populate(AccountRepository accountRepository) {

    }

    @Override
    public void save(int accountId, String username, String password, List<Integer> wishlist,
                     List<String> permissions) {

    }

}
