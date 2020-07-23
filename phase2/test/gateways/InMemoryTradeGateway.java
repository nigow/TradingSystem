package gateways;

import entities.OldTrade;
import entities.TimePlace;

import java.util.*;

public class InMemoryTradeGateway implements TradeGateway{
    public final Map<Integer, OldTrade> tradeMap;
    public final Map<Integer, TimePlace> timePlaceMap;

    public InMemoryTradeGateway(Map<Integer, OldTrade> trades, Map<Integer, TimePlace> timePlace){
        this.tradeMap = trades;
        this.timePlaceMap = timePlace;
    }

    @Override
    public OldTrade findTradeById(int id){
        if(tradeMap.containsKey(id)) return tradeMap.get(id);
        return null;
    }

    @Override
    public TimePlace findTimePlaceById(int id){
        if(timePlaceMap.containsKey(id)) return timePlaceMap.get(id);
        return null;
    }

    @Override
    public boolean updateTrade(OldTrade oldTrade, TimePlace timePlace){
        tradeMap.put(oldTrade.getId(), oldTrade);
        timePlaceMap.put(timePlace.getId(), timePlace);
        return true;
    }

    @Override
    public List<OldTrade> getAllTrades(){
        List<OldTrade> oldTradeList = new ArrayList<>();
        for(OldTrade value: tradeMap.values()){
            oldTradeList.add(value);
        }
        return oldTradeList;
    }

    @Override
    public int generateValidId(){
        if (tradeMap.size() == 0) return 0;
        return Collections.max(tradeMap.keySet()) + 1;
    }

}
