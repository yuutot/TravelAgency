package ua.travel.dao.builders;

import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.utils.DaoUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by yuuto on 5/24/17.
 */
public class SelectQueryBuilder {

    private final Logger LOGGER = Logger.getLogger(SelectQueryBuilder.class.getName());
    private StringBuilder query;

    public SelectQueryBuilder() {
        query = new StringBuilder();
        query.append("select ");
    }

    public SelectQueryBuilder addField(String field) {
        query
                .append(field)
                .append(", ");
        return this;
    }

    public SelectQueryBuilder from() {
        query.deleteCharAt(query.length() - 2);
        query.append("from ");
        return this;
    }

    public SelectQueryBuilder addTable(String tableName) {
        query
                .append(tableName)
                .append(", ");
        return this;
    }

    public SelectQueryBuilder createJoinForClass(Class<?> clazz) {
        if (query.charAt(query.length() - 2) == ',') {
            query.deleteCharAt(query.length() - 2);
        }
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).filter(DaoUtils::isEntity).forEach(field -> createJoin(clazz, field));
        return this;
    }

    private void createJoin(Class<?> clazz, Field field) {
        Class<?> fieldClass = field.getType();
        join(clazz, field.getAnnotation(Column.class).value(), fieldClass);
        createJoinForClass(fieldClass);
    }

    public SelectQueryBuilder join(Class<?> classFrom, String field, Class<?> classTo) {
        query
                .append("inner join ")
                .append(classTo.getAnnotation(Table.class).value())
                .append(" on ")
                .append(classFrom.getAnnotation(Table.class).value())
                .append('.')
                .append(field)
                .append("=")
                .append(classTo.getAnnotation(Table.class).value())
                .append(".id ");
        return this;
    }

    public SelectQueryBuilder where() {
        if (query.charAt(query.length() - 2) == ',') {
            query.deleteCharAt(query.length() - 2);
        }
        query.append("where ");
        return this;
    }

    public SelectQueryBuilder between(String field, Object min, Object max, Class<?> clazz) {
        query
                .append(clazz.getAnnotation(Table.class).value())
                .append('.')
                .append(field)
                .append(" BETWEEN ")
                .append(min)
                .append(" and ")
                .append(max)
                .append(" ");
        return this;
    }

    public SelectQueryBuilder addCondition(String field, Condition condition, Object value, Class<?> clazz) {
        boolean isString = value instanceof String;
        query
                .append(clazz.getAnnotation(Table.class).value())
                .append('.')
                .append(field)
                .append(" ")
                .append(condition.toString())
                .append(isString ? " '" : " ")
                .append(value)
                .append(isString ? "' " : " ");
        return this;
    }

    public SelectQueryBuilder addCondition(String leftField, Condition condition, String rightField, Class<?> leftClazz, Class<?> rightClazz) {
        query
                .append(leftClazz.getAnnotation(Table.class).value())
                .append('.')
                .append(leftField)
                .append(" ")
                .append(condition.toString())
                .append(" ")
                .append(rightClazz.getAnnotation(Table.class).value())
                .append('.')
                .append(rightField)
                .append(" ");
        return this;
    }

    public SelectQueryBuilder and() {
        query.append("and ");
        return this;
    }

    public SelectQueryBuilder or() {
        query.append("or ");
        return this;
    }

    public SelectQueryBuilder limit(Long limit, Long offset) {
        if (query.charAt(query.length() - 2) == ',') {
            query.deleteCharAt(query.length() - 2);
        }
        query
                .append(" LIMIT ")
                .append(limit)
                .append(" OFFSET ")
                .append(offset);
        return this;
    }

    public String build() {
        LOGGER.info("Build select query: " + query.toString());
        if (query.toString().contains("where") || query.charAt(query.length() - 2) != ',') {
            return query.toString();
        } else {
            return query
                    .deleteCharAt(query.length() - 2)
                    .toString();

        }
    }

}
