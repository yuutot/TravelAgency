package ua.travel.controller;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.CommandHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/30/17.
 */
@WebServlet(name = "pageController", urlPatterns = "/")
public class PageController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageCommand command = CommandHelper.getInstance()
                .getPageCommand(request);

        command.get(request, response);
    }
}
