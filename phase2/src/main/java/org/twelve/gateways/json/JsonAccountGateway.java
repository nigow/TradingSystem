package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.account.AccountRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A gateway for Accounts that interacts with a Postgres database using a JSON object
 */
public class JsonAccountGateway implements AccountGateway {

    /**
     * GSON object that handles JSON objects between the client and the endpoints
     */
    private final Gson gson;

    /**
     * Endpoint url for getting all accounts
     */
    private final String getAllAccountsUrl;

    /**
     * Endpoint url for updating an account
     */
    private final String updateAccountUrl;

    /**
     * Endpoint url for creating an account
     */
    private final String createAccountUrl;


    /**
     * Define the endpoints and the JSON objects to interact with the database
     */
    public JsonAccountGateway() {
        gson = new Gson();
        getAllAccountsUrl = "http://csc207phase2.herokuapp.com/accounts/get_all_accounts";
        updateAccountUrl = "http://csc207phase2.herokuapp.com/accounts/update_account";
        createAccountUrl = "http://csc207phase2.herokuapp.com/accounts/create_account";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(AccountRepository accountRepository) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(getAllAccountsUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int status = urlConnection.getResponseCode();

            if (status == 200) {
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                JsonObject json;
                JsonArray jsonArray;

                while ((line = bufferedReader.readLine()) != null) {
                    try {
                        jsonArray = gson.fromJson(line, JsonObject.class).get("accounts").getAsJsonArray();
                        for (JsonElement jsonElement : jsonArray) {
                            json = jsonElement.getAsJsonObject();
                            int accountId = json.get("account_id").getAsInt();
                            String username = json.get("username").getAsString();
                            String password = json.get("password").getAsString();
                            List<Integer> wishlist = new ArrayList<>();


                            for (String s : json.get("wishlist").getAsString().split(" ")) {
                                wishlist.add(Integer.parseInt(s));
                            }
                            List<String> permissions = new ArrayList<>();
                            Collections.addAll(permissions, json.get("permissions").getAsString().split(" "));
                            String location = json.get("city").getAsString();

                            accountRepository.createAccount(accountId, username, password, permissions, wishlist, location);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            assert inputStream != null;
            inputStream.close();
            bufferedReader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int accountId, String username, String password, List<Integer> wishlist,
                        List<String> permissions, String location, boolean newAccount) {
        StringBuilder wishlistString = new StringBuilder(wishlist.isEmpty() ? " " : "");
        for (Integer i : wishlist) wishlistString.append(i.toString()).append(" ");
        StringBuilder permissionsString = new StringBuilder(permissions.isEmpty() ? " " : "");
        for (String s : permissions) permissionsString.append(s).append(" ");
        JsonObject json = new JsonObject();
        json.addProperty("account_id", accountId);
        json.addProperty("username", username);
        json.addProperty("password", password);
        json.addProperty("wishlist", wishlistString.toString());
        json.addProperty("permissions", permissionsString.toString());
        json.addProperty("city", location);


        HttpURLConnection con;
        OutputStream outputStream;
        URL url;
        try {
            if (newAccount) url = new URL(createAccountUrl);
            else url = new URL(updateAccountUrl);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST"); // this is needed otherwise 405 error (default is GET)
            con.setRequestProperty("Content-Type", "application/json; utf-8"); // this is needed otherwise 502 error
            con.setDoOutput(true);
            con.connect();

            outputStream = con.getOutputStream();
            outputStream.write(json.toString().getBytes(StandardCharsets.UTF_8));


            con.getInputStream(); // this is needed otherwise the request doesn't go out

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


}
