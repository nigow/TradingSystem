package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.entities.Trade;
import org.twelve.entities.TradeStatus;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.ItemsGateway;
import org.twelve.gateways.ThresholdsGateway;
import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonTradeGateway implements TradeGateway {

    private final Gson gson;
    private final String getAllTradeUrl;
    private final String updateTradeUrl;

    public JsonTradeGateway(){
        gson = new Gson();
        getAllTradeUrl = "http://csc207phase2.herokuapp.com/trades/get_all_trades"; //this has to migrate to the parameter
        updateTradeUrl = ""; //Todo

    }

    //TODO
    @Override
    public void populate(TradeManager tradeManager) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        List<Integer> existingTradeIds = tradeManager.getAllTradesIds();
        try{
            URL url = new URL(getAllTradeUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int status = urlConnection.getResponseCode();

            if(status==200){
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                JsonObject jsonObj;
                JsonArray jsonArray;

                while((line = bufferedReader.readLine())!=null){
                    try{
                        jsonObj = gson.fromJson(line, JsonObject.class);
                        jsonArray = jsonObj.get("trades").getAsJsonArray();
                        for(JsonElement jsonElement: jsonArray){
                            JsonObject json = jsonElement.getAsJsonObject();
                            int tradeId = json.get("trade_id").getAsInt();
                            if(!existingTradeIds.contains(tradeId)){
                                boolean isPermanent = json.get("is_permanent").getAsBoolean();
                                List<Integer> tradersIds = new ArrayList<>();
                                for(String s: json.get("traders_ids").getAsString().split(" ")){
                                    tradersIds.add(Integer.parseInt(s));
                                }
                                List<Integer> itemIds = new ArrayList<>();
                                for(String s: json.get("item_ids").getAsString().split(" ")){
                                    itemIds.add(Integer.parseInt(s));
                                }
                                String tradeStatus = json.get("trade_status").getAsString();
                                int editCounter = json.get("edit_counter").getAsInt();
                                List<Boolean> tradeCompletions = new ArrayList<>();
                                for(String s: json.get("trade_completions").getAsString().split(" ")){
                                    tradeCompletions.add(Boolean.valueOf(s));
                                }
                                String location = json.get("location").getAsString();
                                String time = json.get("time").getAsString();
                                System.out.println(time);
                                tradeManager.addToTrades(tradeId, isPermanent, tradersIds, itemIds, editCounter, tradeStatus, tradeCompletions, "a", location);
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
    public void save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<Integer> itemIds, int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time, String location) {
        //TODO
    }



}
