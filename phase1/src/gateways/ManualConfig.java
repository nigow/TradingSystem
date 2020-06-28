package gateways;

import usecases.AccountManager;
import usecases.AuthManager;

import java.io.IOException;

/**
 * Configures the application.
 * @author Michael
 */
public class ManualConfig {
    private AccountManager accountManager;
    private AuthManager authManager;

    /**
     * Creates ManualConfig and initializes the required usecases.
     */
    public ManualConfig() {
        try {
            // TODO: Replace csvPath with wherever we store the information.
            String filePath = System.getProperty("user.dir") + "/out/Files/";
            CSVAccountGateway csvAccountGateway = new CSVAccountGateway(filePath + "accounts.csv");
            this.accountManager = new AccountManager(csvAccountGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

    }
    /**
     * @return an instance of AccountManager use case.
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * @return an instance of AuthManager use case.
     */
    public AuthManager getAuthManager() {
        return authManager;
    }
}
