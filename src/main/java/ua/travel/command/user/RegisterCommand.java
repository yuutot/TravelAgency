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
 * Created by yuuto on 5/29/17.
 */
public class RegisterCommand implements PageCommand, ExecuteCommand {

    private UserService userService = UserService.getInstance();
    private final Logger LOGGER = Logger.getLogger(RegisterCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phone = request.getParameter("phone");
        if(ValidatorUtils.isEmptyString(login,password,name,surname,phone)){
            request.getSession().setAttribute("error", new ServiceException("Не все поля заполнены"));
            return "/register";
        }
        try {
            User user = userService.registerUser(login, password, name, surname, phone);
            request.getSession().setAttribute("user", user);
            return "/";
        } catch (ServiceException e) {
            LOGGER.warning(e.getMessage());
            request.getSession().setAttribute("error", e);
            return "/register";
        }
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
    }

}
