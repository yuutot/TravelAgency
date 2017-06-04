package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.entity.City;
import ua.travel.service.CityService;
import ua.travel.service.HotelService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * Created by yuuto on 6/2/17.
 */
public class CreateHotelCommand implements ExecuteCommand, PageCommand {

    private HotelService hotelService = HotelService.getInstance();
    private CityService cityService = CityService.getInstance();
    private final Logger LOGGER = Logger.getLogger(CreateHotelCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String city = request.getParameter("city");
        String name = request.getParameter("name");
        String star = request.getParameter("star");
        String photo = request.getParameter("photo");
        if(!isEmptyString(city, name, star, photo) && isValidLong(city) && isValidLong(star)){
            try {
                hotelService.createHotel(city, name, star, photo);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
            }
        }
        return "/admin";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<City> cities = cityService.getAllCities();
        request.setAttribute("cities", cities);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/createHotel.jsp").forward(request,response);
    }
}
