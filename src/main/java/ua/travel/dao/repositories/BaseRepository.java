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
import java.util.logging.Logger;

/**
 * Base repository has a basic functionality.
 */
public abstract class BaseRepository<T> {

    private final Class<?> clazz = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    /**
     * @param id
     * @return entity received from the database
     */
    public Optional<T> findById(Long id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(clazz.getAnnotation(Table.class).value())
                .createJoinForClass(clazz)
                .where()
                .addCondition("id", Condition.EVEN, id, clazz)
                .build();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return Optional.ofNullable((T) ResultSetToObjectConverter.parseResultSetToObject(clazz, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Save entity to db
     *
     * @param entity
     * @return id for saved entity
     */
    public Long save(T entity) {
        Map<String, Object> map = ObjectToMapConverter.parse(entity, clazz);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(connection);
            return insertQueryBuilder
                    .addTable(clazz.getAnnotation(Table.class).value())
                    .addValues(map)
                    .execute();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return null;
    }

    /**
     * Update entity and save it to db
     *
     * @param entity
     */
    public void update(T entity) {
        Long id = null;
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            id = (Long) field.get(entity);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOGGER.warning(e.getMessage());
        }
        Map<String, Object> map = ObjectToMapConverter.parse(entity, clazz);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(connection);
            updateQueryBuilder
                    .addTable(clazz.getAnnotation(Table.class).value())
                    .addValues(map)
                    .where()
                    .addCondition("id", Condition.EVEN, id)
                    .execute();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * @return list of entity received from the database
     */
    public List<T> findAll() {
        List<T> entities = new LinkedList<>();
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(clazz.getAnnotation(Table.class).value())
                .createJoinForClass(clazz)
                .build();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                entities.add((T) ResultSetToObjectConverter.parseResultSetToObject(clazz, resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return entities;
    }

    /**
     * delete entity
     *
     * @param entity
     */
    public void delete(T entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Long id = (Long) field.get(entity);
            delete(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * delete entity by id
     *
     * @param id
     */
    public void delete(Long id) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String query = deleteQueryBuilder
                .addTable(clazz.getAnnotation(Table.class).value())
                .where()
                .addCondition("id", Condition.EVEN, id)
                .build();
        LOGGER.info(query);
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
