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
 * Default command.
 * If its a picture or style write on jsp else display page 404.
 */
public class DefaultCommand implements ExecuteCommand, PageCommand {

    private static final String IMG = "img/";
    private static final String CSS = "css/";
    private static final String STYLE = "style/";
    private static final String FONT = "font/";
    private static final String PHOTO = "photo/";
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
        if (command.contains(IMG) || command.contains(CSS) || command.contains(PHOTO) || command.contains(STYLE) || command.contains(FONT)) {
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
