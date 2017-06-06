package ua.travel.command.user;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.User;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * urlPattern /register
 * command register
 *
 * Display page with inputs for register user.
 * Execute data for register
 */
public class RegisterCommand implements PageCommand, ExecuteCommand {

    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class.getName());
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_PHONE = "phone";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ERROR = "error";

    private UserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        String surname = request.getParameter(PARAM_SURNAME);
        String phone = request.getParameter(PARAM_PHONE);
        if(ValidatorUtils.isEmptyString(login,password,name,surname,phone)
                || !ValidatorUtils.isValidString(login, password, name, surname, phone)){
            request.getSession().setAttribute(ATTRIBUTE_ERROR, new ServiceException("Не все поля заполнены"));
            return "/register";
        }
        try {
            User user = userService.registerUser(login, password, name, surname, phone);
            request.getSession().setAttribute(ATTRIBUTE_USER, user);
            return "/";
        } catch (ServiceException e) {
            LOGGER.warning(e.getMessage());
            request.getSession().setAttribute(ATTRIBUTE_ERROR, e);
            return "/register";
        }
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
    }

}
