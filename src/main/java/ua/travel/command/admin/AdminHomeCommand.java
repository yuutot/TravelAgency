package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.entity.Order;
import ua.travel.entity.enums.OrderStatus;
import ua.travel.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuuto on 6/2/17.
 */
public class AdminHomeCommand implements PageCommand {

    private OrderService orderService = OrderService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String all = request.getParameter("all");
        if (all != null && !all.isEmpty()) {
            List<Order> allOrders = orderService.getOrders();
            request.setAttribute("allOrders", allOrders);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/orders.jsp").forward(request,response);
        } else {
            List<Order> newOrders = orderService.getOrdersByStatus(OrderStatus.NEW);
            request.setAttribute("newOrders", newOrders);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(request,response);
        }

    }
}
