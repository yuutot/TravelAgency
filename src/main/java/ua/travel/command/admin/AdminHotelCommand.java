package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.FileUtils;
import ua.travel.entity.City;
import ua.travel.entity.Hotel;
import ua.travel.service.CityService;
import ua.travel.service.HotelService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * urlPattern /admin/hotels
 * command create_hotels
 *
 * Get or create hotels by params
 */
public class AdminHotelCommand implements ExecuteCommand, PageCommand {

    private static final Logger LOGGER = Logger.getLogger(AdminHotelCommand.class.getName());
    private static final String PARAM_CITY = "city";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_STAR = "star";
    private static final String ATTRIBUTE_CITIES = "cities";
    private static final String ATTRIBUTE_HOTELS = "hotels";

    private HotelService hotelService = HotelService.getInstance();
    private CityService cityService = CityService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String city = request.getParameter(PARAM_CITY);
        String name = request.getParameter(PARAM_NAME);
        String star = request.getParameter(PARAM_STAR);
        if(!isEmptyString(city, name, star) && isValidLong(city) && isValidLong(star)){
            try {
                String path = FileUtils.loadFile(request,response);
                hotelService.createHotel(city, name, star, path);
            } catch (ServiceException | ServletException | IOException e) {
                LOGGER.warning(e.getMessage());
            }
        }
        return "/admin/hotels";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Hotel> hotels = hotelService.getAllHotel();
        List<City> cities = cityService.getAllCities();
        request.setAttribute(ATTRIBUTE_CITIES, cities);
        request.setAttribute(ATTRIBUTE_HOTELS, hotels);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/hotels.jsp").forward(request,response);
    }
}
