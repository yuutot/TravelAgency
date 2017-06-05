package ua.travel.command;

import ua.travel.command.utils.PathUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
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
        if (command.contains("img/") || command.contains("css/") || command.contains("photo/") || command.contains("font/")) {
            try (ServletOutputStream out = response.getOutputStream();
                 FileInputStream fin = new FileInputStream("/home/yuuto/IdeaProjects/TravelAgency/src/main/webapp" + PathUtils.getContextPath(request));
                 BufferedInputStream bin = new BufferedInputStream(fin);
                 BufferedOutputStream bout = new BufferedOutputStream(out)) {

                int ch;
                while ((ch = bin.read()) != -1) {
                    bout.write(ch);
                }
            }
        } else {
            request.setAttribute("error", "Page not found: " + command);
            request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
        }
    }
}
