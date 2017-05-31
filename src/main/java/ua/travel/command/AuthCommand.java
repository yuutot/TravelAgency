package ua.travel.command;

import ua.travel.entity.User;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.AuthException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/29/17.
 */
public class AuthCommand implements PageCommand, ExecuteCommand {

    private UserService userService = UserService.newInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            User user = userService.authUser(login, password);
            request.getSession().setAttribute("user", user);
            return "/";
        } catch (AuthException e) {
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

        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }
}
