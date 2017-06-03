package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.service.HotelService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.travel.command.utils.ValidatorUtils.*;

/**
 * Created by yuuto on 6/2/17.
 */
public class CreateHotelCommand implements ExecuteCommand {

    private HotelService hotelService = HotelService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String city = request.getParameter("city");
        String name = request.getParameter("name");
        String star = request.getParameter("star");
        String photo = request.getParameter("photo");
        if(!isEmptyString(city, name, star, photo) && isValidLong(city) && isValidLong(star)){
            try {
                hotelService.createHotel(city, name, star, photo);
            } catch (ServiceException e) {
                e.printStackTrace();
                //todo add logger
            }
        }
        return "/admin";
    }
}
