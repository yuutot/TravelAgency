package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.User;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 6/2/17.
 */
public class UserCommand implements PageCommand {

    private UserService userService = UserService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)) {
            try {
                User user = userService.getUserById(id);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/jsp/admin/user.jsp").forward(request,response);
            } catch (ServiceException e) {
                request.setAttribute("error", e);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request,response);
            }
            return;
        }
        response.sendRedirect("/admin");
    }
}
