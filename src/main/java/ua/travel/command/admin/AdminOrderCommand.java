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
 * urlPattern /admin/order
 *
 * Get order by id
 */
public class AdminOrderCommand implements PageCommand {

    private static final Logger LOGGER = Logger.getLogger(AdminOrderCommand.class.getName());
    private static final String PARAM_ID = "id";
    private static final String ATTRIBUTE_ORDER = "order";

    private OrderService orderService = OrderService.getInstance();

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter(PARAM_ID);
        if(id != null && !id.isEmpty() && ValidatorUtils.isValidLong(id)){
            try {
                Order order = orderService.getOrderById(id);
                request.setAttribute(ATTRIBUTE_ORDER, order);
                request.getRequestDispatcher("/WEB-INF/jsp/admin/order.jsp").forward(request,response);
                return;
            } catch (ServiceException e) {
               LOGGER.warning(e.getMessage());
            }
        }
        response.sendRedirect("/admin");
    }
}
