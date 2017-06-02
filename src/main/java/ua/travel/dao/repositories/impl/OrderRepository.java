package ua.travel.dao.repositories.impl;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.dao.converters.ResultSetToObjectConverter;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.Order;
import ua.travel.entity.User;
import ua.travel.entity.enums.OrderStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuuto on 5/19/17.
 */
public class OrderRepository extends BaseRepository<Order> {

    private static OrderRepository orderRepository;

    private OrderRepository(){}

    public static OrderRepository getInstance() {
        OrderRepository localInstance = orderRepository;
        if (localInstance == null) {
            synchronized (OrderRepository.class) {
                localInstance = orderRepository;
                if (localInstance == null) {
                    orderRepository = localInstance = new OrderRepository();
                }
            }
        }
        return localInstance;
    }

    public List<Order> findByUser(User user){
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Order.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("user_id", Condition.EVEN, user.getId(), Order.class)
                .build();
        return executeSelectQuery(query);
    }

    public List<Order> findByStatus(OrderStatus status){
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Order.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("status", Condition.EVEN, status.toString(), Order.class)
                .build();
        return executeSelectQuery(query);

    }

    private List<Order> executeSelectQuery(String query){
        List<Order> entities = new LinkedList<>();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(ResultSetToObjectConverter.parseResultSetToObject(Order.class, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
