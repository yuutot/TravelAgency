package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.City;
import ua.travel.service.CityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * urlPattern /admin/city
 * command create_city
 *
 * Get or create city by params
 */
public class AdminCityCommand implements ExecuteCommand, PageCommand {

    private static final String PARAM_NAME = "name";
    private static final String ATTRIBUTE_CITY = "cities";

    private CityService cityService = CityService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(PARAM_NAME);
        if(!ValidatorUtils.isEmptyString(name)) {
            cityService.createCity(name);
        }
        return "/admin/city";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<City> cities = cityService.getAllCities();
        request.setAttribute(ATTRIBUTE_CITY, cities);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/city.jsp").forward(request,response);
    }
}
