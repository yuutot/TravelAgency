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

import static ua.travel.command.utils.ValidatorUtils.isEmptyString;
import static ua.travel.command.utils.ValidatorUtils.isHaveValidString;

/**
 * Created by yuuto on 5/30/17.
 */
public class TourCommand implements PageCommand {

    private TourService tourService = TourService.getInstance();
    private CityService cityService = CityService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter("id");
        if(id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)){
            idPresent(id, request, response);
        }else {
            filterTour(request,response);
        }
    }

    private void filterTour(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tour> tours;
        String city = request.getParameter("city");
        String costMin = request.getParameter("min_cost");
        String costMax = request.getParameter("max_cost");
        String type = request.getParameter("type");
        if (!isHaveValidString(city, costMin, costMax, type)) {
            tours = tourService.getTours();
        } else {
            try {
                tours = tourService.getToursByParams(city, costMin, costMax, type);
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                tours = tourService.getTours();
            }
        }
        List<City> cities = cityService.getAllCities();
        request.setAttribute("cities", cities);
        request.setAttribute("tours", tours);
        request.getRequestDispatcher("WEB-INF/jsp/tours.jsp").forward(request, response);
    }

    private void idPresent(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tour> tours;
        try {
            Tour tour = tourService.getTourById(id);
            request.setAttribute("tour", tour);
            request.getRequestDispatcher("WEB-INF/jsp/tour.jsp").forward(request, response);
        } catch (ServiceException e) {
            tours = tourService.getTours();
            List<City> cities = cityService.getAllCities();
            request.setAttribute("cities", cities);
            request.setAttribute("error", e.getMessage());
            request.setAttribute("tours", tours);
            request.getRequestDispatcher("WEB-INF/jsp/tours.jsp").forward(request, response);
        }
    }
}