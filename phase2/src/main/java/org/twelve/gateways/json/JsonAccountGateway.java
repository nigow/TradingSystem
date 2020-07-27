package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.twelve.entities.Account;
import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.AccountRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonAccountGateway implements AccountGateway {
    private final Gson gson;
    private final String getAllAccounts;

    public JsonAccountGateway(){
        gson = new Gson();
        getAllAccounts = "http://csc207phase2.herokuapp.com/accounts/get_all_accounts"; //Todo
    }

    @Override
    public void populate(AccountRepository accountRepository) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        List<Integer> existingAccountIds = accountRepository.getAccountIDs();
        try{
            URL url = new URL(getAllAccounts);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int status = urlConnection.getResponseCode();

            if(status==200){
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                JsonObject json;

                while((line = bufferedReader.readLine())!=null){
                    try{
                        json = gson.fromJson(line, JsonObject.class);
                        int accountId = json.get("account_id").getAsInt();
                        if(!existingAccountIds.contains(accountId)){
                            String username = json.get("username").getAsString();
                            String password = json.get("password").getAsString();
                            List<Integer> wishlist = new ArrayList<>();
                            for(String s: json.get("wishlist").getAsString().split(" ")){
                                wishlist.add(Integer.parseInt(s));
                            }
                            List<String> permissions = new ArrayList<>();
                            for(String s: json.get("permissions").getAsString().split(" ")){
                                permissions.add(s);
                            }
                            accountRepository.createAccount(accountRepository.getAccounts().size(), username, password, permissions, wishlist);
                            //TODO HOW CAN I ASSIGN THE ACCOUNT ID FROM DB
                            // accountRepository.getAccounts.size()?
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(int accountId, String username, String password, List<Integer> wishlist,
                     List<String> permissions) {

    }

}
