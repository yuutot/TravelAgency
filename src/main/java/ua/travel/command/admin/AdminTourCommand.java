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
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/30/17.
 */
public class AdminTourCommand implements PageCommand {

    private TourService tourService = TourService.getInstance();
    private final Logger LOGGER = Logger.getLogger(AdminTourCommand.class.getName());

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter("id");
        if(id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)){
            try {
                Tour tour = tourService.getTourById(id);
                request.setAttribute("tour", tour);
                request.getRequestDispatcher("/WEB-INF/jsp/admin/tour.jsp").forward(request, response);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
                response.sendRedirect("/admin");
            }
        }
    }
}