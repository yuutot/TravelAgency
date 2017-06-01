package ua.travel.service;

import ua.travel.dao.repositories.impl.OrderRepository;
import ua.travel.dao.repositories.impl.TourRepository;
import ua.travel.dao.repositories.impl.UserRepository;
import ua.travel.entity.Order;
import ua.travel.entity.Tour;
import ua.travel.entity.User;
import ua.travel.entity.enums.OrderStatus;
import ua.travel.service.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Created by yuuto on 5/26/17.
 */
public class OrderService {

    private static OrderService orderService;
    private TourRepository tourRepository = TourRepository.newInstance();
    private UserRepository userRepository = UserRepository.newInstance();
    private OrderRepository orderRepository = OrderRepository.newInstance();

    public static synchronized OrderService newInstance() {
        if(orderService == null){
            orderService = new OrderService();
        }
        return orderService;
    }

    public Long bookTour(Long tourId, Long userId) throws ServiceException {
        Tour tour = tourRepository.findById(tourId).orElseThrow(()->new ServiceException("Cant find tour by id: "+ tourId));
        User user = userRepository.findById(userId).orElseThrow(()->new ServiceException("Cant find user by id: " + userId));
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.NEW);
        order.setDate(new Date());
        order.setTour(tour);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) throws ServiceException {
        User user = userRepository.findById(userId).orElseThrow(()->new ServiceException("Cant find user by id: " + userId));
        return orderRepository.findByUser(user);
    }

}
