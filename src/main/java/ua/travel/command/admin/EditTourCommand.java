package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.print.DocFlavor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * urlPattern /admin/editTour
 * <p>
 * Edit hot status or delete tour.
 * Get params delete for deleting and hot for change status.
 */
public class EditTourCommand implements PageCommand {

    private static final Logger LOGGER = Logger.getLogger(EditTourCommand.class.getName());
    private static final String PARAM_ID = "id";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_DELETE = "delete";
    private static final String PARAM_HOT = "hot";
    private static final String ATTRIBUTE_ERROR = "error";

    private TourService tourService = TourService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter(PARAM_ID);
        String type = request.getParameter(PARAM_TYPE);

        if (ValidatorUtils.isEmptyString(id, type) || !ValidatorUtils.isValidLong(id) || !ValidatorUtils.isValidString(type)) {
            response.sendRedirect("/admin");
            return;
        }
        if (type.equals(PARAM_DELETE)) {
            tourService.deleteTour(id);
        } else if (type.equals(PARAM_HOT)) {
            try {
                tourService.changeTourStatus(id);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
                request.setAttribute(ATTRIBUTE_ERROR, e);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("/admin");
    }
}
