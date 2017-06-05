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
 * Created by yuuto on 6/2/17.
 */
public class AdminCityCommand implements ExecuteCommand, PageCommand {

    private CityService cityService = CityService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        if(!ValidatorUtils.isEmptyString(name)) {
            cityService.createCity(name);
        }
        return "/admin/city";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<City> cities = cityService.getAllCities();
        request.setAttribute("cities", cities);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/city.jsp").forward(request,response);
    }
}
