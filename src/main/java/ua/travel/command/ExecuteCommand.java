package ua.travel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExecuteCommand {

    /**
     * Executes the command sent by the post-request
     *
     * @param request
     * @param response
     * @return page for redirect
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
