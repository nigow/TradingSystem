package gateways;

import entities.TimePlace;
import entities.Trade;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTradeGateway implements TradeGateway{
    public final Map<Integer, Trade> tradeMap;
    public final Map<Integer, TimePlace> timePlaceMap;

    public InMemoryTradeGateway(Map<Integer, Trade> trades, Map<Integer, TimePlace> timePlace){
        this.tradeMap = trades;
        this.timePlaceMap = timePlace;
    }

    @Override
    public Trade findTradeById(int id){
        if(tradeMap.containsKey(id)) return tradeMap.get(id);
        return null;
    }

    @Override
    public TimePlace findTimePlaceById(int id){
        if(timePlaceMap.containsKey(id)) return timePlaceMap.get(id);
        return null;
    }

    @Override
    public boolean updateTrade(Trade trade, TimePlace timePlace){
        tradeMap.put(trade.getId(), trade);
        timePlaceMap.put(timePlace.getId(), timePlace);
        return true;
    }

    @Override
    public List<Trade> getAllTrades(){
        List<Trade> tradeList = new ArrayList<>();
        for(Trade value: tradeMap.values()){
            tradeList.add(value);
        }
        return tradeList;
    }

    @Override
    public int generateValidId(){
        if (tradeMap.size() == 0) return 0;
        return Collections.max(tradeMap.keySet()) + 1;
    }

}
