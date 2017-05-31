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

public class ResultSetToObjectConverter {

    private static Map<String, Integer> columns = new HashMap<>();

    private static void getColumnsFromResultSet(ResultSet rs) {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int count = md.getColumnCount();

            for (int i = 1; i <= count; i++) {
                String columnName = md.getTableName(i) + "." + md.getColumnName(i);
                columns.put(columnName, i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> T parseResultSetToObject(Class<T> clazz, ResultSet rs) {
        getColumnsFromResultSet(rs);
        try {
            T object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            String tableName = clazz.getAnnotation(Table.class).value();
            Arrays.stream(fields)
                    .peek(f -> f.setAccessible(true))
                    .forEach(f -> parseResultSetColumn(f, object, rs, tableName));
            return object;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void parseResultSetColumn(Field field, Object obj, ResultSet rs, String tableName) {
        String columnName = tableName + "." + (field.getAnnotation(Column.class) != null ? field.getAnnotation(Column.class).value() : "id");
        try {
            Object value;
            if (field.getAnnotation(Enum.class) != null) {
                value = parseEnum(field, rs, columnName);
            } else if (DaoUtils.isEntity(field)) {
                value = parseObject(field, rs, columnName);
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
            field.set(obj, value);

        } catch (IllegalArgumentException | IllegalAccessException | SQLException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private static Object parseObject(Field field, ResultSet rs, String columnName) {
        try {
            Long value = rs.getLong(columnName);
            Class<?> repository = Class.forName("ua.travel.dao.repositories.impl."+ field.getType().getSimpleName() + "Repository");
            BaseRepository baseRepository = (BaseRepository) repository.getMethod("newInstance").invoke(repository);
            return baseRepository.findById(value).get();
        } catch (InvocationTargetException | ClassNotFoundException | IllegalAccessException | SQLException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object parseEnum(Field field, ResultSet rs, String columnName) {
        Class<?> enumClass = field.getType();
        Method valueOf;
        try {
            valueOf = enumClass.getDeclaredMethod("valueOf", String.class);
            String columnValue = (String) rs.getObject(columns.get(columnName));
            return valueOf.invoke(null, columnValue);

        } catch (NoSuchMethodException | SecurityException |
                SQLException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException();
        }
    }
}