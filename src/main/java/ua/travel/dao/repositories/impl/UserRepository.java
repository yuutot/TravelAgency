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
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/19/17.
 */
public class UserRepository extends BaseRepository<User> {

    private final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

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
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.ofNullable(ResultSetToObjectConverter.parseResultSetToObject(User.class, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return Optional.empty();
    }
}
