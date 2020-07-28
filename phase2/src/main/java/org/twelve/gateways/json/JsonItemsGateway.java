package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.ItemUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonItemsGateway implements ItemsGateway {
    private final String getAllItems;
    private final Gson gson;
    public JsonItemsGateway(){
        getAllItems = "http://csc207phase2.herokuapp.com/items/get_all_items"; //TODO
        gson = new Gson();
    }
    @Override
    public void populate(ItemManager itemManager) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        List<Integer> existingItemIds = itemManager.getAllItemIds();
        try{
            URL url = new URL(getAllItems);
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
        }
    }

    @Override
    public void save(int itemId, String name, String description, boolean isApproved, int ownerId) {

    }

}
