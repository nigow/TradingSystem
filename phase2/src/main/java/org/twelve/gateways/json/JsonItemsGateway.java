package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.ItemUtility;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonItemsGateway implements ItemsGateway {
    private final String getAllItemsUrl;
    private final String updateItemUrl;
    private final Gson gson;

    public JsonItemsGateway(){
        getAllItemsUrl = "http://csc207phase2.herokuapp.com/items/get_all_items";
        updateItemUrl = "http://csc207phase2.herokuapp.com/items/update_item";
        gson = new Gson();
    }

    @Override
    public void populate(ItemManager itemManager) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        List<Integer> existingItemIds = itemManager.getAllItemIds();
        try{
            URL url = new URL(getAllItemsUrl);
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
                        jsonArray = gson.fromJson(line, JsonObject.class).get("items").getAsJsonArray();
                        for(JsonElement jsonElement: jsonArray){
                            json = jsonElement.getAsJsonObject();
                            int itemId = json.get("item_id").getAsInt();
                            if(!existingItemIds.contains(itemId)){
                                String name = json.get("name").getAsString();
                                String description = json.get("description").getAsString();
                                Boolean isApproved = json.get("is_approved").getAsBoolean();
                                int ownerID = json.get("owner_id").getAsInt();
                                itemManager.addToItems(itemId, name, description, ownerID, isApproved);
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
            }finally{
            }
        }
    }

    @Override
    public boolean save(int itemId, String name, String description, boolean isApproved, int ownerId) {
        JsonObject json = new JsonObject();
        json.addProperty("item_id", itemId);
        json.addProperty("name", name);
        json.addProperty("description", description);
        json.addProperty("is_approved", isApproved);
        json.addProperty("owner_id", ownerId);

        HttpURLConnection con = null;
        OutputStream outputStream = null;
        try{
            URL url = new URL(updateItemUrl);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST"); // this is needed otherwise 405 error (default is GET)
            con.setRequestProperty("Content-Type", "application/json; utf-8"); // this is needed otherwise 502 error

            con.setDoOutput(true);
            con.connect();
            outputStream = con.getOutputStream();
            outputStream.write(json.toString().getBytes(StandardCharsets.UTF_8));
            con.getInputStream();


        }catch(IOException e){
            e.printStackTrace();
            return false;
        }finally{
            try{
                outputStream.close();
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                return true;
            }
        }

    }


}
