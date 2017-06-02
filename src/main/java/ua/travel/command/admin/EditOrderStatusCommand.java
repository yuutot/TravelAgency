package ua.travel.command.admin;

import ua.travel.command.ExecuteCommand;
import ua.travel.service.OrderService;
import ua.travel.service.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuuto on 6/2/17.
 */
public class EditOrderStatusCommand implements ExecuteCommand {

    private OrderService orderService = OrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String status = request.getParameter("status");
        String id = request.getParameter("id");
        if(status == null || id == null || id.isEmpty()){
            return "/admin";
        }
        try {
            orderService.updateOrderStatus(id, status);
        } catch (ServiceException e) {
            request.setAttribute("error", e);
            return "/error";
        }
        return "/admin";
    }
}