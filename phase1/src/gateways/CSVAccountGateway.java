package gateways;

import entities.Account;
import entities.Roles;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An account gateway that uses csv files as persistent storage.
 */
public class CSVAccountGateway implements AccountGateway {

    private final File csvFile;

    private final LinkedHashMap<String, Integer> headers = new LinkedHashMap<String, Integer>() {{

        put("account_id", 0);
        put("username", 1);
        put("password", 2);
        put("wishlist", 3);
        put("role_ids", 4);

    }}; // must be ordered to ensure each header is written into the correct column

    private final Map<Integer, String> accounts;

    /**
     * Create an account gateway that uses csv files as persistent storage.
     * @param csvPath Path to the csv file holding account data.
     * @throws IOException If the given csv file cannot be accessed.
     */
    public CSVAccountGateway(String csvPath) throws IOException {
        csvFile = new File(csvPath);

        accounts = new HashMap<>();
        try {

            Scanner fileReader = new Scanner(csvFile);
            fileReader.nextLine(); // skip the header

            while (fileReader.hasNext()) {

                String[] row = fileReader.nextLine().split(",");

                Integer accountId = Integer.valueOf(row[headers.get("account_id")]);

                accounts.put(accountId, String.join(",", row));

            }

            fileReader.close();

        } catch (FileNotFoundException e) {

            List<Roles> roleIds = new ArrayList<Roles>(){{
                add(Roles.BASIC);
                add(Roles.TRADER);
                add(Roles.ADMIN);
            }};

            Account initialAdmin = new Account("admin", "12345", roleIds, generateValidId());

            if (!updateAccount(initialAdmin)) throw new IOException("Unable to access account storage.");

        }

    }

    private void save() throws IOException {

        FileWriter fileWriter = new FileWriter(csvFile); // create nonexistent file or overwrite outdated file
        fileWriter.write(String.join(",", headers.keySet()));

        for (String line : accounts.values()) {

            fileWriter.write(line);

        }

        fileWriter.close();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account findById(int id) {

        if (accounts.containsKey(id)) {

            String[] row = accounts.get(id).split(",");

            String username = row[headers.get("username")];
            String password = row[headers.get("password")];

            List<Integer> wishlist = Arrays.stream(row[headers.get("wishlist")].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            List<Roles> roleIds = Arrays.stream(row[headers.get("role_ids")].split(" ")).map(Roles::valueOf).collect(Collectors.toList());

            return new Account(username, password, wishlist, roleIds, id);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account findByUsername(String username) {
        for (Integer accountId : accounts.keySet()) {
            Account account = findById(accountId);
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAccount(Account account) {

        String backup = accounts.get(account.getAccountID());

        String[] row = {
                String.valueOf(account.getAccountID()),
                account.getUsername(),
                account.getPassword(),
                account.getWishlist().stream().map(String::valueOf).collect(Collectors.joining(" ")),
                account.getRolesID().stream().map(String::valueOf).collect(Collectors.joining(" "))
        };

        if (account.getWishlist().isEmpty()) row[headers.get("wishlist")] = " ";
        if (account.getRolesID().isEmpty()) row[headers.get("role_ids")] = " ";

        accounts.put(account.getAccountID(), String.join(",", row));
        try {

            save();

        } catch (IOException e) {

            accounts.put(account.getAccountID(), backup);
            return false;

        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> getAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        for (Integer accountId : accounts.keySet()) {

            allAccounts.add(findById(accountId));

        }
        return allAccounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int generateValidId() {
        if (accounts.isEmpty()) return 0;
        return Collections.max(accounts.keySet()) + 1;
    }

}
