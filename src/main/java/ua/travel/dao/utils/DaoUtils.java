package ua.travel.dao.utils;

import org.reflections.Reflections;
import ua.travel.config.EntityScan;
import ua.travel.config.InitializeListener;
import ua.travel.dao.FieldType;
import ua.travel.dao.annotations.Table;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/21/17.
 */
public class DaoUtils {

    private final static Logger LOGGER = Logger.getLogger(DaoUtils.class.getName());
    private static Set<Class<?>> entities;

    /**
     * Get object type based enum
     *
     * @param field
     * @return FieldType
     */
    public static FieldType getObjectType(Object field) {

        if (field.getClass().equals(Integer.class) || field.getClass().equals(Long.class)) {
            return FieldType.INTEGER;
        } else if (field.getClass().equals(String.class)) {
            return FieldType.STRING;
        } else if (field.getClass().equals(Timestamp.class) || field.getClass().equals(Date.class)) {
            return FieldType.DATE;
        } else if (field.getClass().equals(Double.class)) {
            return FieldType.DOUBLE;
        } else if (field.getClass().equals(Boolean.class)) {
            return FieldType.BOOLEAN;
        } else if (field.getClass().isEnum()) {
            return FieldType.ENUM;
        } else {
            return FieldType.OBJECT;
        }
    }

    public static Set<Class<?>> getClassesInPackage(String packageToScan) {
        if (entities == null) {
            Reflections reflections = new Reflections(packageToScan);
            entities = reflections.getTypesAnnotatedWith(Table.class);
        }
        return entities;
    }

    public static Boolean isEntity(Field field) {
        return getClassesInPackage(InitializeListener.class.getAnnotation(EntityScan.class).value()).contains(field.getType());
    }

    public static void putValuesToStatement(Map<String, Object> map, PreparedStatement statement) throws SQLException {
        Integer position = 1;
        for (String keys : map.keySet()) {
            Object value = map.get(keys);
            if (value != null) {
                LOGGER.info(value.toString());
                if (DaoUtils.getObjectType(value) == FieldType.OBJECT) {
                    try {
                        Field field = value.getClass().getDeclaredField("id");
                        field.setAccessible(true);
                        value = field.get(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (DaoUtils.getObjectType(value) == FieldType.DATE) {
                    value = new Timestamp(((Date) value).getTime());
                } else if (DaoUtils.getObjectType(value) == FieldType.STRING) {
                    statement.setString(position++, (String) value);
                    continue;
                }
            }
            statement.setObject(position++, value);
        }
    }
}
