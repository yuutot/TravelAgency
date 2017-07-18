package ua.travel.dao.builders;

import ua.travel.dao.utils.DaoUtils;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/24/17.
 */
public class InsertQueryBuilder {

    private final Logger LOGGER = Logger.getLogger(InsertQueryBuilder.class.getName());
    private StringBuilder query;
    private Map<String, Object> map;
    private Connection connection;

    public InsertQueryBuilder(Connection connection) {
        this.connection = connection;
        query = new StringBuilder();
        query.append("insert into ");
    }

    public InsertQueryBuilder addTable(String tableName) {
        query
                .append(tableName)
                .append(" ");
        return this;
    }

    public InsertQueryBuilder addValues(Map<String, Object> map) throws SQLException {
        this.map = map;
        query.append('(');
        map.keySet().forEach(s -> query.append(s).append(','));
        query.setCharAt(query.length() - 1, ')');
        query.append(" values(");
        map.keySet().forEach(s -> query.append('?').append(','));
        query.setCharAt(query.length() - 1, ')');
        return this;
    }

    public Long execute() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
            DaoUtils.putValuesToStatement(map, preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Execute insert query: " + query.toString());
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }
}
