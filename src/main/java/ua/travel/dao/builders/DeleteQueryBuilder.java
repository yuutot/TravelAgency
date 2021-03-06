package ua.travel.dao.builders;

import java.util.logging.Logger;

/**
 * Created by yuuto on 5/24/17.
 */
public class DeleteQueryBuilder {

    private final Logger LOGGER = Logger.getLogger(DeleteQueryBuilder.class.getName());
    private StringBuilder query;

    public DeleteQueryBuilder() {
        query = new StringBuilder();
        query.append("delete from ");
    }

    public DeleteQueryBuilder addTable(String tableName) {
        query
                .append(tableName)
                .append(" ");
        return this;
    }

    public DeleteQueryBuilder where() {
        query.append("where ");
        return this;
    }

    public DeleteQueryBuilder addCondition(String field, Condition condition, Object value) {
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

    public DeleteQueryBuilder and() {
        query.append("and ");
        return this;
    }

    public DeleteQueryBuilder or() {
        query.append("or ");
        return this;
    }

    public String build() {
        LOGGER.info("Build query: " + query.toString());
        return query.toString();
    }

}
