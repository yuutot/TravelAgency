package ua.travel.command.user;

import ua.travel.command.PageCommand;
import ua.travel.entity.City;
import ua.travel.entity.Tour;
import ua.travel.service.CityService;
import ua.travel.service.TourService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuuto on 5/29/17.
 */
public class HomeCommand implements PageCommand {

    private CityService cityService = CityService.getInstance();
    private TourService tourService = TourService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Tour> tours = tourService.getHotTours();
        List<City> cities = cityService.getAllCities();
        request.setAttribute("cities", cities);
        request.setAttribute("tours", tours);
        request.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(request, response);
    }
}
