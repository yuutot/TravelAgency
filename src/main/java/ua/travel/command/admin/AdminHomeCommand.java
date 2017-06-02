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
import java.util.stream.Collectors;

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
            List<Order> newOrders = allOrders.stream()
                    .filter(f -> f.getOrderStatus().equals(OrderStatus.NEW))
                    .collect(Collectors.toList());

            request.setAttribute("allOrders", allOrders);
            request.setAttribute("newOrders", newOrders);
        } else {
            List<Order> newOrders = orderService.getOrdersByStatus(OrderStatus.NEW);
            request.setAttribute("newOrders", newOrders);
        }
        request.getRequestDispatcher("/WEB-INF/admin/home.jsp").forward(request,response);
    }
}
