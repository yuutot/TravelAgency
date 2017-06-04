package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.PageCommand;
import ua.travel.entity.Hotel;
import ua.travel.service.HotelService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuuto on 6/2/17.
 */
public class CreateTourCommand implements ExecuteCommand, PageCommand {

    private HotelService hotelService = HotelService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//todo ~
        return "/admin";
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Hotel> hotels = hotelService.getAllHotel();
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/createTour.jsp").forward(request,response);
    }
}
