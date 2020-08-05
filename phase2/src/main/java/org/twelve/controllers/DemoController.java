package org.twelve.controllers;

// TODO javadoc

import org.twelve.entities.Permissions;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.ThresholdRepository;
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
    private final ThresholdRepository thresholdRepository;

    public DemoController(UseCasePool useCasePool) {
        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        thresholdRepository = useCasePool.getThresholdRepository();
    }

    public void createDemoAccount() {
        List<Permissions> perms = Arrays.asList(Permissions.LOGIN, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST,
                Permissions.TRADE, Permissions.BROWSE_INVENTORY, Permissions.REQUEST_VACATION);

        int counter = 1;
        while (!accountRepository.createAccount("demo" + counter, "12345", perms, "UTM"))
            counter++;
        sessionManager.login("demo" + counter);

        // TODO rebuild gateways to in-memory gateways

        // this loosens the regular threshold values of our program, so that the
        //  demo user can borrow/lend/trade without any restrictions and without
        //  getting frozen.
        thresholdRepository.setLendMoreThanBorrow(-1000);
        thresholdRepository.setMaxIncompleteTrade(1000);
        thresholdRepository.setMaxWeeklyTrade(1000);
        thresholdRepository.setNumberOfEdits(100);
        thresholdRepository.setRequiredTradesForTrusted(10);
    }
}
