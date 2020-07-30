package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonTradeGateway implements TradeGateway {

    private final Gson gson;
    private final String getAllTradeUrl;
    private final String updateTradeUrl;

    public JsonTradeGateway(){
        gson = new Gson();
        getAllTradeUrl = "http://csc207phase2.herokuapp.com/trades/get_all_trades";
        updateTradeUrl = "http://csc207phase2.herokuapp.com/trades/update_trade";

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
        }finally{
            try{
                inputStream.close();
                bufferedReader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    //memo: learnt from this https://qiita.com/QiitaD/items/289dd82ba3ce03a915a0
    @Override
    public boolean save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<Integer> itemIds, int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time, String location) {
        String traderIdsString = "";
        for(Integer i: traderIds) traderIdsString += i.toString() + " ";
        String itemIdsString = "";
        for(Integer i: itemIds) itemIdsString += i.toString() + " ";
        String tradeCompletionsString = "";
        for(Boolean b: tradeCompletions) tradeCompletionsString += b.toString() + " ";
        JsonObject json = new JsonObject();

        json.addProperty("trade_id", tradeId);
        json.addProperty("is_permanent", isPermanent);
        json.addProperty("trader_ids", traderIdsString);
        json.addProperty("item_ids", itemIdsString);
        json.addProperty("edited_counter", editedCounter);
        json.addProperty("trade_status", tradeStatus);
        json.addProperty("trade_completions", tradeCompletionsString);
        json.addProperty("time", time);
        json.addProperty("location", location);
        System.out.println(json.toString().getBytes(StandardCharsets.UTF_8));

        OutputStream outputStream = null;
        try{
            URL url = new URL(updateTradeUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
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

    public static void main(String[] args) {
        JsonTradeGateway jsonTradeGateway = new JsonTradeGateway();
        List<Integer> trader_ids = new ArrayList<>();
        trader_ids.add(2);
        trader_ids.add(3);
        List<Integer> item_ids = new ArrayList<>();
        item_ids.add(1);
        List<Boolean> tradeCompletions = new ArrayList<>();
        jsonTradeGateway.save(3,false, trader_ids, item_ids,1,"UNCONFIRMED",tradeCompletions,"fsalj","UTM");

    }


}
