package org.twelve.gateways.ram;

import org.twelve.gateways.CitiesGateway;
import org.twelve.usecases.CityManager;

import java.util.Map;

/**
 * pseudo-external storage of cities and gateway
 * @author Tairi
 */
public class InMemoryCitiesGateway implements CitiesGateway {


    private final Map<Integer, String> cities;

    /**
     * Initializes the external storage
     * @param cities pseudo-external storage of cities
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
