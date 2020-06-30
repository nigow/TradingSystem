package gateways;

import entities.Item;

import java.io.IOException;
import java.util.*;

public class InMemoryItemGateway implements ItemsGateway{
    public final Map<Integer, Item> itemMap;
    public InMemoryItemGateway(Map<Integer, Item> itemMap){
        this.itemMap = itemMap;
    }
    @Override
    public Item findById(int id){
        if(itemMap.containsKey(id)) return itemMap.get(id);
        return null;
    }

    @Override
    public void updateItem(Item item){
        itemMap.put(item.getItemID(), item);
    }

    @Override
    public List<Item> getAllItems(){
        List<Item> list = new ArrayList<>();
        for(Item item: itemMap.values()) list.add(item);
        return list;
    }

    @Override
    public int generateValidId(){
        if (itemMap.size() == 0) return 0;
        return Collections.max(itemMap.keySet()) + 1;
    }

}