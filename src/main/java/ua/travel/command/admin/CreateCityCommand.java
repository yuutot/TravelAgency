package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.service.CityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuuto on 6/2/17.
 */
public class CreateCityCommand implements ExecuteCommand {

    private CityService cityService = CityService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        if(!ValidatorUtils.isEmptyString(name)) {
            cityService.createCity(name);
        }
        return "/admin";
    }
}
