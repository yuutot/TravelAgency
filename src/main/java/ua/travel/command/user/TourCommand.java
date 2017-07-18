package ua.travel.command.user;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.City;
import ua.travel.entity.Tour;
import ua.travel.service.CityService;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static ua.travel.command.utils.ValidatorUtils.isEmptyString;
import static ua.travel.command.utils.ValidatorUtils.isHaveValidString;
import static ua.travel.command.utils.ValidatorUtils.isValidString;

/**
 * urlPattern /tours
 * <p>
 * Display page with all tours or tours by parameters
 */
public class TourCommand implements PageCommand {

    private static final Logger LOGGER = Logger.getLogger(TourCommand.class.getName());
    private static final String PARAM_ID = "id";
    private static final String PARAM_CITY = "city";
    private static final String PARAM_MIN_COST = "min_cost";
    private static final String PARAM_MAX_COST = "max_cost";
    private static final String PARAM_TYPE = "type";
    private static final String ATTRIBUTE_TOUR = "tour";
    private static final String ATTRIBUTE_TOURS = "tours";
    private static final String ATTRIBUTE_ERROR = "error";
    private static final String ATTRIBUTE_CITIES = "cities";

    private TourService tourService = TourService.getInstance();
    private CityService cityService = CityService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter(PARAM_ID);
        if (id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)) {
            idPresent(id, request, response);
        } else {
            filterTour(request, response);
        }
    }

    private void filterTour(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tour> tours;
        String city = request.getParameter(PARAM_CITY);
        String costMin = request.getParameter(PARAM_MIN_COST);
        String costMax = request.getParameter(PARAM_MAX_COST);
        String type = request.getParameter(PARAM_TYPE);
        if (!isHaveValidString(city, costMin, costMax, type) || !isValidString(city, costMin, costMax, type)) {
            tours = tourService.getTours();
        } else {
            try {
                tours = tourService.getToursByParams(city, costMin, costMax, type);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
                request.setAttribute(ATTRIBUTE_ERROR, e.getMessage());
                tours = tourService.getTours();
            }
        }
        List<City> cities = cityService.getAllCities();
        request.setAttribute(ATTRIBUTE_CITIES, cities);
        request.setAttribute(ATTRIBUTE_TOURS, tours);
        request.getRequestDispatcher("WEB-INF/jsp/tours.jsp").forward(request, response);
    }

    private void idPresent(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tour> tours;
        try {
            Tour tour = tourService.getTourById(id);
            request.setAttribute(ATTRIBUTE_TOUR, tour);
            request.getRequestDispatcher("WEB-INF/jsp/tour.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOGGER.warning(e.getMessage());
            tours = tourService.getTours();
            List<City> cities = cityService.getAllCities();
            request.setAttribute(ATTRIBUTE_CITIES, cities);
            request.setAttribute(ATTRIBUTE_ERROR, e.getMessage());
            request.setAttribute(ATTRIBUTE_TOURS, tours);
            request.getRequestDispatcher("WEB-INF/jsp/tours.jsp").forward(request, response);
        }
    }
}