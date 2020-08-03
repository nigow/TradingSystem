package org.twelve.gateways.ram;

import org.twelve.gateways.CitiesGateway;
import org.twelve.usecases.CityManager;

import java.util.Map;

public class InMemoryCitiesGateway implements CitiesGateway {
    public final Map<Integer, String> cities;
    public InMemoryCitiesGateway(Map<Integer, String> cities){
        this.cities = cities;
    }

    @Override
    public boolean populate(CityManager cityManager) {
        for(int id: cities.keySet()){
            cityManager.createCity(id, cities.get(id));
        }
        return true;
    }

    @Override
    public boolean save(int cityId, String cityName) {
        cities.put(cityId, cityName);
        return true;
    }
}
