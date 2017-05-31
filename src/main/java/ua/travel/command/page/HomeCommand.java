package ua.travel.command.page;

import ua.travel.command.PageCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/29/17.
 */
public class HomeCommand implements PageCommand {

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/home.jsp").forward(request, response);
    }
}
