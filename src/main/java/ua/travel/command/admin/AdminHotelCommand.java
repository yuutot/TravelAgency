package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.FileUtils;
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
public class AdminHotelCommand implements ExecuteCommand, PageCommand {

    private HotelService hotelService = HotelService.getInstance();
    private CityService cityService = CityService.getInstance();
    private final Logger LOGGER = Logger.getLogger(AdminHotelCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String city = request.getParameter("city");
        String name = request.getParameter("name");
        String star = request.getParameter("star");
        if(!isEmptyString(city, name, star) && isValidLong(city) && isValidLong(star)){
            try {
                String path = FileUtils.loadFile(request,response);
                hotelService.createHotel(city, name, star, path);
            } catch (ServiceException | ServletException | IOException e) {
                LOGGER.warning(e.getMessage());
            }
        }
        return "/admin";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<City> cities = cityService.getAllCities();
        request.setAttribute("cities", cities);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/hotels.jsp").forward(request,response);
    }
}
