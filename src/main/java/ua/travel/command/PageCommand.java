package ua.travel.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 5/29/17.
 */
public interface PageCommand {
    void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
