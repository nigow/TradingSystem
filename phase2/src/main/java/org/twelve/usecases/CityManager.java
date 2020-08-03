package org.twelve.usecases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityManager {
    private Map<Integer, String> cities;
    public CityManager(){
        this.cities = new HashMap<>();
        //Do something
    }


    public void createCity(int cityId, String cityName){
        cities.put(cityId, cityName);
    }

    //For debugging
    //public List<Integer> getAllCityIds() {return new ArrayList<>(cities.keySet());}

    //For debugging
    //public String cityName(int id){ return cities.get(id);}
}
