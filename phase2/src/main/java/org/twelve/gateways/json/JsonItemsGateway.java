package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.ItemsGateway;
import org.twelve.usecases.ItemManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * A gateway for Items that interacts with a Postgres database using a JSON object
 */
public class JsonItemsGateway implements ItemsGateway {

    /**
     * Endpoint url for getting all items
     */
    private final String getAllItemsUrl;

    /**
     * Endpoint url for updating an item
     */
    private final String updateItemUrl;

    /**
     * Endpoint url for creating an item
     */
    private final String createItemUrl;

    /**
     * GSON object that handles JSON objects between the client and the endpoints
     */
    private final Gson gson;

    /**
     * Define the endpoints and the JSON objects to interact with the database
     */
    public JsonItemsGateway(){
        getAllItemsUrl = "http://csc207phase2.herokuapp.com/items/get_all_items";
        updateItemUrl = "http://csc207phase2.herokuapp.com/items/update_item";
        createItemUrl = "http://csc207phase2.herokuapp.com/items/create_item";
        gson = new Gson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(ItemManager itemManager) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        //List<Integer> existingItemIds = itemManager.getAllItemIds();
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
                            //if(!existingItemIds.contains(itemId)){
                                String name = json.get("name").getAsString();
                                String description = json.get("description").getAsString();
                                boolean isApproved = json.get("is_approved").getAsBoolean();
                                int ownerID = json.get("owner_id").getAsInt();
                                itemManager.addToItems(itemId, name, description, ownerID, isApproved);
                            //}

                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        try{
            assert inputStream != null;
            inputStream.close();
            bufferedReader.close();
            return true;
        }catch(IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int itemId, String name, String description, boolean isApproved, int ownerId, boolean newItem) {
        JsonObject json = new JsonObject();
        json.addProperty("item_id", itemId);
        json.addProperty("name", name);
        json.addProperty("description", description);
        json.addProperty("is_approved", isApproved);
        json.addProperty("owner_id", ownerId);

        HttpURLConnection con;
        OutputStream outputStream;
        URL url;
        try{

            if(newItem) url = new URL(createItemUrl);
            else url = new URL(updateItemUrl);
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
        }
        try{
            outputStream.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }

    }


}
