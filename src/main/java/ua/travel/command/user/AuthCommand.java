package ua.travel.command.user;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.User;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.AuthException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * urlPattern /login
 * command login
 * <p>
 * Display page with inputs for auth user.
 * Execute data for auth
 */
public class AuthCommand implements PageCommand, ExecuteCommand {

    private static final Logger LOGGER = Logger.getLogger(AuthCommand.class.getName());
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_LOGOUT = "logout";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ERROR = "error";

    private UserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (!ValidatorUtils.isValidString(login, password)) {
            return "/login";
        }
        try {
            User user = userService.authUser(login, password);
            request.getSession().setAttribute(ATTRIBUTE_USER, user);
            return "/";
        } catch (AuthException e) {
            LOGGER.warning(e.getMessage());
            request.getSession().setAttribute(ATTRIBUTE_ERROR, e);
            return "/login";
        }
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter(PARAM_LOGOUT) != null) {
            request.getSession().removeAttribute(ATTRIBUTE_USER);
            response.sendRedirect("/");
            return;
        }

        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
