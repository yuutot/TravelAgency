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
public class HotelRepository extends BaseRepository<Hotel> {

    private final Logger LOGGER = Logger.getLogger(HotelRepository.class.getName());

    private static HotelRepository hotelRepository;

    private HotelRepository(){}

    public static HotelRepository getInstance() {
        HotelRepository localInstance = hotelRepository;
        if (localInstance == null) {
            synchronized (HotelRepository.class) {
                localInstance = hotelRepository;
                if (localInstance == null) {
                    hotelRepository = localInstance = new HotelRepository();
                }
            }
        }
        return localInstance;
    }

    public List<Hotel> findByCity(City city){
        List<Hotel> hotels = new LinkedList<>();
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Hotel.class.getAnnotation(Table.class).value())
                .createJoinForClass(Hotel.class)
                .where()
                .addCondition("city", Condition.EVEN, city.getId(), Hotel.class)
                .build();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                hotels.add(ResultSetToObjectConverter.parseResultSetToObject(Hotel.class, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return hotels;
    }
}
