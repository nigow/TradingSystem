package org.twelve.usecases;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.gateways.CitiesGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityManager {
    private Map<Integer, String> cities;
    private final CitiesGateway citiesGateway;

    public CityManager(CitiesGateway citiesGateway){
        this.cities = new HashMap<>();
        this.citiesGateway = citiesGateway;
        citiesGateway.populate(this);
        //Do something
    }

    private void updateToGateway(int cityId) {
        citiesGateway.save(cityId, cities.get(cityId));
    }

    public void createCity(String cityName){
        int cityId = cities.size();
        cities.put(cityId, cityName);
        updateToGateway(cityId);
    }

    public void addToCities(int cityId, String cityName) {
        cities.put(cityId, cityName);
    }

    //For debugging
    //public List<Integer> getAllCityIds() {return new ArrayList<>(cities.keySet());}

    //For debugging
    //public String cityName(int id){ return cities.get(id);}
}
