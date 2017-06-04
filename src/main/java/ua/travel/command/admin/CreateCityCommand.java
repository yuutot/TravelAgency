package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.service.CityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuuto on 6/2/17.
 */
public class CreateCityCommand implements ExecuteCommand, PageCommand {

    private CityService cityService = CityService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        if(!ValidatorUtils.isEmptyString(name)) {
            cityService.createCity(name);
        }
        return "/admin";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/createCity.jsp").forward(request,response);
    }
}
