package org.twelve.gateways;

import org.twelve.usecases.CityManager;

/**
 * A gateway for Cities that interacts with external storage.
 */
public interface CitiesGateway {
    /**
     * Method that syncs the external storage information into the in-memory city storage
     * @param cityManager local storage for cities
     * @return whether or not the sync was successful
     */
    boolean populate(CityManager cityManager);

    /**
     * Method that syncs a local city's update to the external storage
     *
     * @param cityId city id of the city
     * @param cityName name of the city
     * @return whether or not the sync was successful
     */
    boolean save(int cityId, String cityName);
}
