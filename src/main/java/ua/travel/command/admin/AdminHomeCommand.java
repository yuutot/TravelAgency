package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.entity.City;
import ua.travel.entity.Hotel;
import ua.travel.entity.Order;
import ua.travel.entity.enums.OrderStatus;
import ua.travel.service.CityService;
import ua.travel.service.HotelService;
import ua.travel.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuuto on 6/2/17.
 */
public class AdminHomeCommand implements PageCommand {

    private OrderService orderService = OrderService.getInstance();
    private CityService cityService = CityService.getInstance();
    private HotelService hotelService = HotelService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String all = request.getParameter("all");
        if (all != null && !all.isEmpty()) {
            List<Order> allOrders = orderService.getOrders();
            request.setAttribute("allOrders", allOrders);
        } else {
            List<Order> newOrders = orderService.getOrdersByStatus(OrderStatus.NEW);
            request.setAttribute("newOrders", newOrders);
        }
        List<City> cities = cityService.getAllCities();
        List<Hotel> hotels = hotelService.getAllHotel();
        request.setAttribute("cities", cities);
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(request,response);
    }
}
