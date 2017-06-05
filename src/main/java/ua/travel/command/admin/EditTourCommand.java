package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * urlPattern /admin/editTour
 *
 * Edit hot status or delete tour.
 * Get params delete for deleting and hot for change status.
 */
public class EditTourCommand implements PageCommand {

    private TourService tourService = TourService.getInstance();
    private final Logger LOGGER = Logger.getLogger(EditTourCommand.class.getName());

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if(ValidatorUtils.isEmptyString(id, type) || !ValidatorUtils.isValidLong(id)){
            response.sendRedirect("/admin");
            return;
        }
        if(type.equals("delete")){
            tourService.deleteTour(id);
        } else if(type.equals("hot")){
            try {
                tourService.changeTourStatus(id);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
                request.setAttribute("error", e);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request,response);
                return;
            }
        }
        response.sendRedirect("/admin");
    }
}
