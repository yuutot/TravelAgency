package ua.travel.service;

import ua.travel.dao.repositories.impl.CityRepository;
import ua.travel.entity.City;

import java.util.List;
import java.util.Optional;

/**
 * Created by yuuto on 5/26/17.
 */
public class CityService {

    private static CityService cityService;
    private CityRepository cityRepository = CityRepository.getInstance();

    private CityService() {
    }

    public static CityService getInstance() {
        CityService localInstance = cityService;
        if (localInstance == null) {
            synchronized (CityService.class) {
                localInstance = cityService;
                if (localInstance == null) {
                    cityService = localInstance = new CityService();
                }
            }
        }
        return localInstance;
    }

    public City createCity(String name){
        Optional<City> cityOptional = cityRepository.findByName(name);
        if(cityOptional.isPresent()){
            return cityOptional.get();
        }
        City city = new City();
        city.setName(name);
        city.setId(cityRepository.save(city));
        return city;
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }
}
