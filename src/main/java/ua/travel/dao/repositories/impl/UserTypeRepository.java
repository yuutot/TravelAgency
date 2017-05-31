package ua.travel.dao.repositories.impl;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.dao.converters.ResultSetToObjectConverter;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.UserType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Created by yuuto on 5/19/17.
 */
public class UserTypeRepository extends BaseRepository<UserType> {

    private static UserTypeRepository userTypeRepository;

    private UserTypeRepository(){}

    public static synchronized UserTypeRepository newInstance() {
        if (userTypeRepository == null) {
            userTypeRepository = new UserTypeRepository();
        }
        return userTypeRepository;
    }

    public Optional<UserType> findByType(String type) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(UserType.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("type", Condition.EVEN, type, UserType.class)
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.of(ResultSetToObjectConverter.parseResultSetToObject(UserType.class, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
