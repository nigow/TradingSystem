package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.entities.Account;
import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.AccountRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonAccountGateway implements AccountGateway {
    private final Gson gson;
    private final String getAllAccountsUrl;
    private final String updateAccountUrl;

    public JsonAccountGateway(){
        gson = new Gson();
        getAllAccountsUrl = "http://csc207phase2.herokuapp.com/accounts/get_all_accounts";
        updateAccountUrl = "http://csc207phase2.herokuapp.com/accounts/update_account";
    }

    @Override
    public void populate(AccountRepository accountRepository) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        List<Integer> existingAccountIds = accountRepository.getAccountIDs();
        try{
            URL url = new URL(getAllAccountsUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int status = urlConnection.getResponseCode();

            if(status==200){
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                JsonObject json;
                JsonArray jsonArray;

                while((line = bufferedReader.readLine())!=null){
                    try{
                        jsonArray = gson.fromJson(line, JsonObject.class).get("accounts").getAsJsonArray();
                        for(JsonElement jsonElement: jsonArray){
                            json = jsonElement.getAsJsonObject();
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
                                System.out.println(username);
                                accountRepository.createAccount(accountId, username, password, permissions, wishlist);

                            }
                        }


                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                inputStream.close();
                bufferedReader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(int accountId, String username, String password, List<Integer> wishlist,
                     List<String> permissions) {
        String wishlistString = "";
        for(Integer i: wishlist) wishlistString += i.toString() + " ";
        String permissionsString = "";
        for(String s: permissions) permissionsString += s + " ";
        JsonObject json = new JsonObject();
        json.addProperty("account_id", accountId);
        json.addProperty("username", username);
        json.addProperty("password", password);
        json.addProperty("wishlist", wishlistString);
        json.addProperty("permissions", permissionsString);

        HttpURLConnection con = null;
        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try{
            URL url = new URL(updateAccountUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.connect();
            outputStream = con.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(json.toString());
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                outputStream.close();
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

    }


}
