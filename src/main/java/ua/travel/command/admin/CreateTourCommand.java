package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuuto on 6/2/17.
 */
public class CreateTourCommand implements ExecuteCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/admin";
    }
}
