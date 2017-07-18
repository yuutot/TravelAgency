package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.command.utils.ValidatorUtils;
import ua.travel.service.OrderService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * command edit_order_status
 * <p>
 * Edit order status for one order by id
 */
public class EditOrderStatusCommand implements ExecuteCommand {

    private static final Logger LOGGER = Logger.getLogger(EditOrderStatusCommand.class.getName());
    private static final String PARAM_STATUS = "status";
    private static final String PARAM_ID = "id";
    private static final String ATTRIBUTE_ERROR = "error";

    private OrderService orderService = OrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String status = request.getParameter(PARAM_STATUS);
        String id = request.getParameter(PARAM_ID);
        if (status == null || id == null || id.isEmpty() || !ValidatorUtils.isValidString(status, id)) {
            return "/admin";
        }
        try {
            orderService.updateOrderStatus(id, status);
        } catch (ServiceException e) {
            LOGGER.warning(e.getMessage());
            request.setAttribute(ATTRIBUTE_ERROR, e);
            return "/error";
        }
        return "/admin";
    }
}
