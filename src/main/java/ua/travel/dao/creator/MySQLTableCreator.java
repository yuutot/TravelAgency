package ua.travel.dao.creator;

import ua.travel.dao.annotations.Table;

/**
 * Created by yuuto on 5/19/17.
 */
public class MySQLTableCreator extends TableCreator {

    public MySQLTableCreator(String packageToScan) {
        super(packageToScan);
    }

    protected String createSqlQuery(Class<?> entity) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(entity.getAnnotation(Table.class).value())
                .append("(").append("\n");
        getFields(entity).forEach(field -> queryBuilder.append(field).append(',').append("\n"));
        queryBuilder.deleteCharAt(queryBuilder.length() - 2);
        queryBuilder
                .append(")")
                .append("DEFAULT CHARSET=")
                .append(CHARSET)
                .append(';');
        return queryBuilder.toString();
    }


}
