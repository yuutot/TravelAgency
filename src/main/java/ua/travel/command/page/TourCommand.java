package ua.travel.command.page;

import ua.travel.command.PageCommand;
import ua.travel.entity.Tour;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * Created by yuuto on 5/30/17.
 */
public class TourCommand implements PageCommand {

    private TourService tourService = TourService.newInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Tour> tours;

        String city = request.getParameter("city");
        String costMin = request.getParameter("min_cost");
        String costMax = request.getParameter("max_cost");
        if(city == null && costMin == null && costMax == null) {
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
                request.setAttribute("error", e);
                request.getRequestDispatcher("WEB-INF/error.jsp").forward(request,response);
                return;
            }
        }
        request.setAttribute("tours", tours);
        request.getRequestDispatcher("WEB-INF/tours.jsp").forward(request,response);
    }
}
