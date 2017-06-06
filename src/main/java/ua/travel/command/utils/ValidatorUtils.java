package ua.travel.command.utils;

public class ValidatorUtils {

    /**
     * Check for valid long
     *
     * @param value
     * @return true if valid long
     */
    public static boolean isValidLong(String value){
        return value.matches("\\d*");
    }

    /**
     * Check for valid double
     *
     * @param value
     * @return true if valid double
     */
    public static boolean isValidDouble(String value){
        return !value.isEmpty() && value.matches("\\d*[.]?\\d*");
    }

    /**
     * Check at least one empty string
     *
     * @param str
     * @return true if have empty string
     */
    public static boolean isEmptyString(String... str){
        if(str.length == 0) return true;
        for(String param : str){
            if(param == null || param.isEmpty()){
                return true;
            }
        }
        return false;
    }
    /**
     * Check at least one valid string
     *
     * @param str
     * @return true if have valid string
     */
    public static boolean isHaveValidString(String... str){
        if(str.length == 0) return true;
        for(String param : str){
            if(param != null && !param.isEmpty()){
                return true;
            }
        }
        return false;
    }
}