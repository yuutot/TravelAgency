package ua.travel.command.user;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.entity.User;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.AuthException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/29/17.
 */
public class AuthCommand implements PageCommand, ExecuteCommand {

    private UserService userService = UserService.getInstance();
    private final Logger LOGGER = Logger.getLogger(AuthCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            User user = userService.authUser(login, password);
            request.getSession().setAttribute("user", user);
            return "/";
        } catch (AuthException e) {
            LOGGER.warning(e.getMessage());
            request.getSession().setAttribute("error", e);
            return "/login";
        }
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameter("logout") != null){
            request.getSession().removeAttribute("user");
            response.sendRedirect("/");
            return;
        }

        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
