package ua.travel;

import ua.travel.dao.repositories.BaseRepository;
import ua.travel.dao.repositories.impl.OrderRepository;
import ua.travel.dao.repositories.impl.TourRepository;
import ua.travel.dao.repositories.impl.UserRepository;
import ua.travel.entity.Order;
import ua.travel.entity.Tour;
import ua.travel.entity.User;
import ua.travel.entity.enums.OrderStatus;

import java.util.Date;

/**
 * Created by yuuto on 5/21/17.
 */
public class Main {
    public static void main(String[] args) {
//        Order order = new Order();
//        User user = new User();
//        Tour tour = new Tour();
//        order.setOrderStatus(OrderStatus.CANCELED);
//        order.setUser(user);
//        order.setDate(new Date());
//        order.setTour(tour);
//        BaseRepository baseRepository = OrderRepository.newInstance();
//        BaseRepository baseRepository1 = TourRepository.newInstance();
//        BaseRepository baseRepository2 = UserRepository.newInstance();
//        user.setId(baseRepository2.save(user));
//        tour.setId(baseRepository1.save(tour));
//        System.out.println(baseRepository.save(order));
        Integer q = 2;
        Class<?> s = q.getClass();
        System.out.println(s.getSimpleName());
    }
}
