package gateways;

import entities.Account;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CSVAccountGatewayTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void findById() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        AccountGateway ag = new CSVAccountGateway(tempPath);

        assertEquals(ag.findById(0).getUsername(), "admin");

    }

    @Test
    public void findByUsername() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        AccountGateway ag = new CSVAccountGateway(tempPath);

        assertEquals(ag.findByUsername("admin").getUsername(), "admin");

    }

    @Test
    public void updateAccount() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        AccountGateway ag = new CSVAccountGateway(tempPath);

        Account newAccount = new Account("fbk", "srkm", new ArrayList<>(), ag.generateValidId());

        ag.updateAccount(newAccount);

        assertEquals(ag.findByUsername("fbk").getUsername(), "fbk");

    }

    @Test
    public void getAllAccounts() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        AccountGateway ag = new CSVAccountGateway(tempPath);

        // note: this is only accurate because there is just one account in the system. when there are multiple
        // accounts the order getAllAccounts() returns them in is unpredictable
        assertEquals(ag.getAllAccounts().get(0).getUsername(), "admin");

    }

    @Test
    public void generateValidId() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        AccountGateway ag = new CSVAccountGateway(tempPath);

        assertEquals(ag.generateValidId(), 1);

    }
}