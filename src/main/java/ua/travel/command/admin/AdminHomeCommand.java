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
 * urlPattern /admin
 *
 * Get home admins page.
 * Includes all/new orders depending on the parameters
 */
public class AdminHomeCommand implements PageCommand {

    private static final String PARAM_ALL = "all";
    private static final String ATTRIBUTE_ALL_ORDERS = "allOrders";
    private static final String ATTRIBUTE_NEW_ORDERS = "newOrders";

    private OrderService orderService = OrderService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String all = request.getParameter(PARAM_ALL);
        if (all != null && !all.isEmpty()) {
            List<Order> allOrders = orderService.getOrders();
            request.setAttribute(ATTRIBUTE_ALL_ORDERS, allOrders);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/orders.jsp").forward(request,response);
        } else {
            List<Order> newOrders = orderService.getOrdersByStatus(OrderStatus.NEW);
            request.setAttribute(ATTRIBUTE_NEW_ORDERS, newOrders);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(request,response);
        }

    }
}
