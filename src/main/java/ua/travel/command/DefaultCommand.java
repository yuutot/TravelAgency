package ua.travel.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/30/17.
 */
public class DefaultCommand implements ExecuteCommand, PageCommand {

    private String command;

    public DefaultCommand(String command) {
        this.command = command;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/error";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("error", "Page not found: " + command);
        request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
    }
}
