package ua.travel.service;

import ua.travel.dao.repositories.impl.CityRepository;
import ua.travel.dao.repositories.impl.HotelRepository;
import ua.travel.entity.City;
import ua.travel.entity.Hotel;
import ua.travel.service.exceptions.ServiceException;

import java.util.List;

/**
 * Created by yuuto on 5/26/17.
 */
public class HotelService {

    private static HotelService hotelService;
    private CityRepository cityRepository = CityRepository.getInstance();
    private HotelRepository hotelRepository = HotelRepository.getInstance();
    private CityService cityService = CityService.getInstance();

    private HotelService(){}

    public static HotelService getInstance() {
        HotelService localInstance = hotelService;
        if (localInstance == null) {
            synchronized (HotelService.class) {
                localInstance = hotelService;
                if (localInstance == null) {
                    hotelService = localInstance = new HotelService();
                }
            }
        }
        return localInstance;
    }

    public Hotel createHotel(String cityId, String name, String star, String photoUrl) throws ServiceException {
        City city = cityRepository.findById(Long.parseLong(cityId)).orElseThrow(()->new ServiceException("Cant find city by id: " + cityId));
        Hotel hotel = new Hotel();
        hotel.setCity(city);
        hotel.setName(name);
        hotel.setStar(Integer.parseInt(star));
        hotel.setPhotoUrl(photoUrl);
        hotel.setId(hotelRepository.save(hotel));
        return hotel;
    }

    public List<Hotel> getHotelsByCity(Long cityId) throws ServiceException {
        City city = cityRepository.findById(cityId).orElseThrow(()->new ServiceException("Cant find city by id " + cityId));
        return hotelRepository.findByCity(city);
    }

    public List<Hotel> getAllHotel(){
        return hotelRepository.findAll();
    }
}
