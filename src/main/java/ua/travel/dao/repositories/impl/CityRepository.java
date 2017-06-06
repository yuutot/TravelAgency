package ua.travel.dao.repositories.impl;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.dao.converters.ResultSetToObjectConverter;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.City;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/19/17.
 */
public class CityRepository extends BaseRepository<City> {

    private final Logger LOGGER = Logger.getLogger(CityRepository.class.getName());

    private static CityRepository cityRepository;

    private CityRepository(){}

    public static CityRepository getInstance() {
        CityRepository localInstance = cityRepository;
        if (localInstance == null) {
            synchronized (CityRepository.class) {
                localInstance = cityRepository;
                if (localInstance == null) {
                    cityRepository = localInstance = new CityRepository();
                }
            }
        }
        return localInstance;
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
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.ofNullable(ResultSetToObjectConverter.parseResultSetToObject(City.class, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return Optional.empty();
    }
}
