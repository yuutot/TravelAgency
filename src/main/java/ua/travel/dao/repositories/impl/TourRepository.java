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
import ua.travel.entity.enums.TourType;

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
public class TourRepository extends BaseRepository<Tour> {

    private final Logger LOGGER = Logger.getLogger(TourRepository.class.getName());

    private static TourRepository tourRepository;

    private TourRepository(){}

    public static TourRepository getInstance() {
        TourRepository localInstance = tourRepository;
        if (localInstance == null) {
            synchronized (TourRepository.class) {
                localInstance = tourRepository;
                if (localInstance == null) {
                    tourRepository = localInstance = new TourRepository();
                }
            }
        }
        return localInstance;
    }

    /**
     * Find Tour by city and tour type
     * @param city
     * @param tourType
     * @return list of tours
     */
    public List<Tour> findByCity(City city, TourType tourType) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .createJoinForClass(Tour.class)
                .where()
                .addCondition("hotel", Condition.EVEN, "id", Tour.class, Hotel.class)
                .and()
                .addCondition("city", Condition.EVEN, city.getId(), Hotel.class)
                .and()
                .addCondition("type", Condition.EVEN, tourType.toString(), Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    /**
     * Find tours by cost
     * @param minCost
     * @param maxCost
     * @param tourType
     * @return list tours
     */
    public List<Tour> findByCost(Double minCost, Double maxCost, TourType tourType) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .createJoinForClass(Tour.class)
                .where()
                .addCondition("type", Condition.EVEN, tourType.toString(), Tour.class)
                .and()
                .between("cost", minCost, maxCost, Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    /**
     * Find tours by type
     * @param tourType
     * @return list of tours
     */
    public List<Tour> findByType(TourType tourType) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .createJoinForClass(Tour.class)
                .where()
                .addCondition("type", Condition.EVEN, tourType.toString(), Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    /**
     * Find hot tours
     * @return list of tours
     */

    public List<Tour> findHotTours() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .createJoinForClass(Tour.class)
                .where()
                .addCondition("hot", Condition.EVEN, "1", Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    /**
     * Find tours by city, cost, tour type
     * @param city
     * @param minCost
     * @param maxCost
     * @param tourType
     * @return list of tours
     */
    public List<Tour> findByCityAndCost(City city, Double minCost, Double maxCost, TourType tourType){
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .createJoinForClass(Tour.class)
                .where()
                .addCondition("hotel", Condition.EVEN, "id", Tour.class, Hotel.class)
                .and()
                .addCondition("city", Condition.EVEN, city.getId(), Hotel.class)
                .and()
                .addCondition("type", Condition.EVEN, tourType.toString(), Tour.class)
                .and()
                .between("cost", minCost, maxCost, Tour.class)
                .build();
        return executeSelectQuery(query);
    }

    /**
     * execute select sql query
     * @param query
     * @return list of tours
     */
    private List<Tour> executeSelectQuery(String query){
        List<Tour> entities = new LinkedList<>();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(ResultSetToObjectConverter.parseResultSetToObject(Tour.class, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return entities;
    }
}
