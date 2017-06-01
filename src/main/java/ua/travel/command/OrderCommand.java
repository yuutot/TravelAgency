package ua.travel.command;

import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.Order;
import ua.travel.entity.Tour;
import ua.travel.entity.User;
import ua.travel.service.OrderService;
import ua.travel.service.TourService;
import ua.travel.service.UserService;
import ua.travel.service.exceptions.AuthException;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuuto on 5/29/17.
 */
public class OrderCommand implements PageCommand {

    private OrderService orderService = OrderService.newInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("user");
        try {
            if (id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)) {
                Long orderId = orderService.bookTour(Long.parseLong(id), user.getId());
                request.setAttribute("success", orderId);
                request.getRequestDispatcher("WEB-INF/success.jsp").forward(request, response);
            } else {
                List<Order> orders = orderService.getOrdersByUser(user.getId());
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("WEB-INF/orders.jsp").forward(request, response);
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}
