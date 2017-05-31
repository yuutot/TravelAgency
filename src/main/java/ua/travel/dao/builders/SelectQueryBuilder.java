package ua.travel.dao.builders;

import ua.travel.dao.annotations.Table;

/**
 * Created by yuuto on 5/24/17.
 */
public class SelectQueryBuilder {
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

    public SelectQueryBuilder where() {
        query
                .deleteCharAt(query.length() - 2)
                .append("where ");
        return this;
    }

    public SelectQueryBuilder between(String field, Object min, Object max, Class<?> clazz){
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

    public String build() {
        if (query.toString().contains("where")) {
            return query.toString();
        } else {
            return query
                    .deleteCharAt(query.length() - 2)
                    .toString();

        }
    }

}
