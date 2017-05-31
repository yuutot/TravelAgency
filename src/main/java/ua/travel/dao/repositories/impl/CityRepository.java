package ua.travel.dao.repositories.impl;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.dao.converters.ResultSetToObjectConverter;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.City;
import ua.travel.entity.UserType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Created by yuuto on 5/19/17.
 */
public class CityRepository extends BaseRepository<City> {

    private static CityRepository cityRepository;

    private CityRepository(){}

    public static synchronized CityRepository newInstance() {
        if (cityRepository == null) {
            cityRepository = new CityRepository();
        }
        return cityRepository;
    }

    public Optional<City> findByName(String name) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(City.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("name", Condition.EVEN, name, City.class)
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.of(ResultSetToObjectConverter.parseResultSetToObject(City.class, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
