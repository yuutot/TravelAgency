package ua.travel.command.admin;

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
 * urlPattern /admin/createTour
 * command create_tour
 *
 * Display page with inputs for tour creations or create tour by params
 */
public class CreateTourCommand implements ExecuteCommand, PageCommand {

    private static final Logger LOGGER = Logger.getLogger(CreateTourCommand.class.getName());
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_TOUR_TYPE = "tour_type";
    private static final String PARAM_DATE_FROM = "date_from";
    private static final String PARAM_DATE_TO = "date_to";
    private static final String PARAM_COST = "cost";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_TRANSPORT_TYPE = "transport_type";
    private static final String PARAM_HOTEL = "hotel";
    private static final String PARAM_IS_HOT = "is_hot";
    private static final String ATTRIBUTE_ERROR = "error";
    private static final String ATTRIBUTE_HOTELS = "hotels";

    private HotelService hotelService = HotelService.getInstance();
    private TourService tourService = TourService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter(PARAM_TITLE);
        String tourType = request.getParameter(PARAM_TOUR_TYPE);
        String dateFrom = request.getParameter(PARAM_DATE_FROM);
        String dateTo = request.getParameter(PARAM_DATE_TO);
        String cost = request.getParameter(PARAM_COST);
        String description = request.getParameter(PARAM_DESCRIPTION);
        String transportType = request.getParameter(PARAM_TRANSPORT_TYPE);
        String hotel = request.getParameter(PARAM_HOTEL);
        String isHot = request.getParameter(PARAM_IS_HOT);
        if(isEmptyString(title,tourType, dateFrom, dateTo, cost, description, transportType, hotel)
                || !isValidDouble(cost) || !isValidLong(hotel)
                || !isValidString(title, tourType, dateFrom, dateTo, cost, description, transportType, hotel)){
           return "/admin/createTour";
        }
        Tour tour;
        try {
            String path = FileUtils.loadFile(request,response);
            tour = tourService.createTour(title, tourType, dateFrom, dateTo, cost, description, transportType, hotel, isHot, path);
        } catch (ServiceException | ServletException | IOException e) {
            request.getSession().setAttribute(ATTRIBUTE_ERROR, e);
            LOGGER.warning(e.getMessage());
            return "/error";
        }
        return "/tours?id=" + tour.getId();
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Hotel> hotels = hotelService.getAllHotel();
        request.setAttribute(ATTRIBUTE_HOTELS, hotels);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/createTour.jsp").forward(request,response);
    }
}
