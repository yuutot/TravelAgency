package ua.travel.command.utils;

/**
 * Created by yuuto on 5/31/17.
 */
public class ValidatorUtils {

    public static boolean isValidLong(String value){
        return value.matches("\\d*");
    }

    public static boolean isValidDouble(String value){
        return value.matches("\\d*[.]?\\d*");
    }

    public static boolean isEmptyString(String str){
        return str == null || str.isEmpty();
    }
}