package ua.travel.command.page;

import ua.travel.command.PageCommand;
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

import static ua.travel.command.utils.ValidatorUtils.isEmptyString;

/**
 * Created by yuuto on 5/30/17.
 */
public class TourCommand implements PageCommand {

    private TourService tourService = TourService.newInstance();
    private CityService cityService = CityService.newInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Tour> tours;
        String city = request.getParameter("city");
        String costMin = request.getParameter("min_cost");
        String costMax = request.getParameter("max_cost");
        if (isEmptyString(city) && isEmptyString(costMin) && isEmptyString(costMax)) {
            tours = tourService.getTours();
        } else {
            try {
                tours = tourService.getToursByParams(city, costMin, costMax);
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                tours = tourService.getTours();
            }
        }
        List<City> cities = cityService.getAllCities();
        request.setAttribute("cities", cities);
        request.setAttribute("tours", tours);
        request.getRequestDispatcher("WEB-INF/tours.jsp").forward(request, response);
    }
}
