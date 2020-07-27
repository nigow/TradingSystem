package org.twelve.gateways.json;

import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.TradeUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class JsonTradeGateway implements TradeGateway {

    //TODO
    @Override
    public void populate(TradeUtility tradeUtility) {
        String urlString = "http://csc207phase2.herokuapp.com/trades/get_all_trades"; //this has to migrate to the parameter
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try{
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int status = urlConnection.getResponseCode();

            if(status==200){
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                String output = null;

                while((line = bufferedReader.readLine())!=null){
                    output = line;
                }
                System.out.println(output);
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
