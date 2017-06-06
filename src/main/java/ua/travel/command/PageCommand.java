package ua.travel.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PageCommand {

    /**
     * Display page based on url
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}