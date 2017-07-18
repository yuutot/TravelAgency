package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.Order;
import ua.travel.entity.User;
import ua.travel.service.OrderService;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * urlPattern /admin/user?id=<id>
 * <p>
 * Display page with user info and user orders by id
 */
public class UserCommand implements PageCommand {

    private static final Logger LOGGER = Logger.getLogger(UserCommand.class.getName());
    private static final String PARAM_ID = "id";
    private static final String PARAM_ORDERS = "orders";
    private static final String PARAM_USER_PROFILE = "userProfile";
    private static final String ATTRIBUTE_ERROR = "error";

    private UserService userService = UserService.getInstance();
    private OrderService orderService = OrderService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter(PARAM_ID);
        if (id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)) {
            try {
                User user = userService.getUserById(id);
                List<Order> orders = orderService.getOrdersByUser(user.getId());
                request.setAttribute(PARAM_ORDERS, orders);
                request.setAttribute(PARAM_USER_PROFILE, user);
                request.getRequestDispatcher("/WEB-INF/jsp/admin/user.jsp").forward(request, response);
            } catch (ServiceException e) {
                LOGGER.warning(e.getMessage());
                request.setAttribute(ATTRIBUTE_ERROR, e);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
            return;
        }
        response.sendRedirect("/admin");
    }
}
