package org.twelve.controllers;

// TODO javadoc

import org.twelve.entities.Permissions;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

import java.util.Arrays;
import java.util.List;

/**
 * A controller for handling the demo mode of the program
 *
 * @author Maryam
 */
public class DemoController {

    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;

    public DemoController(UseCasePool useCasePool) {
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
    }

    public void createDemoAccount() {
        List<Permissions> perms = Arrays.asList(Permissions.LOGIN, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST,
                Permissions.TRADE, Permissions.BROWSE_INVENTORY, Permissions.REQUEST_VACATION);

        int counter = 1;
        while (!accountRepository.createAccount("demo" + counter, "12345", perms, "UTM"))
            counter++;
        sessionManager.login("demo" + counter);
    }
}
