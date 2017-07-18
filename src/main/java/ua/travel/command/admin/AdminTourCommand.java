package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.Tour;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * urlPattern /admin/tour, /admin/tour?id=<id>
 * <p>
 * Get all or one tour by id
 */
public class AdminTourCommand implements PageCommand {

    private static final Logger LOGGER = Logger.getLogger(AdminTourCommand.class.getName());
    private static final String PARAM_ID = "id";
    private static final String ATTRIBUTE_TOUR = "tour";
    private static final String ATTRIBUTE_TOURS = "tours";
    private TourService tourService = TourService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter(PARAM_ID);
        if (id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)) {
            try {
                Tour tour = tourService.getTourById(id);
                request.setAttribute(ATTRIBUTE_TOUR, tour);
                request.getRequestDispatcher("/WEB-INF/jsp/admin/tour.jsp").forward(request, response);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
                response.sendRedirect("/admin");
            }
        } else {
            List<Tour> tours = tourService.getTours();
            request.setAttribute(ATTRIBUTE_TOURS, tours);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/tours.jsp").forward(request, response);
        }
    }
}