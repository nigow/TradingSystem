package gateways;

import entities.Item;

import java.util.*;

public class OldInMemoryItemGateway implements ItemsGateway{
    public final Map<Integer, Item> itemMap;
    public OldInMemoryItemGateway(Map<Integer, Item> itemMap){
        this.itemMap = itemMap;
    }

    @Override
    public Item findById(int id){
        if(itemMap.containsKey(id)) return itemMap.get(id);
        return null;
    }

    @Override
    public boolean updateItem(Item item){
        itemMap.put(item.getItemID(), item);
        return true;
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

    @Override
    public boolean deleteItem(Item item) {
        if(itemMap.containsKey(item.getItemID())){
            itemMap.remove(item.getItemID());
            return true;
        }
        return false;
    }
}
