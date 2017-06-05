package ua.travel.command.admin;

import sun.rmi.runtime.Log;
import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.FileUtils;
import ua.travel.entity.Hotel;
import ua.travel.entity.Tour;
import ua.travel.service.HotelService;
import ua.travel.service.TourService;
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
public class CreateTourCommand implements ExecuteCommand, PageCommand {

    private HotelService hotelService = HotelService.getInstance();
    private TourService tourService = TourService.getInstance();
    private final Logger LOGGER = Logger.getLogger(CreateTourCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String tourType = request.getParameter("tour_type");
        String dateFrom = request.getParameter("date_from");
        String dateTo = request.getParameter("date_to");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String transportType = request.getParameter("transport_type");
        String hotel = request.getParameter("hotel");
        String isHot = request.getParameter("is_hot");
        if(isEmptyString(tourType, dateFrom, dateTo, cost, description, transportType, hotel) || !isValidDouble(cost) || !isValidLong(hotel)){
           return "/admin/createTour";
        }
        Tour tour;
        try {
            String path = FileUtils.loadFile(request,response);
            tour = tourService.createTour(tourType, dateFrom, dateTo, cost, description, transportType, hotel, isHot, path);
        } catch (ServiceException | ServletException | IOException e) {
            request.getSession().setAttribute("error", e);
            LOGGER.warning(e.getMessage());
            return "/error";
        }
        return "/tours?id=" + tour.getId();
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Hotel> hotels = hotelService.getAllHotel();
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/createTour.jsp").forward(request,response);
    }
}
