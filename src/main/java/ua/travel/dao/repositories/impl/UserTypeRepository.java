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
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/19/17.
 */
public class UserTypeRepository extends BaseRepository<UserType> {

    private final Logger LOGGER = Logger.getLogger(UserTypeRepository.class.getName());

    private static UserTypeRepository userTypeRepository;

    private UserTypeRepository(){}

    public static UserTypeRepository getInstance() {
        UserTypeRepository localInstance = userTypeRepository;
        if (localInstance == null) {
            synchronized (UserTypeRepository.class) {
                localInstance = userTypeRepository;
                if (localInstance == null) {
                    userTypeRepository = localInstance = new UserTypeRepository();
                }
            }
        }
        return localInstance;
    }

    public Optional<UserType> findByType(String type) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(UserType.class.getAnnotation(Table.class).value())
                .createJoinForClass(UserType.class)
                .where()
                .addCondition("type", Condition.EVEN, type, UserType.class)
                .build();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.ofNullable(ResultSetToObjectConverter.parseResultSetToObject(UserType.class, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return Optional.empty();
    }
}
