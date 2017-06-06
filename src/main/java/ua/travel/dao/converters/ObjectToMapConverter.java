package ua.travel.dao.converters;

import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/18/17.
 */
public class ObjectToMapConverter {

    private final static Logger LOGGER = Logger.getLogger(ObjectToMapConverter.class.getName());

    /**
     * Parse object to HashMap
     *
     * @param object object for parse
     * @param clazz class of object
     * @return HashMap. key - field name, value - field value
     */
    public static <T> Map<String, Object> parse(T object, Class<?> clazz) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        LOGGER.info("Start parse object: " + object);
        Arrays.stream(fields)
                .filter(f -> f.isAnnotationPresent(Column.class) &&
                        !f.isAnnotationPresent(Id.class))
                .peek(f -> f.setAccessible(true))
                .forEach(f -> parseObjectField(f, object, result));

        LOGGER.info("Ready object: " + result);
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
            LOGGER.warning(e.getMessage());
        }
    }

    private static Object parseEnum(Field field, Object obj) {
        try {
            return ((Enum) field.get(obj)).name();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.warning(e.getMessage());
            throw new RuntimeException();
        }
    }
}