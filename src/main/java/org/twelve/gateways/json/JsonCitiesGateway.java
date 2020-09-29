package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.CitiesGateway;
import org.twelve.usecases.system.CityManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * A gateway for Cities that interacts with a Postgres database using a JSON object
 */
public class JsonCitiesGateway implements CitiesGateway {

    /**
     * GSON object that handles JSON objects between the client and the endpoints
     */
    private final Gson gson;

    /**
     * Endpoint url for getting all cities
     */
    private final String getAllCitiesUrl;

    /**
     * Endpoint url for updating a city
     */
    private final String updateCityUrl;

    /**
     * Define the endpoints and the JSON objects to interact with the database
     */
    public JsonCitiesGateway() {
        gson = new Gson();
        getAllCitiesUrl = "http://csc207phase2.herokuapp.com/cities/get_all_cities";
        updateCityUrl = "http://csc207phase2.herokuapp.com/cities/update_city";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(CityManager cityManager) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(getAllCitiesUrl);
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
                        jsonArray = gson.fromJson(line, JsonObject.class).get("cities").getAsJsonArray();
                        for (JsonElement jsonElement : jsonArray) {
                            json = jsonElement.getAsJsonObject();
                            int cityId = json.get("city_id").getAsInt();
                            String cityName = json.get("city_name").getAsString();
                            cityManager.addToCities(cityId, cityName);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public boolean save(int cityId, String cityName) {
        JsonObject json = new JsonObject();
        json.addProperty("city_id", cityId);
        json.addProperty("city_name", cityName);
        HttpURLConnection con;
        OutputStream outputStream;
        URL url;
        try {
            url = new URL(updateCityUrl);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");

            con.setDoOutput(true);
            con.connect();

            outputStream = con.getOutputStream();
            outputStream.write(json.toString().getBytes(StandardCharsets.UTF_8));
            con.getInputStream();


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
