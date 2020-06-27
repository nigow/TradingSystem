package gateways;

import usecases.AccountManager;

import java.io.IOException;

/**
 * Configures the application.
 * @author Michael
 */
public class ManualConfig {
    private AccountManager accountManager;

    /**
     * Creates ManualConfig and initializes the required usecases.
     */
    public ManualConfig() {
        try {
            // TODO: Replace csvPath with wherever we store the information.
            String filePath = System.getProperty("user.dir") + "/out/Files/accounts.csv";
            CSVAccountGateway csvAccountGateway = new CSVAccountGateway(filePath);
            this.accountManager = new AccountManager(csvAccountGateway);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

    }
    /**
     * @return an instance of accountManager usecase.
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }
}
