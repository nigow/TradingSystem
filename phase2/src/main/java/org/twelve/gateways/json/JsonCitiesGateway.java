package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.CitiesGateway;
import org.twelve.usecases.CityManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonCitiesGateway implements CitiesGateway {
    private final Gson gson;
    private final String getAllCitiesUrl;
    private final String updateCityUrl;

    public JsonCitiesGateway(){
        gson = new Gson();
        getAllCitiesUrl = "http://csc207phase2.herokuapp.com/cities/get_all_cities";
        updateCityUrl = "http://csc207phase2.herokuapp.com/cities/update_city";
    }


    @Override
    public boolean populate(CityManager cityManager) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try{
            URL url = new URL(getAllCitiesUrl);
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
                        jsonArray = gson.fromJson(line, JsonObject.class).get("cities").getAsJsonArray();
                        for(JsonElement jsonElement: jsonArray){
                            json = jsonElement.getAsJsonObject();
                            int cityId = json.get("city_id").getAsInt();
                            String cityName = json.get("city_name").getAsString();
                            cityManager.createCity(cityId, cityName);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                return false;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            assert inputStream != null;
            inputStream.close();
            bufferedReader.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }

    }

    //Todo Figure out why 502 error happens
    //Todo Make error log on the server side
    @Override
    public boolean save(int cityId, String cityName) {
        JsonObject json = new JsonObject();
        json.addProperty("city_id", cityId);
        json.addProperty("city_name", cityName);
        HttpURLConnection con;
        OutputStream outputStream;
        URL url;
        try{
            url = new URL(updateCityUrl);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");

            con.setDoOutput(true);
            con.connect();

            outputStream = con.getOutputStream();
            outputStream.write(json.toString().getBytes(StandardCharsets.UTF_8));
            con.getInputStream();


        }catch (IOException e) {
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
