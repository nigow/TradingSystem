package org.twelve.gateways.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.TradeRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A gateway for Trades that interacts with a Postgres database using a JSON object
 */
public class JsonTradeGateway implements TradeGateway {

    /**
     * GSON object that handles JSON objects between the client and the endpoints
     */
    private final Gson gson;

    /**
     * Endpoint url for getting all trades
     */
    private final String getAllTradeUrl;

    /**
     * Endpoint url for updating a trade
     */
    private final String updateTradeUrl;

    /**
     * Endpoint url for creating a trade
     */
    private final String createTradeUrl;

    /**
     * Define the endpoints and the JSON objects to interact with the database
     */
    public JsonTradeGateway(){
        gson = new Gson();
        getAllTradeUrl = "http://csc207phase2.herokuapp.com/trades/get_all_trades";
        updateTradeUrl = "http://csc207phase2.herokuapp.com/trades/update_trade";
        createTradeUrl = "http://csc207phase2.herokuapp.com/trades/create_trade";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(TradeRepository tradeRepository) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
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
                    try {
                        jsonObj = gson.fromJson(line, JsonObject.class);
                        jsonArray = jsonObj.get("trades").getAsJsonArray();
                        for (JsonElement jsonElement : jsonArray) {
                            JsonObject json = jsonElement.getAsJsonObject();
                            int tradeId = json.get("trade_id").getAsInt();
                            boolean isPermanent = json.get("is_permanent").getAsBoolean();
                            List<Integer> tradersIds = new ArrayList<>();
                            for (String s : json.get("traders_ids").getAsString().split(" ")) {
                                tradersIds.add(Integer.parseInt(s));
                            }

                            List<List<Integer>> allItemIds = new ArrayList<>();
                            String[] itemIdArray = json.get("item_ids").getAsString().split(" ");
                            for (String itemIds : itemIdArray) {

                                List<Integer> ids = new ArrayList<>();

                                if (!itemIds.equals("*")) {
                                    ids.addAll(Arrays.stream(itemIds.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
                                }

                                allItemIds.add(ids);

                            }

                            /*
                            if(itemIdArray.length == 1){
                                List<Integer> temp = new ArrayList<>();
                                temp.add(Integer.parseInt(itemIdArray[0]));
                                List<Integer> newTemp = new ArrayList<>();
                                allItemIds.add(temp);
                                allItemIds.add(newTemp);
                            }
                            else{
                                for(String outerString: itemIdArray){
                                    List<String> temp = Arrays.asList(outerString.split(" "));
                                    List<Integer> integerList = new ArrayList<>();
                                    for(String number: temp){
                                        integerList.add(Integer.parseInt(number));
                                    }
                                    allItemIds.add(integerList);
                                }
                            }
                            */

                            String tradeStatus = json.get("trade_status").getAsString();
                            int editCounter = json.get("edit_counter").getAsInt();
                            List<Boolean> tradeCompletions = new ArrayList<>();
                            for (String s : json.get("trade_completions").getAsString().split(" ")) {
                                tradeCompletions.add(Boolean.valueOf(s));
                            }
                            String location = json.get("location").getAsString();
                            String time = json.get("time").getAsString();

                            tradeRepository.addToTrades(tradeId, isPermanent, tradersIds, allItemIds, editCounter, tradeStatus, tradeCompletions, time, location);
                        }


                    } catch (RuntimeException e) {
                        //e.printStackTrace();
                        return false;
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
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    //memo: learnt from this https://qiita.com/QiitaD/items/289dd82ba3ce03a915a0
    @Override
    public boolean save(int tradeId, boolean isPermanent, List<Integer> traderIds, List< List<Integer> > allItemIds,
                        int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time,
                        String location, boolean newTrade) {
        StringBuilder traderIdsString = new StringBuilder();
        for(Integer i: traderIds) traderIdsString.append(i.toString()).append(" ");

        List<String> allItemIdsStr = new ArrayList<>();
        for (List<Integer> itemIds : allItemIds) {

            if (itemIds.isEmpty()) {

                allItemIdsStr.add("*");

            } else {

                allItemIdsStr.add(itemIds.stream().map(String::valueOf).collect(Collectors.joining(",")));

            }

        }

        /*
        String itemIdsString = "";
        for(List<Integer> outerList: allItemIds){
            for(int innerInt: outerList){
                itemIdsString += innerInt + " ";
            }
            if(itemIdsString.length() > 0){
                itemIdsString = itemIdsString.substring(0, itemIdsString.length() - 1) + "!";
            }
        }

        itemIdsString = itemIdsString.substring(0, itemIdsString.length() - 1);

         */
        StringBuilder tradeCompletionsString = new StringBuilder();
        for(Boolean b: tradeCompletions) tradeCompletionsString.append(b.toString()).append(" ");
        JsonObject json = new JsonObject();

        json.addProperty("trade_id", tradeId);
        json.addProperty("is_permanent", isPermanent);
        json.addProperty("traders_ids", traderIdsString.toString());
        json.addProperty("item_ids", String.join(" ", allItemIdsStr));
        json.addProperty("edit_counter", editedCounter);
        json.addProperty("trade_status", tradeStatus);
        json.addProperty("trade_completions", tradeCompletionsString.toString());
        json.addProperty("time", time);
        json.addProperty("location", location);

        OutputStream outputStream;
        URL url;
        HttpURLConnection con;
        try{
            if (newTrade) url = new URL(createTradeUrl);
            else url = new URL(updateTradeUrl);

            con = (HttpURLConnection) url.openConnection();

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
