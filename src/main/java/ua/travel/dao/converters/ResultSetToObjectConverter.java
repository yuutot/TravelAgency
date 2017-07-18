package ua.travel.dao.converters;

import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.annotations.Enum;
import ua.travel.dao.repositories.BaseRepository;
import ua.travel.dao.utils.DaoUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ResultSetToObjectConverter {

    private static final Logger LOGGER = Logger.getLogger(ResultSetToObjectConverter.class.getName());

    /**
     * @param rs - ResultSet
     * @return HashMap key columnName, value - number
     * @throws SQLException
     */
    private static Map<String, Integer> getColumnsFromResultSet(ResultSet rs) throws SQLException {
        Map<String, Integer> columns = new HashMap<>();
        ResultSetMetaData md = rs.getMetaData();
        int count = md.getColumnCount();

        for (int i = 1; i <= count; i++) {
            String columnName = md.getTableName(i) + "." + md.getColumnName(i);
            columns.put(columnName, i);
        }
        return columns;
    }

    /**
     * @param clazz Link for class for parse
     * @param rs    ResultSet
     * @return Instance of object with data from ResultSet
     * @throws SQLException
     */

    public static <T> T parseResultSetToObject(Class<T> clazz, ResultSet rs) throws SQLException {
        LOGGER.info("Start parse rs to object");
        Map<String, Integer> columns = getColumnsFromResultSet(rs);
        try {
            T object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            String tableName = clazz.getAnnotation(Table.class).value();
            Arrays.stream(fields)
                    .peek(f -> f.setAccessible(true))
                    .forEach(f -> parseResultSetColumn(f, object, rs, tableName, columns));
            LOGGER.info("Parse object complete. " + object);
            return object;

        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }

    /**
     * Set values to the field from ResultSet
     *
     * @param field     link to field
     * @param obj       Object
     * @param rs        ResultSet
     * @param tableName Name of table for parse
     * @param columns   Map with column names
     */

    private static void parseResultSetColumn(Field field, Object obj, ResultSet rs, String tableName, Map<String, Integer> columns) {
        String columnName = tableName + "." + (field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).value() : "id");
        try {
            Object value;
            if (field.isAnnotationPresent(Enum.class)) {
                value = parseEnum(field, rs, columnName, columns);
            } else if (DaoUtils.isEntity(field)) {
                value = parseResultSetToObject(field.getType(), rs);
            } else {
                Integer columnIndex = columns.get(columnName);
                if (columnIndex == null) {
                    return;
                }
                value = rs.getObject(columns.get(columnName));
            }

            if (field.getType().equals(Long.class) && value != null) {
                value = new Long((Integer) value);
            }
            if (field.getType().equals(Double.class) && value != null) {
                value = ((int) (((float) value) * 100)) / 100.0;
            }
            if (field.getType().equals(Boolean.class) && value != null) {
                value = (int) value != 0;
            }
            field.set(obj, value);

        } catch (IllegalArgumentException | IllegalAccessException | SQLException | SecurityException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private static Object parseEnum(Field field, ResultSet rs, String columnName, Map<String, Integer> columns) {
        Class<?> enumClass = field.getType();
        Method valueOf;
        try {
            valueOf = enumClass.getDeclaredMethod("valueOf", String.class);
            String columnValue = (String) rs.getObject(columns.get(columnName));
            return valueOf.invoke(null, columnValue);

        } catch (NoSuchMethodException | SecurityException |
                SQLException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }
}