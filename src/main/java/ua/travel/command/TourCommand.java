package ua.travel.command;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.dao.repositories.impl.CityRepository;
import ua.travel.entity.City;
import ua.travel.entity.Tour;
import ua.travel.service.CityService;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * Created by yuuto on 5/30/17.
 */
public class TourCommand implements PageCommand, ExecuteCommand {

    private TourService tourService = TourService.newInstance();
    private CityService cityService = CityService.newInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Object toursObject = session.getAttribute("tours");
        Object citiesObject = session.getAttribute("cities");
        List<Tour> tours;
        if(toursObject == null) {
            tours = tourService.getTours();
        } else {
            tours = (List<Tour>) toursObject;
            session.removeAttribute("tours");
        }
        List<City> cities;
        if(citiesObject == null) {
            cities = cityService.getAllCities();
        } else {
            cities = (List<City>) citiesObject;
            session.removeAttribute("cities");
        }
        request.setAttribute("tours", tours);
        request.setAttribute("cities", cities);
        request.getRequestDispatcher("WEB-INF/tours.jsp").forward(request,response);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Tour> tours;
        String city = request.getParameter("city") == null ? "" : request.getParameter("city");
        String costMin = request.getParameter("min_cost");
        String costMax = request.getParameter("max_cost");
        if(city.isEmpty() && costMin.isEmpty() && costMax.isEmpty()) {
            tours = tourService.getTours();
        } else {
            try {
                if(isValidLong(city) && isValidDouble(costMin) && isValidDouble(costMax)){
                    tours = tourService.getToursByParams(city, costMin, costMax);
                } else {
                    tours = tourService.getTours();
                    request.setAttribute("error", "You entered incorrect data");
                }

            } catch (ServiceException e) {
                request.getSession().setAttribute("error", e);
                return "/error";
            }
        }
        List<City> cities = cityService.getAllCities();
        request.getSession().setAttribute("cities", cities);
        request.getSession().setAttribute("tours", tours);
        return "/tours";
    }
}
