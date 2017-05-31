package ua.travel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuuto on 5/29/17.
 */
public interface ExecuteCommand {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
