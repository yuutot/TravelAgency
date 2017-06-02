package ua.travel.dao.repositories.impl;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.dao.converters.ResultSetToObjectConverter;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Created by yuuto on 5/19/17.
 */
public class UserRepository extends BaseRepository<User> {

    private static UserRepository userRepository;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        UserRepository localInstance = userRepository;
        if (localInstance == null) {
            synchronized (UserRepository.class) {
                localInstance = userRepository;
                if (localInstance == null) {
                    userRepository = localInstance = new UserRepository();
                }
            }
        }
        return localInstance;
    }

    public Optional<User> findByLogin(String login) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(User.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("login", Condition.EVEN, login, User.class)
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.of(ResultSetToObjectConverter.parseResultSetToObject(User.class, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
