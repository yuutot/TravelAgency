package ua.travel.dao.repositories.impl;

import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.Order;

/**
 * Created by yuuto on 5/19/17.
 */
public class OrderRepository extends BaseRepository<Order> {

    private static OrderRepository orderRepository;

    private OrderRepository(){}

    public static synchronized OrderRepository newInstance(){
        if(orderRepository == null){
            orderRepository = new OrderRepository();
        }
        return orderRepository;
    }
}
