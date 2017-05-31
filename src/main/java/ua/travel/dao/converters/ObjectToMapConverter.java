package ua.travel.dao.converters;

import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuuto on 5/18/17.
 */
public class ObjectToMapConverter {

    public static <T> Map<String, Object> parse(T object, Class<?> clazz) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        Arrays.stream(fields)
                .filter(f -> f.getAnnotation(Column.class) != null &&
                        f.getAnnotation(Id.class) == null)
                .peek(f -> f.setAccessible(true))
                .forEach(f -> parseObjectField(f, object, result));
        return result;
    }

    private static void parseObjectField(Field field, Object obj, Map<String, Object> result) {
        String columnName = field.getAnnotation(Column.class).value();
        try {
            Object value = Enum.class.isAssignableFrom(field.getType())
                    ? parseEnum(field, obj)
                    : field.get(obj);

            result.put(columnName, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Object parseEnum(Field field, Object obj) {
        try {
            return ((Enum) field.get(obj)).name();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}