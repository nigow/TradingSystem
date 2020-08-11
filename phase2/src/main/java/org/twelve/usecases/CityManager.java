package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.gateways.AccountGateway;
import org.twelve.gateways.CitiesGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityManager {
    private Map<Integer, String> cities;
    private CitiesGateway citiesGateway;

    /**
     * Constructor for cityManager which stores a cityGateway.
     *
     * @param citiesGateway the gateway dealing with cities.
     */
    public CityManager(CitiesGateway citiesGateway){
        this.cities = new HashMap<>();
        this.citiesGateway = citiesGateway;
        citiesGateway.populate(this);
    }

    public void switchToDemoMode(CitiesGateway citiesGateway) {
        this.citiesGateway = citiesGateway;
        for (int city : cities.keySet()) {
            updateToGateway(city);
        }
    }

    public void switchToNormalMode(CitiesGateway citiesGateway) {
        this.citiesGateway = citiesGateway;
        cities.clear();
        citiesGateway.populate(this);
    }

    /**
     * Updates the city with the given id to the gateway for cities
     *
     * @param cityId the id of the city to be updated
     */
    private void updateToGateway(int cityId) {
        citiesGateway.save(cityId, cities.get(cityId));
    }

    /**
     * Creates a city with the given city name and updates it to the gateway and local storage
     *
     * @param cityName the name of the city
     */
    public void createCity(String cityName){
        int cityId = cities.size();
        cities.put(cityId, cityName);
        updateToGateway(cityId);
    }

    /**
     * Adds the given city and city id to local storage
     *
     * @param cityId the id of the city
     * @param cityName the name of the city
     */
    public void addToCities(int cityId, String cityName) {
        cities.put(cityId, cityName);
    }

    /**
     * Returns a list of all the cities
     *
     * @return an arraylist of all the cities in local storage
     */
    public List<String> getAllCities() {
        return new ArrayList<>(cities.values());
    }
}
