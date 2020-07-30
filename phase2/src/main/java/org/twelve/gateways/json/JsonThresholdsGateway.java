package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.twelve.gateways.ThresholdsGateway;
import org.twelve.usecases.ThresholdRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonThresholdsGateway implements ThresholdsGateway {

    private HttpClient httpClient;
    private Gson gson;

    public JsonThresholdsGateway() {

        httpClient = HttpClient.newHttpClient();
        gson = new Gson();

    }

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

            thresholdRepository.createThresholds(lendMoreThanBorrow, maxIncompleteTrade, maxWeeklyTrade, numberOfDays,
                    numberOfEdits, numberOfStats);

            return true;

        } catch (IOException | InterruptedException e) {
            return false;
        }

    }

    @Override
    public boolean save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                     int numberOfEdits, int numberOfStats) {

        JsonObject json = new JsonObject();
        json.addProperty("lend_limit", lendMoreThanBorrow);
        json.addProperty("incomplete_limit", maxIncompleteTrade);
        json.addProperty("weekly_limit", maxWeeklyTrade);
        json.addProperty("number_of_days", numberOfDays);
        json.addProperty("number_of_edits", numberOfEdits);
        json.addProperty("number_of_stats", numberOfStats);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(json)))
                .uri(URI.create("http://csc207phase2.herokuapp.com/thresholds"))
                .build();

        try {

            httpClient.send(postRequest, HttpResponse.BodyHandlers.discarding());
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
