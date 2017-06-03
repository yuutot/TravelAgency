package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.service.TourService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 6/2/17.
 */
public class EditTourCommand implements PageCommand {

    private TourService tourService = TourService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if(ValidatorUtils.isEmptyString(id, type) || !ValidatorUtils.isValidLong(id)){
            response.sendRedirect("/tours");
            return;
        }
        if(type.equals("delete")){
            tourService.deleteTour(id);
        } else if(type.equals("hot")){
            try {
                tourService.changeTourStatus(id);
            } catch (ServiceException e) {
                request.setAttribute("error", e);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request,response);
                return;
            }
        }
        response.sendRedirect("/tours");
    }
}
