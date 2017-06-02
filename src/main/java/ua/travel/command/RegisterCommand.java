package ua.travel.command;

import ua.travel.entity.User;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/29/17.
 */
public class RegisterCommand implements PageCommand, ExecuteCommand {

    private UserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phone = request.getParameter("phone");

        try {
            User user = userService.registerUser(login, password, "user", name, surname, phone);
            request.getSession().setAttribute("user", user);
            return "/";
        } catch (ServiceException e) {
            request.getSession().setAttribute("error", e);
            return "/register";
        }
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
    }

}
