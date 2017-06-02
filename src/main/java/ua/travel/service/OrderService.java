package ua.travel.service;

import ua.travel.command.utils.ValidatorUtils;
import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.UpdateQueryBuilder;
import ua.travel.dao.converters.ObjectToMapConverter;
import ua.travel.dao.repositories.impl.OrderRepository;
import ua.travel.dao.repositories.impl.TourRepository;
import ua.travel.dao.repositories.impl.UserRepository;
import ua.travel.entity.Order;
import ua.travel.entity.Tour;
import ua.travel.entity.User;
import ua.travel.entity.enums.OrderStatus;
import ua.travel.service.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by yuuto on 5/26/17.
 */
public class OrderService {

    private static OrderService orderService;
    private TourRepository tourRepository = TourRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();
    private OrderRepository orderRepository = OrderRepository.getInstance();

    private OrderService(){}

    public static OrderService getInstance() {
        OrderService localInstance = orderService;
        if (localInstance == null) {
            synchronized (OrderService.class) {
                localInstance = orderService;
                if (localInstance == null) {
                    orderService = localInstance = new OrderService();
                }
            }
        }
        return localInstance;
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

    public List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void updateOrderStatus(String orderId, String status) throws ServiceException {
        if(!ValidatorUtils.isValidLong(orderId)){
            throw new ServiceException("Invalid order id: " + orderId);
        }
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        Long id = Long.parseLong(orderId);
        Order order = orderRepository.findById(id).orElseThrow(()->new ServiceException("Cant find order by id: " + orderId));
        order.setOrderStatus(orderStatus);
        Map<String, Object> map = ObjectToMapConverter.parse(order, Order.class);
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection()) {
            UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(connection);
            updateQueryBuilder
                    .addTable(Order.class.getAnnotation(Table.class).value())
                    .addValues(map)
                    .where()
                    .addCondition("id", Condition.EVEN, id)
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
