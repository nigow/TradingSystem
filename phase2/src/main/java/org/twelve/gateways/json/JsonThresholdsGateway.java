package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.twelve.gateways.ThresholdsGateway;
import org.twelve.usecases.system.ThresholdRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A gateway for Thresholds that interacts with a Postgres database using a JSON object
 */
public class JsonThresholdsGateway implements ThresholdsGateway {

    /**
     * HTTP Client object that handles HTTP requests
     */
    private final HttpClient httpClient;

    /**
     * GSON object that handles JSON objects between the client and the endpoints
     */
    private final Gson gson;

    /**
     * Define the JSON object and http objects to interact with the DB
     */
    public JsonThresholdsGateway() {

        httpClient = HttpClient.newHttpClient();
        gson = new Gson();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(ThresholdRepository thresholdRepository) {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://csc207phase2.herokuapp.com/thresholds"))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            JsonObject json = gson.fromJson(response.body(), JsonObject.class);

            int lendMoreThanBorrow = json.get("lend_limit").getAsInt();
            int maxIncompleteTrade = json.get("incomplete_limit").getAsInt();
            int maxWeeklyTrade = json.get("weekly_limit").getAsInt();
            int numberOfDays = json.get("number_of_days").getAsInt();
            int numberOfEdits = json.get("number_of_edits").getAsInt();
            int numberOfStats = json.get("number_of_stats").getAsInt();
            int requiredTradesForTrusted = json.get("required_trades_for_trusted").getAsInt();

            thresholdRepository.createThresholds(lendMoreThanBorrow, maxIncompleteTrade, maxWeeklyTrade, numberOfDays,
                    numberOfEdits, numberOfStats, requiredTradesForTrusted);

            return true;

        } catch (IOException | InterruptedException e) {
            return false;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                        int numberOfEdits, int numberOfStats, int requiredTradesForTrusted) {

        JsonObject json = new JsonObject();
        json.addProperty("lend_limit", lendMoreThanBorrow);
        json.addProperty("incomplete_limit", maxIncompleteTrade);
        json.addProperty("weekly_limit", maxWeeklyTrade);
        json.addProperty("number_of_days", numberOfDays);
        json.addProperty("number_of_edits", numberOfEdits);
        json.addProperty("number_of_stats", numberOfStats);
        json.addProperty("required_trades_for_trusted", requiredTradesForTrusted);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(json)))
                .uri(URI.create("http://csc207phase2.herokuapp.com/thresholds"))
                .build();

        try {

            //HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
