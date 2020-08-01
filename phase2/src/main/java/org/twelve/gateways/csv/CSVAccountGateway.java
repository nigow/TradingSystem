package org.twelve.gateways.csv;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An account gateway that uses csv files as persistent storage.
 */
public class CSVAccountGateway implements AccountGateway {

    private final File csvFile;

    private final Map<String, Integer> headers;

    private final Map<Integer, String> accounts;

    /**
     * Create an account gateway that uses csv files as persistent storage.
     *
     * @param csvPath Path to the csv file holding account data
     * @throws IOException If the given csv file cannot be accessed
     */
    public CSVAccountGateway(String csvPath) throws IOException {

        csvFile = new File(csvPath);
        headers = new LinkedHashMap<>();

        headers.put("account_id", 0);
        headers.put("username", 1);
        headers.put("password", 2);
        headers.put("wishlist", 3);
        headers.put("permissions", 4);

        accounts = new HashMap<>();

        if (csvFile.length() == 0) { // according to internal docs this handles the directory case

            save();

        } else {

            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            reader.readLine(); // skip header

            String row;
            while ((row = reader.readLine()) != null) {

                String[] col = row.split(",");
                Integer accountId = Integer.valueOf(col[headers.get("account_id")]);
                accounts.put(accountId, row);

            }

            reader.close();

        }

    }

    private void save() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        writer.write(String.join(",", headers.keySet()));
        writer.newLine();

        for (String line : accounts.values()) {

            writer.write(line);
            writer.newLine();

        }

        writer.close();

    }

    private String generateRow(Account account) {

        String[] col = {
                String.valueOf(account.getAccountID()),
                account.getUsername(),
                account.getPassword(),
                account.getWishlist().stream().map(String::valueOf).collect(Collectors.joining(" ")),
                account.getPermissions().stream().map(String::valueOf).collect(Collectors.joining(" "))
        };

        if (account.getWishlist().isEmpty()) col[headers.get("wishlist")] = " ";
        if (account.getPermissions().isEmpty()) col[headers.get("permissions")] = " ";

        return String.join(",", col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account findById(int id) {

        if (accounts.containsKey(id)) {

            String[] col = accounts.get(id).split(",");

            String username = col[headers.get("username")];
            String password = col[headers.get("password")];

            List<Integer> wishlist = Arrays.stream(col[headers.get("wishlist")].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            List<Permissions> permissions = Arrays.stream(col[headers.get("permissions")].split(" ")).map(Permissions::valueOf).collect(Collectors.toList());

            return new Account(username, password, wishlist, permissions, id, "");
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

        accounts.put(account.getAccountID(), generateRow(account));

        try {

            save();

        } catch (IOException e) {

            if (backup != null) accounts.put(account.getAccountID(), backup);
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
