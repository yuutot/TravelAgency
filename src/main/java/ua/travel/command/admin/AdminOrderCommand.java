package ua.travel.command.admin;

import ua.travel.command.PageCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.entity.Order;
import ua.travel.service.OrderService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by yuuto on 6/2/17.
 */
public class AdminOrderCommand implements PageCommand {

    private OrderService orderService = OrderService.getInstance();
    private static final Logger LOGGER = Logger.getLogger(AdminOrderCommand.class.getName());

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        if(id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)){
            try {
                Order order = orderService.getOrderById(id);
                request.setAttribute("order", order);
                request.getRequestDispatcher("/WEB-INF/jsp/admin/order.jsp").forward(request,response);
                return;
            } catch (ServiceException e) {
               LOGGER.warning(e.getMessage());
            }
        }
        response.sendRedirect("/admin");
    }
}
