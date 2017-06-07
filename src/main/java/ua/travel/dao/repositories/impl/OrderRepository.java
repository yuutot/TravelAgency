package ua.travel.dao.repositories.impl;

import org.h2.store.Page;
import ua.travel.config.datasource.DataSourceFactory;
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
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/19/17.
 */
public class OrderRepository extends BaseRepository<Order> {

    private final Logger LOGGER = Logger.getLogger(OrderRepository.class.getName());

    private static OrderRepository orderRepository;

    private OrderRepository() {
    }

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

    /**
     * Find order by user
     *
     * @param user
     * @return list of orders
     */
    public List<Order> findByUser(User user) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Order.class.getAnnotation(Table.class).value())
                .createJoinForClass(Order.class)
                .where()
                .addCondition("user_id", Condition.EVEN, user.getId(), Order.class)
                .build();
        return executeSelectQuery(query);
    }

    /**
     * Find orders by status
     *
     * @param status
     * @return list of orders
     */
    public List<Order> findByStatus(OrderStatus status) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Order.class.getAnnotation(Table.class).value())
                .createJoinForClass(Order.class)
                .where()
                .addCondition("status", Condition.EVEN, status.toString(), Order.class)
                .build();
        return executeSelectQuery(query);

    }

    public List<Order> findByPage(Long page, Long itemsOnPage) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Order.class.getAnnotation(Table.class).value())
                .createJoinForClass(Order.class)
                .limit(itemsOnPage, (page - 1) * itemsOnPage)
                .build();
        return executeSelectQuery(query);
    }

    public Long getCount() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("count(*)")
                .from()
                .addTable(Order.class.getAnnotation(Table.class).value())
                .build();
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return 0L;
    }

    /**
     * Execute select sql query
     *
     * @param query
     * @return list of orders
     */
    private List<Order> executeSelectQuery(String query) {
        List<Order> entities = new LinkedList<>();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(ResultSetToObjectConverter.parseResultSetToObject(Order.class, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return entities;
    }
}
