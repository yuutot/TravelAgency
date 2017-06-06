package ua.travel.command.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Get the relative path for the file from request
 */
public class PathUtils {
    public static String getContextPath(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        Matcher m = Pattern.compile("http://[^/]+").matcher(url);
        if (m.find()) {
            url = url.substring(m.end(), url.length());
        }
        return url;
    }
}
