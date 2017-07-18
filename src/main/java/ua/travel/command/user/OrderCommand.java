package ua.travel.command.user;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.Order;
import ua.travel.entity.User;
import ua.travel.service.OrderService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * urlPattern /order
 * <p>
 * Display page with order/orders depending on the parameters.
 */

public class OrderCommand implements PageCommand {

    private static final Logger LOGGER = Logger.getLogger(OrderCommand.class.getName());
    private static final String PARAM_ID = "id";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_SUCCESS = "success";
    private static final String ATTRIBUTE_ERROR = "error";
    private static final String ATTRIBUTE_ORDERS = "orders";

    private OrderService orderService = OrderService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter(PARAM_ID);
        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        try {
            if (id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)) {
                Long orderId = orderService.bookTour(Long.parseLong(id), user);
                request.setAttribute(ATTRIBUTE_SUCCESS, orderId);
                request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
            } else {
                List<Order> orders = orderService.getOrdersByUser(user.getId());
                request.setAttribute(ATTRIBUTE_ORDERS, orders);
                request.getRequestDispatcher("WEB-INF/jsp/orders.jsp").forward(request, response);
            }
        } catch (ServiceException e) {
            LOGGER.warning(e.getMessage());
            request.setAttribute(ATTRIBUTE_ERROR, e);
            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
