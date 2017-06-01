package ua.travel.service;

import ua.travel.dao.repositories.impl.CityRepository;
import ua.travel.dao.repositories.impl.HotelRepository;
import ua.travel.dao.repositories.impl.TourRepository;
import ua.travel.entity.City;
import ua.travel.entity.Hotel;
import ua.travel.entity.Tour;
import ua.travel.entity.enums.TourType;
import ua.travel.entity.enums.TransportType;
import ua.travel.service.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * Created by yuuto on 5/26/17.
 */
public class TourService {

    private static TourService tourService;
    private HotelRepository hotelRepository = HotelRepository.newInstance();
    private TourRepository tourRepository = TourRepository.newInstance();
    private CityRepository cityRepository = CityRepository.newInstance();

    public static synchronized TourService newInstance() {
        if (tourService == null) {
            tourService = new TourService();
        }
        return tourService;
    }

    public Tour createTour(TourType tourType, Date dateTo, Date dateFrom, Double cost, String description, TransportType transportType, Long hotelId) throws ServiceException {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ServiceException("Cant find hotel by id: " + hotelId));
        Tour tour = new Tour();
        tour.setTourType(tourType);
        tour.setDateTo(dateTo);
        tour.setDateFrom(dateFrom);
        tour.setCost(cost);
        tour.setDescription(description);
        tour.setTransportType(transportType);
        tour.setHotel(hotel);
        tour.setId(tourRepository.save(tour));
        return tour;
    }

    public List<Tour> getTours() {
        return tourRepository.findAll();
    }

    public List<Tour> getToursByParams(String cityId, String costMin, String costMax) throws ServiceException {
        if ((isEmptyString(costMin) && !isEmptyString(costMax)) || (!isEmptyString(costMin) && isEmptyString(costMax))) {
            throw new ServiceException("You must specify the minimum and maximum price");
        }
        boolean isCostValid = isValidDouble(costMin) && isValidDouble(costMax);
        if (!isEmptyString(cityId) && isValidLong(cityId)) {
            City city = cityRepository.findById(Long.parseLong(cityId)).orElseThrow(() -> new ServiceException("Cant find city by id: " + cityId));
            if (!isEmptyString(costMin)) {
                if (isCostValid) {
                    return tourRepository.findByCityAndCost(city, Double.parseDouble(costMin), Double.parseDouble(costMax));
                }
            } else {
                return tourRepository.findByCity(city);
            }
        } else {
            if (isCostValid) {
                return tourRepository.findByPrice(Double.parseDouble(costMin), Double.parseDouble(costMax));
            }
        }
        throw new ServiceException("You entered incorrect data");
    }

    public Tour getTourById(String id) throws ServiceException {
        return tourRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ServiceException("Cant find tour by id: " + id));
    }
}
