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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * Created by yuuto on 5/26/17.
 */
public class TourService {

    private final Logger LOGGER = Logger.getLogger(TourService.class.getName());

    private static TourService tourService;
    private HotelRepository hotelRepository = HotelRepository.getInstance();
    private TourRepository tourRepository = TourRepository.getInstance();
    private CityRepository cityRepository = CityRepository.getInstance();

    private TourService(){}

    public static TourService getInstance() {
        TourService localInstance = tourService;
        if (localInstance == null) {
            synchronized (TourService.class) {
                localInstance = tourService;
                if (localInstance == null) {
                    tourService = localInstance = new TourService();
                }
            }
        }
        return localInstance;
    }

    public Tour createTour(String title, String tourType, String dateFrom, String dateTo, String cost, String description, String transportType, String hotelId, String isHot, String photoUrl) throws ServiceException {
        Hotel hotel = hotelRepository.findById(Long.parseLong(hotelId)).orElseThrow(() -> new ServiceException("Cant find hotel by id: " + hotelId));
        Tour tour = new Tour();
        tour.setTitle(title);
        tour.setTourType(TourType.valueOf(tourType));
        tour.setDateTo(localTimeToDate(dateTo));
        tour.setDateFrom(localTimeToDate(dateFrom));
        tour.setCost(Double.parseDouble(cost));
        tour.setDescription(description);
        tour.setTransportType(TransportType.valueOf(transportType));
        tour.setHotel(hotel);
        tour.setHot(Boolean.valueOf(isHot));
        tour.setPhoto(photoUrl);
        tour.setId(tourRepository.save(tour));
        LOGGER.info(tour.toString());
        return tour;
    }

    public List<Tour> getTours() {
        return tourRepository.findAll();
    }

    public List<Tour> getToursByParams(String cityId, String costMin, String costMax, String type) throws ServiceException {
        if ((isEmptyString(costMin) && !isEmptyString(costMax)) || (!isEmptyString(costMin) && isEmptyString(costMax))) {
            throw new ServiceException("You must specify the minimum and maximum price");
        }
        boolean isCostValid = isValidDouble(costMin) && isValidDouble(costMax);
        TourType tourType;
        try{
            tourType = TourType.valueOf(type);
        } catch (IllegalArgumentException ex){
            throw new ServiceException("Invalid tour type value: " + type);
        }
        if (!isEmptyString(cityId) && isValidLong(cityId)) {
            City city = cityRepository.findById(Long.parseLong(cityId)).orElseThrow(() -> new ServiceException("Cant find city by id: " + cityId));
            if (!isEmptyString(costMin)) {
                if (isCostValid) {
                    LOGGER.info("Find by city and cost");
                    return tourRepository.findByCityAndCost(city, Double.parseDouble(costMin), Double.parseDouble(costMax), tourType);
                }
            } else {
                LOGGER.info("Find by city");
                return tourRepository.findByCity(city, tourType);
            }
        } else {
            if (isCostValid) {
                LOGGER.info("Find by cost");
                return tourRepository.findByCost(Double.parseDouble(costMin), Double.parseDouble(costMax), tourType);
            }
        }
        LOGGER.info("Find by type");
        return tourRepository.findByType(tourType);
    }

    public Tour getTourById(String id) throws ServiceException {
        return tourRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ServiceException("Cant find tour by id: " + id));
    }

    public void deleteTour(String id){
        tourRepository.delete(Long.parseLong(id));
    }

    public void changeTourStatus(String id) throws ServiceException {
        Tour tour = tourRepository.findById(Long.parseLong(id)).orElseThrow(()->new ServiceException("Cant find tour by id: " + id));
        tour.setHot(!tour.getHot());
        tourRepository.update(tour);
    }

    private Date localTimeToDate(String time){
        return new Date(Timestamp.valueOf(time.replace("T", " ").concat(":00")).getTime());
    }
}
