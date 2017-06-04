package ua.travel.dao.builders;

import ua.travel.dao.utils.DaoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/24/17.
 */
public class UpdateQueryBuilder {

    private final Logger LOGGER = Logger.getLogger(UpdateQueryBuilder.class.getName());
    private StringBuilder query;
    private Map<String, Object> map;
    private Connection connection;

    public UpdateQueryBuilder(Connection connection) {
        this.connection = connection;
        query = new StringBuilder();
        query.append("update ");
    }

    public UpdateQueryBuilder addTable(String tableName){
        query
                .append(tableName)
                .append(" ");
        return this;
    }

    public UpdateQueryBuilder addValues(Map<String, Object> map) {
        this.map = map;
        query.append("set ");
        map.keySet().forEach(s -> query.append(s).append('=').append('?').append(','));
        query.deleteCharAt(query.length()-1);
        return this;
    }

    public UpdateQueryBuilder where(){
        query.append(" where ");
        return this;
    }

    public UpdateQueryBuilder addCondition(String field, Condition condition, Object value){
        boolean isString = value instanceof String;
        query
                .append(field)
                .append(" ")
                .append(condition.toString())
                .append(isString ? " '" : " ")
                .append(value)
                .append(isString ? "' " : " ");
        return this;
    }

    public UpdateQueryBuilder and(){
        query.append("and ");
        return this;
    }

    public UpdateQueryBuilder or(){
        query.append("or ");
        return this;
    }

    public void execute() throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
            DaoUtils.putValuesToStatement(map, preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Execute update query: " + query.toString());
        }
    }

}
