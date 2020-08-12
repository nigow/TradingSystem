package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.gateways.CitiesGateway;

import java.util.*;

/**
 * Stores all cities in the program, and provides abilities
 * to get city info, add cities, and change cities for an account.
 */
public class CityManager {
    private final Map<Integer, String> cities;
    private CitiesGateway citiesGateway;
    private final AccountRepository accountRepository;

    /**
     * Constructor for cityManager which stores a cityGateway.
     *
     * @param citiesGateway the gateway dealing with cities.
     */
    public CityManager(CitiesGateway citiesGateway, AccountRepository accountRepository){
        this.cities = new HashMap<>();
        this.citiesGateway = citiesGateway;
        this.accountRepository = accountRepository;
        citiesGateway.populate(this);
    }

    /**
     * Switch cities gateway to a demo mode gateway.
     * @param citiesGateway A new CitiesGateway.
     */
    void switchToDemoMode(CitiesGateway citiesGateway) {
        this.citiesGateway = citiesGateway;
        for (int city : cities.keySet()) {
            updateToGateway(city);
        }
    }

    /**
     * Switch cities gateway to a normal gateway.
     * @param citiesGateway A new CitiesGateway.
     */
    void switchToNormalMode(CitiesGateway citiesGateway) {
        this.citiesGateway = citiesGateway;
        cities.clear();
        citiesGateway.populate(this);
    }

    /**
     * Get the location where an account is located.
     * @param accountID The account id.
     * @return An account's location.
     */
    public String getLocationOfAccount(int accountID) {
        return accountRepository.getAccountFromID(accountID).getLocation();
    }

    /**
     * Change the location of an account.
     * @param accountID An account id.
     * @param newCity The new city of the account.
     */
    public void changeAccountLocation(int accountID, String newCity) {
        if (!cities.containsValue(newCity))
            createCity(newCity);
        Account account = accountRepository.getAccountFromID(accountID);
        account.setLocation(newCity);
        accountRepository.updateToAccountGateway(account);
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
        int cityId = (cities.isEmpty() ? 1 : Collections.max(cities.keySet()) + 1);
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
