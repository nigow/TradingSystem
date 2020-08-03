package org.twelve.gateways;

import org.twelve.usecases.CityManager;

public interface CitiesGateway {
    boolean populate(CityManager cityManager);

    boolean save(int cityId, String cityName);
}
