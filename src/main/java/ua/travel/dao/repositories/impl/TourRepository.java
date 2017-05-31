package ua.travel.dao.repositories.impl;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.dao.converters.ResultSetToObjectConverter;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.entity.City;
import ua.travel.entity.Hotel;
import ua.travel.entity.Tour;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuuto on 5/19/17.
 */
public class TourRepository extends BaseRepository<Tour> {

    private static TourRepository tourRepository;

    private TourRepository(){}

    public static synchronized TourRepository newInstance() {
        if (tourRepository == null) {
            tourRepository = new TourRepository();
        }
        return tourRepository;
    }

    public List<Tour> findByPrice(double min, double max) {
        List<Tour> entities = new LinkedList<>();
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .where()
                .between("cost", min, max, Tour.class)
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(ResultSetToObjectConverter.parseResultSetToObject(Tour.class, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public List<Tour> findByCity(City city) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .addTable(Hotel.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("hotel", Condition.EVEN, "id", Tour.class, Hotel.class)
                .and()
                .addCondition("city", Condition.EVEN, city.getId(), Hotel.class)
                .build();
        return executeSelectQuery(query);
    }

    public List<Tour> findByCost(Double minCost, Double maxCost) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .where()
                .between("cost", minCost, maxCost, Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    public List<Tour> findByCityAndCost(City city, Double minCost, Double maxCost){
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .addTable(Hotel.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("hotel", Condition.EVEN, "id", Tour.class, Hotel.class)
                .and()
                .addCondition("city", Condition.EVEN, city.getId(), Hotel.class)
                .and()
                .between("cost", minCost, maxCost, Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    private List<Tour> executeSelectQuery(String query){
        List<Tour> entities = new LinkedList<>();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(ResultSetToObjectConverter.parseResultSetToObject(Tour.class, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
