package gateways;

import usecases.AccountManager;

import java.io.IOException;

public class ManualConfig {
    private AccountManager accountManager;

    public ManualConfig() {
        try {
            // TODO: Replace csvPath with wherever we store the information.
            CSVAccountGateway csvAccountGateway = new CSVAccountGateway("");
            this.accountManager = new AccountManager(csvAccountGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
}
