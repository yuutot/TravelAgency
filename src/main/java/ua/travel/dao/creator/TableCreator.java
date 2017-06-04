package ua.travel.dao.creator;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.FieldType;
import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Enum;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.annotations.relations.ManyToOne;
import ua.travel.dao.utils.DaoUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by yuuto on 5/20/17.
 */
public abstract class TableCreator {

    private final static Logger LOGGER = Logger.getLogger(TableCreator.class.getName());

    private List<Field> fKeys;
    final String CHARSET = "cp1251";

    private final String packageToScan;

    TableCreator(String packageToScan) {
        this.packageToScan = packageToScan;
        this.fKeys = new ArrayList<>();
    }

    protected abstract String createSqlQuery(Class<?> entity);

    List<String> getFields(Class<?> entity) {
        Field[] fields = entity.getDeclaredFields();
        List<String> fieldsName = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(this::createSqlQueryLineForField)
                .collect(Collectors.toList());
        fieldsName.add(0, "id " + FieldType.INTEGER.getTypeNameForLength(10) + " NOT NULL PRIMARY KEY AUTO_INCREMENT");
        return fieldsName;
    }

    private List<String> createForeignKeysForEntity() {
        return fKeys
                .stream()
                .filter(field -> field.isAnnotationPresent(ManyToOne.class))
                .map(this::createSqlQueryForeignKey)
                .collect(Collectors.toList());
    }

    private String createSqlQueryLineForField(Field field) {

        String name = field.getAnnotation(Column.class).value();

        if (field.getType().equals(Integer.class)) {
            return String.format("%s %s", name, FieldType.INTEGER.getTypeNameForLength(12));
        } else if (field.getType().equals(String.class)) {
            return String.format("%s %s CHARACTER SET %s", name, FieldType.STRING.getTypeNameForLength(256), CHARSET);
        } else if (field.getType().equals(Date.class)) {
            return String.format("%s %s", name, FieldType.DATE.getTypeNameForLength());
        } else if (field.getType().equals(Double.class)) {
            return String.format("%s %s", name, FieldType.DOUBLE.getTypeNameForLength(12));
        } else if (field.getAnnotation(Enum.class) != null) {
            return String.format("%s %s", name, FieldType.ENUM.getTypeNameForLength(32));
        } else {
            fKeys.add(field);
            return String.format("%s %s", name, FieldType.OBJECT.getTypeNameForLength(10));
        }
    }

    private String createSqlQueryForeignKey(Field field) {
        String tableName = field.getDeclaringClass().getAnnotation(Table.class).value();
        String referenceTableName = field.getType().getAnnotation(Table.class).value();
        String fieldName = field.getAnnotation(Column.class).value();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder
                .append("ALTER TABLE ")
                .append(tableName)
                .append(" ADD FOREIGN KEY (")
                .append(fieldName)
                .append(") REFERENCES ")
                .append(referenceTableName)
                .append("(id);");
        return queryBuilder.toString();

    }

    public void createTableForEntity() {
        try (Connection connection = DataSourceFactory.getDataSource(DataSourceType.MYSQL).getConnection();
             Statement statement = connection.createStatement()) {
            for (Class<?> entity : DaoUtils.getClassesInPackage(packageToScan)) {
                statement.addBatch(createSqlQuery(entity));
            }
            for (String query : createForeignKeysForEntity()) {
                statement.addBatch(query);
            }
            statement.executeBatch();
            LOGGER.info("Table created");
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
    }
}