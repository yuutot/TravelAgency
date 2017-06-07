package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
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
    private static final String PARAM_PAGE = "page";
    private static final String ATTRIBUTE_ALL_ORDERS = "allOrders";
    private static final String ATTRIBUTE_NEW_ORDERS = "newOrders";
    private static final String ATTRIBUTE_COUNT_PAGE = "countPage";

    private OrderService orderService = OrderService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String all = request.getParameter(PARAM_ALL);
        String pageParam = request.getParameter(PARAM_PAGE);
        if (all != null && !all.isEmpty()) {
            Long page = pageParam != null && ValidatorUtils.isValidLong(pageParam) ? Long.parseLong(pageParam) : 1L;
            List<Order> allOrders = orderService.getOrdersByPage(page);
            Long count = orderService.getPageForOrders();
            request.setAttribute(ATTRIBUTE_ALL_ORDERS, allOrders);
            request.setAttribute(ATTRIBUTE_COUNT_PAGE, count);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/orders.jsp").forward(request,response);
        } else {
            List<Order> newOrders = orderService.getOrdersByStatus(OrderStatus.NEW);
            request.setAttribute(ATTRIBUTE_NEW_ORDERS, newOrders);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(request,response);
        }

    }
}
