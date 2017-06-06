package ua.travel.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Display error page
 */
public class ErrorCommand implements PageCommand {
    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
    }
}