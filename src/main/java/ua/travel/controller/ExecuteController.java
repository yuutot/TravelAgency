package ua.travel.controller;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.utils.CommandHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/26/17.
 */
@WebServlet(name = "executeController", urlPatterns = "/execute")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class ExecuteController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExecuteCommand command = CommandHelper.getInstance()
                .getExecuteCommand(request);

        String page = command.execute(request, response);
        response.sendRedirect(page);
    }
}
