package ua.travel.dao.repositories;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.*;
import ua.travel.dao.converters.ObjectToMapConverter;
import ua.travel.dao.converters.ResultSetToObjectConverter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by yuuto on 5/19/17.
 */
public abstract class BaseRepository<T> {
    private final Class<?> clazz = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public Optional<T> findById(Long id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(clazz.getAnnotation(Table.class).value())
                .where()
                .addCondition("id", Condition.EVEN, id, clazz)
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.of((T) ResultSetToObjectConverter.parseResultSetToObject(clazz, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Long save(T entity) {
        Map<String, Object> map = ObjectToMapConverter.parse(entity, clazz);
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection()) {
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(connection);
            return insertQueryBuilder
                    .addTable(clazz.getAnnotation(Table.class).value())
                    .addValues(map)
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(T entity){
        Long id = null;
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            id = (Long) field.get(entity);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = ObjectToMapConverter.parse(entity, clazz);
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection()) {
            UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(connection);
            updateQueryBuilder
                    .addTable(clazz.getAnnotation(Table.class).value())
                    .addValues(map)
                    .where()
                    .addCondition("id", Condition.EVEN, id)
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> findAll() {
        List<T> entities = new LinkedList<>();
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(clazz.getAnnotation(Table.class).value())
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                entities.add((T) ResultSetToObjectConverter.parseResultSetToObject(clazz, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public void delete(T entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Long id = (Long) field.get(entity);
            delete(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String query = deleteQueryBuilder
                .addTable(clazz.getAnnotation(Table.class).value())
                .where()
                .addCondition("id", Condition.EVEN, id)
                .build();
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
