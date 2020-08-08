package org.twelve.gateways.ram;

import org.twelve.gateways.CitiesGateway;
import org.twelve.usecases.CityManager;

import java.util.Map;

public class InMemoryCitiesGateway implements CitiesGateway {

    /**
     * pseudo-external storage of cities
     */
    private final Map<Integer, String> cities;

    /**
     * Initializes the gateway
     * @param cities a key-pair set of a city id and city object
     */
    public InMemoryCitiesGateway(Map<Integer, String> cities){
        this.cities = cities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(CityManager cityManager) {
        for(int id: cities.keySet()){
            cityManager.addToCities(id, cities.get(id));
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int cityId, String cityName) {
        cities.put(cityId, cityName);
        return true;
    }
}
